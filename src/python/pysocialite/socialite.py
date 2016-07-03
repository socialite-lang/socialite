
__doc__ = """
"""

__all__ = ["decl", "run", "query", "clear", "drop"]

def _init():
    from callbackServer import getCallbackPort

    from pysocialite.rpc.query import QueryService
    from pysocialite.rpc.query.ttypes import QueryMessage, TQueryError
    from pysocialite.rpc.standalone import StandaloneService
    from pysocialite.thriftUtil import translate

    from thrift import Thrift
    from thrift.transport import TSocket
    from thrift.transport import TTransport
    from thrift.protocol import TCompactProtocol
    from thrift.protocol import TMultiplexedProtocol

    import os

    def connect():
        tryCnt = 0
        while True:
            try:
                port = int(os.environ["SocialiteStandalonePort"])
                transport = TSocket.TSocket('localhost', port)
                transport = TTransport.TFramedTransport(transport)
                transport.open()
                protocol = TCompactProtocol.TCompactProtocol(transport)
                muxproto = TMultiplexedProtocol.TMultiplexedProtocol(protocol, "QueryService")
                queryClient = QueryService.Client(muxproto)
                return queryClient
            except Thrift.TException as tx:
                tryCnt += 1
                if tryCnt <= 30:
                    import time
                    time.sleep(0.02)
                else:
                    print "Exception while connecting to SociaLite SingleNodeServer"
                    print(('%s' % (tx.message)))
                    import sys
                    sys.exit()

    class Socialite:
        def __init__(self, queryCli):
            self.queryClient = queryCli

        def run(self, query):
            qm = QueryMessage()
            qm.query = query
            try:
                self.queryClient.runSimple(qm)
            except TQueryError as qe:
                raise Exception(qe.message)

        def runQuery(self, query, qid):
            qm = QueryMessage()
            qm.query = query
            try:
                self.queryClient.run(qm, "localhost", getCallbackPort(), qid)
            except TQueryError as qe:
                raise Exception(qe.message)

        def getFirstTuple(self, query):
            qm = QueryMessage()
            qm.query = query
            try:
                ttuple = self.queryClient.getFirstTuple(qm)
            except TQueryError as qe:
                raise Exception(qe.message)
            return translate(ttuple)

        def clear(self, table):
            try:
                self.queryClient.clear(table)
            except TQueryError as qe:
                raise Exception(qe.message)

        def drop(self, table):
            try:
                self.queryClient.drop(table)
            except TQueryError as qe:
                raise Exception(qe.message)

        def getEnumId(self, kind, key):
            try:
                return self.queryClient.getEnumId(kind, key)
            except TQueryError as qe:
                raise Exception(qe.message)

        def getEnumKey(self, kind, i):
            try:
                return self.queryClient.getEnumKey(kind, i)
            except TQueryError as qe:
                raise Exception(qe.message)

        def getEnumKeyList(self, kind):
            try:
                return self.queryClient.getEnumKeyList(kind)
            except TQueryError as qe:
                raise Exception(qe.message)

        def gc(self):
            try:
                return self.queryClient.gc()
            except TQueryError as qe:
                raise Exception(qe.message)

    queryClient = connect()
    socialite = Socialite(queryClient)
    return socialite

socialite = _init()

class DelayedQuery:
    def __init__(self, query, filename, line):
        self.query = query
        self.queryid = hash(filename) ^ line
        self.queue = None 

    def first(self):
        return socialite.getFirstTuple(self.query)

    def __iter__(self):
        from Queue import Queue
        from callbackServer import registerQueryQueue

        self.queue = Queue()
        registerQueryQueue(self.queryid, self.queue)
        socialite.runQuery(self.query, self.queryid)
        return self

    def next(self):
        tup = self.queue.get()
        if tup == None:
            raise StopIteration
        return tup

def decl(query):
    """Declares SociaLite tables.
       e.g. decl("Student(int id, String name) indexby id.")
            decl("Friend(String name, (String friendName)) indexby name.")
    """
    run(query)

def run(query):
    """Runs SociaLite rules.
       e.g. run("FriendOfFriend(f1, ff) :- Friend(f1, f2), Friend(f2, ff).")
    """
    socialite.run(query)

