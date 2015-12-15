import os
import sys

from pysoc import *
from org.python.core import Py, CompilerFlags, CompileMode
from org.python import core
import imp
import SociaLite

def setSocialiteVars(locals):
    # setting SociaLite specific variables
    locals[getModuleVar()] = SociaLite
    locals["socialite"] = SociaLite
    for k in SociaLite.__all__:
        locals[k] = getattr(SociaLite, k)
    return;

import sys
def addMod(name):
    module = sys.modules.get(name)
    if not module:
        module =  core.imp.addModule(name)
        sys.modules[name] = module
    return module
 
def loadMod(name, src, path):
    module = addMod(name)
    code = pycompile(src, path)
    module.__file__ = path
    locals = module.__dict__
    setSocialiteVars(locals)
    locals["__name__"] = name
    locals["__file__"] = path
    core.imp.createFromCode(name, code, path)
    #Py.runCode(code, locals, locals)
    return module

class PyCompile:
    def __init__(self):
        self.cflags = CompilerFlags()
    def __call__(self, src, filename):
        code = Py.compile_flags(src, filename, CompileMode.exec, self.cflags)
        return code
pycompile = PyCompile()

class __Importer(object):
    def __init__(self, path):
        self.__path = path
    def find_module(self, fullname, path=None):
        path = fullname.split('.')
        filename = path[-1]
        path = path[:-1]
        pyfile = os.path.join(self.__path, *(path + [filename + '.py']))
        if os.path.exists(pyfile):
            return self
        else: return None # abort! py-file does not exist
    def load_module(self, fullname):
        path = fullname.split('.')
        path[-1] += '.py'
        filename = os.path.join(self.__path, *path)

        module = sys.modules.get(fullname)
        if not module: 
            src = compiler.compile(open(filename, 'rb').read())
            module = imp.new_module(fullname)
            sys.modules[fullname] = module
            #module = sys.modules[fullname] = addMod(fullname)
            setSocialiteVars(module.__dict__)
            locals = module.__dict__
            locals["__name__"] = fullname
            locals["__file__"] = filename
            if src[0]=='#':
                src = removeEncoding(src)
            exec src in module.__dict__
        return module;

def removeEncoding(src):
    parts = src.split('\n', 3)
    hasEncoding = False
    for i in xrange(len(parts)-1):
        tmp = parts[i]
        if len(tmp)==0: continue
        if tmp[0]=='#' and tmp.find("coding")>=0:
            hasEncoding = True
            parts[i] = '#'

    if hasEncoding:
        src = '\n'.join(parts)
    return src

class SocialiteImporter(object):
    def __init__(self):
        self.__importers = {}
    def find_module(self, fullname, path):
        for _path in sys.path:
            if _path not in self.__importers:
                try:
                    self.__importers[_path] = __Importer(_path)
                except:
                    self.__importers[_path] = None
            importer = self.__importers[_path]
            if importer is not None:
                loader = importer.find_module(fullname, path)
                if loader is not None:
                    return loader
        else:
            return None
