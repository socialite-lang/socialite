import socialite.engine.LocalEngine as LocalEngine
import socialite.engine.ClientEngine as ClientEngine
import socialite.engine.Config as Config
import socialite.tables.QueryVisitor as QueryVisitor
import socialite.tables.Tuple as Tuple
import socialite.util.SociaLiteException as SociaLiteException
import socialite.type.Utf8 as Utf8
import sys
import java.util.concurrent.atomic.AtomicBoolean as AtomicBool
import java.lang.InterruptedException as JavaInterruptedException
from threading import Thread, InterruptedException, Condition, Lock
from Queue import Queue


__all__ = ['returns', 'cwd', 'chdir', 'store', 'load', 'tables', 'status', 'engine', 'SociaLiteException', 'double']
__doc__ = """
Useful functions:
 tables() : shows declared SociaLite tables
 status() : shows runtime status of SociaLite

Use backtik(`) to run SociaLite queries
e.g. `Friend(String i, (String f)).`  # declares a table Friend having two columns.
     `Friend(a,b) :- a="John Smith", b="Jane Doe".` # inserts a tuple into Friend.
     for i, f in `Friend(i, f)`:  # iterates over tuples in Friend
        print i, f

Type help(socialite.examples) to see more SociaLite query examples.
"""

examples="""
`Edge(int i, (int f)).`         # declares Edge table (with nested 2nd column).
`Edge(int i:0..1000, (int f)).` # Values of 1st column of Edge is between 0 and 1000

`Edge(s, t) :- l=$read("edges.txt"), # $read returns lines in edges.txt
               (a,b)=$split(l, "\\t"),# splits a string with a delimiter, tab here.
               s=$toInt(a),          # Casting a,b into primitive int.
               t=$toInt(b).`

                  
`Foaf(i, f) :- Friend(i,x), Friend(x,f).` # joins Friend table with itself 
                                          # to compute friends-of-friends 
                                          # and store the result in Foaf.

for i, f in `Foaf(i, f)`:  # iterates over tuples in Foaf 
   print i, f

`FriendCnt(int i, int cnt) groupby(1).  # we will apply $inc to the 'cnt' column,
                                        # which requires groupby with one column (column 'i').
 FriendCnt(i, $inc(1)) :- Friend(i,f).` # counting the # of friends for each person.

@returns(int)             # annotates function return type 
def randInt(s, e):        # to access it from SociaLite queries
    import random as r
    return r.randint(s, e)

# Computes average friend counts for randomly selected samples.
`SampleAvg(int i:0..0, Avg avg).
 SampleAvg(0, $avg(cnt)) :- i=$randInt(0,100), FriendCnt(i, cnt).`

"""

# Initialize useful functions (help, quit, ...)
import __builtin__
class _Helper(object):
    def __init__(self):
        global examples
        self.socialite = sys.modules[__name__]
        self.socialiteExamples = examples

    def __repr__(self):
        return "Type help(socialite) for help on SociaLite, " \
               "or help(object) for help about object." 
    def __call__(self, *args, **kwds):
        if args and args[0]==self.socialite:
            print self.socialite.__doc__
            return
        elif args and args[0]==self.socialiteExamples:
            print self.socialite.examples
            return
        import pydoc
        return pydoc.help(*args, **kwds)

def sethelper():
    __builtin__.socialite = sys.modules[__name__]
    __builtin__.help = _Helper()

import os
def setquit():
    """Define new built-ins 'quit' and 'exit'.
    These are simply strings that display a hint on how to exit.

    """
    if os.sep == ':':
        eof = 'Cmd-Q'
    elif os.sep == '\\':
        eof = 'Ctrl-Z plus Return'
    else:
        eof = 'Ctrl-D (i.e. EOF)'

    class Quitter(object):
        def __init__(self, name):
            self.name = name
        def __repr__(self):
            return 'Use %s() or %s to exit' % (self.name, eof)
        def __call__(self, code=None):
            # Shells like IDLE catch the SystemExit, but listen when their
            # stdin wrapper is closed.
            try:
                sys.stdin.close()
            except:
                pass
            raise SystemExit(code)
    __builtin__.quit = Quitter('quit')
    __builtin__.exit = Quitter('exit')

double = float

def internal(f):
    f.internal = True
    return f
internal.internal = True

isInteractive = False
isClusterEngine = False
engine = None
@internal
def init(cpu=None, dist=False, interactive=False, verbose=None):
    verbose = True
    global engine, isClusterEngine, isInteractive
    if engine==None:
        if dist:
            engine = ClientEngine()
            isClusterEngine = True
        else:
            conf = None
            if cpu == None: conf = Config.par()
            else: conf = Config.par(cpu)

            if verbose: conf.setVerbose()
            engine = LocalEngine(conf)
    if interactive:
        isInteractive = True
        engine = AsyncEngine(engine)

cleanupFuncsBefore =[]
cleanupFuncsAfter =[]
cleanupLock = Lock()
@internal
def registerCleanupOnExit(f, before=True):
    try:
        cleanupLock.acquire()
        if before: cleanupFuncsBefore.append(f)
        else: cleanupFuncsAfter.append(f)
    finally:
        cleanupLock.release()
@internal
def unregisterCleanupOnExit(f):
    try:
        cleanupLock.acquire()
        cleanupFuncsBefore.remove(f)
        cleanupFuncsAfter.remove(f)
    finally:
        cleanupLock.release()

cleanupDone = AtomicBool()
import time

@internal
def cleanupOnExit():
    if cleanupDone.compareAndSet(False, True):
        for f in cleanupFuncsBefore: f()

        #time.sleep(0.02)
        engine.shutdown()

        for f in cleanupFuncsAfter: f()
        #time.sleep(0.02)

def install_funcs():
    sethelper()
    setquit()       
    import atexit
    atexit.register(cleanupOnExit)

install_funcs()


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
                except:
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