def query(query):
    """Queries SociaLite tables.
       e.g. query("Friend(n1, n2)").first()
            for i, name in query("Student(i, name)"):
                print i, name
    """
    import inspect
    callerframerecord = inspect.stack()[1]
    frame = callerframerecord[0]
    info = inspect.getframeinfo(frame)
    filename = info.filename
    line = info.lineno
    query = "?-"+query+"."
    return DelayedQuery(query, filename, line)

def clear(table):
    socialite.clear(table)

def drop(table):
    socialite.drop(table)

def getEnumId(kind, i):
    return socialite.getEnumId(kind, i)

def getEnumKey(kind, i):
    return socialite.getEnumKey(kind, i)

def getEnumKeyList(kind):
    return socialite.getEnumKeyList(kind)

def gc():
    socialite.gc()
"""
@internal
def cwd(): return engine.cwd()
@internal
def chdir(path): engine.chdir(path)
@internal
def store(): engine.storeWorkspace()
@internal
def load(): engine.loadWorkspace()

@internal
def tables(verbose=0):
    status = engine.status(0)
    print status.getTableStatus()

@internal
def status(verbose=0):
    write = sys.stdout.write
    write("** SociaLite Runtime Status **\n")
    status = engine.status(verbose)
    write("Number of nodes: "+status.getNodeNum()+"\n")
    write("Free memory:\n")
    memStat = filter(lambda x:x, status.getMemStatus().split('\n'))
    memStat = ''.join(map(lambda x:'    '+x+'\n', memStat))
    memStat.rstrip(' ')
    write(memStat)

    write("Recent rules:\n")
    progStat = status.getProgress().split('\n')
    progStat = '    '+'\n    '.join(progStat)
    progStat.rstrip(' ')
    write(progStat)

@internal
def clear(name): engine.clearTable(name)

@internal
def indent(msg, width=4, indentFirst=True):
    if not msg: return msg
    tab1=''
    if indentFirst:tab1=' '*width
    tab=' '*width
    msg = tab1+msg.replace('\n','\n'+tab)
    return msg.rstrip(' ')

@internal
def _removeStackTrace(msg):
    if not msg: return msg
    magic="at socialite.dist.master.QueryListener."
    if msg.find(magic) >= 0:
        msg = msg[:msg.find(magic)].strip()
    magic="org.apache.hadoop.ipc.RemoteException:"
    if msg.find(magic) == 0:
        msg = msg[len(magic):].strip()
    return msg


class AsyncEngine:
    END = None
    def __init__(self, engine):
        self.engine = engine
        self.q = Queue(maxsize=16)
        self.reqThreads = []
        reqThreadNum = 2
        for i in xrange(reqThreadNum):
            t=Thread(target=self.asyncRequest, name="Async Request Thread")
            t.start()
            self.reqThreads.append(t)
        registerCleanupOnExit(self.cleanupReqThreads)

    def getTableRef(self, name):
        return self.engine.getTableRef(name)

    def cleanupReqThreads(self):
        try:
            #for t in self.reqThreads:
            #    self.q.put(self.END)
            for t in self.reqThreads:
                t._thread.interrupt()
        except:
            pass
            #print "Exception in cleanupReqThreads"

    def asyncRequest(self):
        try:
            while True:
                tup = self.q.get()
                if tup == self.END: break

                query, visitor, id, checker = tup
                try:
                    if visitor: self.engine.run(query, visitor, id)
                    else: self.engine.run(query)
                except Exception, e:
                    type, inst, tb = sys.exc_info()
                    errhead="Error while running:" 
                    print "\n"+errhead+indent(query, width=len(errhead), indentFirst=False)
                    print indent(_removeStackTrace(inst.getMessage()))

                    if visitor:
                        visitor.raiseError(inst)

                checker.done=True
                self._notify(checker.cv)
        except JavaInterruptedException:
            pass

    def _notify(self, cv):
        cv.acquire()
        try: cv.notify()
        finally: cv.release()
    def _wait(self, cv, timeout=None):
        cv.acquire()
        try: cv.wait(timeout)
        finally: cv.release()

    def run(self, program, visitor=None, id=None):
        done=[]
        class Checker(object): pass
        checker = Checker()
        checker.cv = Condition()
        checker.done=False
        self.q.put((program, visitor, id, checker))
        self._wait(checker.cv, 3)
        if not checker.done and not visitor:
            print "... still running the query. Type status() to see the progress."

    def cleanupTableIter(self, id):
        self.engine.cleanupTableIter(id)
    def cwd(self):
        self.engine.cwd()
    def load(self):
        self.engine.load()
    def status(self, verbose=0):
        return self.engine.status()
    def chdir(self, path):
        self.engine.chdir(path)
    def shutdown(self):
        self.engine.shutdown()
    def update(self, func):
        self.engine.update(func)
    def runGc(self):
        self.engine.runGc()

@internal
def returns(*types):
    def _wrapper(f):
        if len(types) == 1:
            f.returns = types[0]
        else: 
            f.returns = types
        engine.update(f)
        return f
    return _wrapper

@internal
def passVars(*vars):
    tmp=[]
    for v in vars:
        if type(v) == type(0):
            tmp.append(str(v))
        elif type(v) == type(0L):
            tmp.append(str(v)+"L")
        elif type(v) == type(0.0):
            tmp.append(str(v))
        elif type(v) == type(""):
            v = v.replace('"', '\\"')
            tmp.append('"'+v+'"')
        elif type(v) == type(u""):
            v = v.replace('"', '\\"')
            tmp.append('"'+v+'"')
        elif isinstance(v , Utf8):
            v = v.toString().replace('"', '\\"')
            tmp.append('u"'+v+'"')
        else:
            raise SociaLiteException("Only numbers and Strings can be passed to SociaLite queries:"+
                                        str(v)+" is "+str(type(v)))
    return tuple(tmp)

class IdFactory:
    def __init__(self):
        import java.util.concurrent.atomic.AtomicInteger as AtomicInt
        self.nextid = AtomicInt()
    def next(self):
        nextid = self.nextid.getAndIncrement()
        return nextid

class TableIterator(QueryVisitor):
    END = None
    idFactory = IdFactory()

    def __init__(self, engine, query):
        self.engine = engine
        self.query = query
        self.q = Queue(maxsize=1024)
        self.finished = False
        self.cleanupIterDone = AtomicBool()
        self.error = None
        self.thread = None
        self.id = self.idFactory.next()

    def startThread(self):
        if self.thread: return
        self.thread = t = Thread(target=self.run, name="Table Iterator Thread query="+self.query)
        registerCleanupOnExit(self.cleanupIterThread, False)

        t.start()

    def __del__(self):
        unregisterCleanupOnExit(self.cleanupIterThread)
        self.cleanupIterThread()

    def cleanupIterThread(self):
        try:
            if not self.cleanupIterDone.compareAndSet(False, True):
                return

            self.finished = True
            self.engine.cleanupTableIter(self.id)
            self.thread._thread.interrupt()
        except:
            pass
            #print "Exception in cleanupIterThread"

    def visit(self, t):
        if self.finished: return False

        if isinstance(t, Tuple):
            cols = []
            for i in xrange(t.size()):
                cols.append(t.get(i))
            self.q.put(tuple(cols))
        else: self.q.put(t)
        return True

    def finish(self):
        if self.finished: return

        self.q.put(self.END)

    def raiseError(self, error):
        self.error = error
        self.finish()

    def run(self):
        try:
            self.engine.run(self.query, self, self.id)
        except SociaLiteException, e1:
            e1.printStackTrace()
            self.q.put(self.END)
            raise e1
        except InterruptedException, e3:
            return
        except Exception, e2:
            e2.printStackTrace()
            self.q.put(self.END)
            raise e2

    def __next__(self):
        if not self.thread: 
            self.startThread()

        if self.finished or self.error:
            raise StopIteration
 
        v = self.q.get()
        if self.error:
            self.finished = True
            raise self.error

        if v == self.END:
            self.finished = True
            raise StopIteration
        return v

    def next(self):
        n = self.__next__()
        return n
        
    def isEmpty(self):
        try: 
            self.next()
            return False
        except StopIteration:
            return True

    def __iter__(self):
        self.startThread()
        return self

"""
