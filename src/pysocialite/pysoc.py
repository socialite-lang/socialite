import time
import pysocialite.Preprocess as Preprocess

__all__ = ["compiler", "compileStr", "getModuleVar"]

def getUID():
    return int(time.time() * 100 % 100000)

_moduleVar = "socialite"+str(getUID())
def getModuleVar():
    return _moduleVar

def getEngineVar():
    return getModuleVar()+".engine" # see SociaLite.py

def getPassVarsFunc():
    return getModuleVar()+".passVars" # see SociaLite.py

def getIteratorClass():
    return getModuleVar()+".TableIterator" # see SociaLite.py

def getPySociaLitePrefix():
    socialiteEngineCode="""
# importing SociaLite engine
import SociaLite as %s

# user code follows here.\n""" % getModuleVar()
    return socialiteEngineCode

### Tokens used for extracting SociaLite queries
#_ws = ' \t'
#ParserElement.setDefaultWhitespaceChars(_ws)
#
#ident = Word(alphas+"_", alphanums+"_")
#
##EOL = LineEnd()  #.suppress()
##SOL = LineStart().leaveWhitespace()
#
##backtik = Literal("`")
#lparen = Literal("(")
##rparen = Literal(")")
##lbracket = Literal("[")
##rbracket = Literal("]")
##comma = Literal(",")
#dot = Literal(".")
##ws = ZeroOrMore(White(_ws))
#dollar = Literal("$")

class Compiler:
    def __init__(self):
        self._pythonVar = None
        self.varNames = []
        Preprocess.setSocialiteModule(getModuleVar())

    def pythonVar(self):
        if not self._pythonVar:
            from pyparsing import (ParserElement, Word, alphas, alphanums,
                                   Literal, Suppress, FollowedBy)
            _ws = ' \t'
            ParserElement.setDefaultWhitespaceChars(_ws)
            ident = Word(alphas+"_", alphanums+"_")
            lparen = Literal("(")
            dot = Literal(".")
            dollar = Literal("$")

            self._pythonVar = Suppress(dollar) + ident + ~FollowedBy((dot+ident) | lparen)
            self._pythonVar.setParseAction(self.onPythonVar)
        return self._pythonVar

    def compile(self, src):
        gen=Preprocess.run(src)
        return gen

    def processPythonVars(self, query):
        query = '('+query+')'

        tmp = query
        if tmp.find("$") >= 0: tmp = self.pythonVar().transformString(query)
        if self.varNames:
            query = ''.join([tmp, "%"+getPassVarsFunc()+"(", ','.join(self.varNames), ")"])
        else: query = tmp

        for i in xrange(len(self.varNames)):
            self.varNames.pop()
        return query

    def onPythonVar(self, inputStr, loc, tokens):
        varName = ''.join(tokens)
        self.varNames.append(varName)
        return "%s"

compiler = Compiler()

def _compile(src):
    gen = compiler.compile(src)
    return gen

testStr = """
`Foo(int a, int b:1..$N, int c).
Baz(int a, int b, (int c)). `

for i in range(10):
    foo()
    bar(a, b, c)

`Foo($i, a, b) :- l=$Read("data/data.txt"),(s1,s2)=$Split(line, "\t"), a=$ToInt(s1), b=$ToInt(s2).
Qux(a, b, $i) :- Bar(a, b, d), Baz(b, d, c).`

for i, j in `Foo(i, j, _)`:
    print i, j

""" 


import os

def compileStr(src, prefix=True):
    src="\n"+src+"\n"

    code = ""
    if prefix:
        code += getPySociaLitePrefix()
    code += _compile(src)

    #assert code[0]=="\n" and code[len(code)-1]=="\n"
    if code[0]=='\n': code = code[1:]
    if code[len(code)-1]=='\n': code = code[:-1]
    return code

def main():
    print "testing with..."
    print "==============================================="
    print testStr 
    compiled = compileStr(testStr, False)
    print "==============================================="
    print compiled
    
if __name__=='__main__':
    main()
