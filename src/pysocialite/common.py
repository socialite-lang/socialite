# WIP

import socialite.engine.LocalEngine as LocalEngine
import socialite.engine.ClientEngine as ClientEngine
import socialite.engine.Config as Config
import sys

isInteractive = False
isClusterEngine = False
engine = None

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
    #if interactive:
    #    isInteractive = True
    #    engine = AsyncEngine(engine)

init()

_getframe = sys._getframe
def decl(rule):
    frame = _getframe().f_back
    _locals = frame.f_locals
    _globals = frame.f_globals
    if rule.find('$') >= 0:
        rule = rule # transform $var
    engine.run(rule)


def select(table):
    frame = _getframe().f_back
    _locals = frame.f_locals
    _globals = frame.f_globals
    if rule.find('$') >= 0:
        rule = rule # transform $var
    engine.run(rule)


