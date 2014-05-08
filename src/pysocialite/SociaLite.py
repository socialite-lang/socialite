import socialite.engine.LocalEngine as LocalEngine
import socialite.engine.ClientEngine as ClientEngine
import socialite.engine.Config as Config
import socialite.tables.QueryVisitor as QueryVisitor
import socialite.tables.Tuple as Tuple
import socialite.util.SociaLiteException as SociaLiteException
import socialite.functions.PyInterp as PyInterp
import socialite.type.Utf8 as Utf8

from threading import Thread, InterruptedException, Condition, Lock
from Queue import Queue, Empty, Full

__all__ = ['returns', 'cwd', 'chdir', 'store', 'load', 'tables', 'status', 'engine', 'SociaLiteException', 'double']

double = float
import sys
try: PyInterp.set(sys._jy_interpreter)
except:
    sys.stderr.write("Cannot access interpreter object via sys._jy_interpreter\n")

def internal(f):
    f.internal = True
    return f
internal.internal = True

isClusterEngine = False
engine = None
@internal
def init(cpu=None, dist=False, interactive=False, verbose=0):
    global engine, isClusterEngine
    if engine==None:
        if dist:
            engine = ClientEngine()
            isClusterEngine = True
        else:
            conf = None
            if cpu == None: 
                conf = Config.par()
                conf.setDebugOpt("DijkstraOpt", False)
            else: 
                conf = Config.par(cpu)
            if verbose: conf.setVerbose()
            engine = LocalEngine(conf)
    if interactive:
        engine = AsyncEngine(engine)

cleanupFuncs =[]
cleanupFuncsLock = Lock()
@internal
def registerCleanupOnExit(f):
    try:
        cleanupFuncsLock.acquire()
        cleanupFuncs.append(f)
    finally:
        cleanupFuncsLock.release()
@internal
def unregisterCleanupOnExit(f):
    try:
        cleanupFuncsLock.acquire()
        cleanupFuncs.remove(f)
    finally:
        cleanupFuncsLock.release()

@internal
def cleanupOnExit():
    for f in cleanupFuncs:
        f()

    import time
    time.sleep(0.1)
    engine.shutdown()
    time.sleep(0.1)

@internal
def cwd():
    return engine.cwd()
@internal
def chdir(path):
    engine.chdir(path)
@internal
def store():
    engine.storeWorkspace()
@internal
def load():
    engine.loadWorkspace()

import sys
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
def clear(name):
    engine.clearTable(name)

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
            t=Thread(target=self.asyncRequest)
            t.setDaemon(True)
            t.start()
            self.reqThreads.append(t)
        registerCleanupOnExit(self.cleanupReqThreads)

    def cleanupReqThreads(self):
        try:
            for t in self.reqThreads:
                self.q.put(self.END)
                #t._thread.interrupt()
        except:
            print "Exception in cleanupReqThreads"

    def asyncRequest(self):
        while True:
            tup = self.q.get()
            if tup == self.END: break

            query, visitor, id, checker = tup
            try:
                if visitor: self.engine.run(query, visitor, id)
                else: self.engine.run(query)
            except:
                type, inst, tb = sys.exc_info()
                errhead="Error while running:" 
                print "\n"+errhead+indent(query, width=len(errhead), indentFirst=False)
                print indent(_removeStackTrace(inst.getMessage()))

                if visitor:
                    visitor.raiseError(inst)

            checker.done=True
            self._notify(checker.cv)

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
        elif type(v) == type(0.0):
            tmp.append(str(v))
        elif type(v) == type(""):
            v = v.replace('"', '\\"')
            tmp.append('"'+v+'"')
        elif isinstance(v , Utf8):
            v = v.toString().replace('"', '\\"')
            tmp.append('u"'+v+'"')
        else:
            raise Exception("Only numbers and Strings can be passed to SociaLite queries")
    return tuple(tmp)

import java.util.concurrent.atomic.AtomicInteger as AtomicInt
class IdFactory:
    def __init__(self):
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
        self.error = None
        self.thread = None
        self.id = self.idFactory.next()

    def startThread(self):
        self.thread = t = Thread(target=self.run)
        registerCleanupOnExit(self.cleanupOnServer)

        #t.setDaemon(True)
        t.start()

    def __del__(self):
        unregisterCleanupOnExit(self.cleanupOnServer)
        self.cleanupOnServer()

    def cleanupOnServer(self):
        try:
            if self.finished: return

            self.finished = True
            #self.thread._thread.interrupt()
            self.engine.cleanupTableIter(self.id)

            while True:
                try:
                    try: self.q.get_nowait()
                    except: pass

                    self.q.put_nowait(self.END)
                    break
                except: pass

            #self.thread.join()
        except:
            print "Exception in cleanupOnServer"

    def visit(self, t):
        if self.finished: return False

        if isinstance(t, Tuple):
            cols = []
            for i in xrange(t.size()):
                cols.append(t.get(i))
            self.q.put(tuple(cols))
        else:
            self.q.put(t)
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
            self.q.put(self.END)
            raise e1
        except Exception, e2:
            self.q.put(self.END)
            raise e2
        except InterruptedException, e3:
            return

    def __next__(self):
        return self.next()

    def next(self):
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
        
    def __iter__(self):
        self.startThread()
        return self


