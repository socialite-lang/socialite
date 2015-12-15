import SociaLite

import sys
from SociaLite import SociaLiteException

from impSocialite import SocialiteImporter
import __builtin__

from code import InteractiveConsole
import org.python.util.PythonInterpreter as PythonInterpInJava

class PythonInterpAdapter(PythonInterpInJava):
    def __init__(self, socialite):
        import sys
        self.socialite = socialite
        try:
            self.realInterp = sys._jy_interpreter
        except:
            print "Attribute _jy_interpreter does not exist in sys module"
            self.realInterp = None
            
    def getLocals(self):
        return self.socialite.locals
    def get(self, name):
        return self.socialite.locals[name]
    def getSystemState(self):
        from org.python.core import Py
        if not self.realInterp: return Py.getSystemState()
        return self.realInterp.getSystemState()

class SociaLiteConsole(InteractiveConsole):
    def __init__(self, cpu=None, verbose=False):
        InteractiveConsole.__init__(self)
        self.filename="<stdin>"

        self.inQuery = False # True if the shell is in SociaLite query
        self.compiler = None

        self.declBegin = None
        self.declEnd = None

        self.locals={}

        from impSocialite import setSocialiteVars
        setSocialiteVars(self.locals)
        self.locals["__name__"] = "__main__"

        self.adapter = PythonInterpAdapter(self)
        import socialite.functions.PyInterp as PyInterp
        PyInterp.set(self.adapter)

    def initLocals(self, _locals=None):
        if _locals:
            for k,v in _locals.iteritems():
                self.locals[k] = v

    def asyncImportPyparsing(self):
        def importPyparsing():
            import time
            time.sleep(0.001)
            import pyparsing

        from threading import Thread
        t=Thread(target=importPyparsing)
        t.start()

    def runsource(self, source, filename="<stdin>", symbol="single"):
        if not self.compiler:
            from pysoc import compiler
            self.compiler = compiler

        source = self.compiler.compile(source)
        try :
            return InteractiveConsole.runsource(self, source, filename, symbol)
        except SystemExit, e:
            sys.exit(0)
        except SociaLiteException, e:
            print e.getMessage()
        except:
            import traceback
            try:
                tp, value, tb = sys.exc_info()
                sys.last_type = tp
                sys.last_value = value
                sys.last_traceback = tb
                tblist = traceback.extract_tb(tb)
                del tblist[:1]
                list = traceback.format_list(tblist)
                if list:
                    list.insert(0, "Traceback (most recent call last):\n")
                list[len(list):] = traceback.format_exception_only(tp, value)
            finally:
                tblist = tb = None
            map(sys.stderr.write, list)


    def hasDeclBegin(self, line):
        if line.lstrip().find("`") == 0:
            return True
        return False

    def hasDeclEnd(self, line):
        l = line.rstrip()
        if l.find("`") == len(l)-1:
            return True

        return False

    def __hasDeclBegin(self, line):
        if not self.declBegin:
            import pyparsing as p
            self.declBegin = p.stringStart+p.Literal("`")
            self.declBegin.ignore(p.pythonStyleComment)
            self.declBegin.ignore(p.quotedString)

        if self.declBegin.searchString(line.strip()):
            return True
        return False

    def __hasDeclEnd(self, line):
        if not self.declEnd: 
            import pyparsing as p
            self.declEnd = p.Literal("`") + p.stringEnd
            self.declEnd.ignore(p.pythonStyleComment)
            self.declEnd.ignore(p.quotedString)

        if self.declEnd.searchString(line.strip()):
            return True
        return False

    def interact(self, banner=None):
        try:
            InteractiveConsole.interact(self, banner)
        except KeyboardInterrupt:
            if self.inQuery: 
                self.inQuery = False
                self.buffer = []
            print "Enter quit() or Ctrl-D to exit"

    def push(self, line):
        if self.inQuery:
            if self.hasDeclEnd(line):
                self.inQuery= False
                more = InteractiveConsole.push(self, line)
                return more
            elif line.find("`") >= 0:
                exc = 'Traceback (most recent call last):\n' + \
                      '  File "<stdin>", line 1, in <module>\n' + \
                      '  Cannot have both Python code and SociaLite query in one line\n'
                print exc
                self.buffer = []
                self.inQuery = False
                return False
            else:
                if line.strip():
                    self.buffer.append(line)
                return True
        else:
            if self.hasDeclBegin(line) and not self.hasDeclEnd(line):
                self.inQuery = True
                self.buffer.append(line)
                return True

        more = InteractiveConsole.push(self, line)
        return more
            
def getBanner():
    banner = """
SociaLite 0.8.0-alpha
Type "help" for more information.
Type "quit()" to quit."""
    return banner

def interact(verbose=0):
    sys.path.insert(0, '')

    banner = getBanner()
    console = SociaLiteConsole(verbose=verbose)
    console.interact(banner)

def run_files(args, verbose=0, inspect=False):
    sys.argv = args
    filename = args[0]
    args = args[1:]

    import java.io.File as File
    abspath = File(filename).getAbsolutePath()
    path = str(abspath[0:abspath.rindex(File.separator)])
    sys.path.insert(0, path)

    program=open(filename).read()
    from pysoc import compiler
    src = compiler.compile(program)

    from impSocialite import addMod, loadMod, setSocialiteVars
    sys.modules.pop("__main__", None)

    class FakeConsole:
        def __init__(self):
            self.locals = None

    console = FakeConsole()
    adapter = PythonInterpAdapter(console)
    import socialite.functions.PyInterp as PyInterp
    PyInterp.set(adapter)

    mod = addMod("__main__")
    setSocialiteVars(mod.__dict__)
    console.locals = mod.__dict__
    try:
        import java.lang.System as Sys
        mod = loadMod("__main__", src, filename)
        #exec src in locals
    except SystemExit:
        sys.exit(0)
    except:
        # adapted from code.py::InteractiveInterpreter::showtraceback
        import traceback
        try:
            tp, value, tb = sys.exc_info()
            sys.last_type = tp
            sys.last_value = value
            sys.last_traceback = tb
            tblist = traceback.extract_tb(tb)
            del tblist[:1]
            list = traceback.format_list(tblist)
            if list:
                list.insert(0, "Traceback (most recent call last):\n")
            list[len(list):] = traceback.format_exception_only(tp, value)
        finally:
            tblist = tb = None
        map(sys.stderr.write, list)
        sys.exit(1)
 
    if inspect:
        console = SociaLiteConsole(verbose=verbose)
        console.initLocals(mod.__dict__)
        console.interact("")

def installKeyboardInterruptHandler():
    def handler(signum, frame):
        print "Enter quit() or Ctrl-D to exit"

    import signal
    signal.signal(signal.SIGINT, handler)

def show_cluster_status():
    SociaLite.status()

def main():
    usage ="usage: socialite [options] [script] [script args]"
    from optparse import OptionParser
    parser=OptionParser(usage)
    parser.add_option("-v", "--verbose", action="store_true", dest="v",
                      help="Run SociaLite script with verbose level=1")
    parser.add_option("-c", "--cpu", type="int", default=None, dest="cpu", 
                      help="Set # of cpu to be used")
    parser.add_option("-i", action="store_true", dest="inspect",
                      help="inspect interactively after running script")
    parser.add_option("-d", "--dist", action="store_true", dest="dist",
                      help="Run SociaLite script on a distributed cluster (see conf/ for cluster info).  ")
 
    opts, args = parser.parse_args()

    interactive = False
    if len(args)==0:
        interactive = True

    if opts.cpu==None: SociaLite.init(dist=opts.dist, interactive=interactive, verbose=opts.v)
    else: SociaLite.init(opts.cpu, dist=opts.dist, interactive=interactive, verbose=opts.v)

    if opts.dist: show_cluster_status()

    import atexit
    atexit.register(SociaLite.cleanupOnExit)

    sys.meta_path.insert(0, SocialiteImporter())

    if interactive: 
        installKeyboardInterruptHandler()
        interact(verbose=opts.v)
    else:  
        run_files(args, verbose=opts.v, inspect = opts.inspect)

if __name__=='__main__':
    main()
