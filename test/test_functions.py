"""
  Testing functions in a query.
"""

import socialite as s
import unittest

class TestFunctions(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
        s.decl("""Foo(int a, int b) groupby(1).""")
        s.decl("""Path(int a, int b) groupby(1).""")
        s.decl("""Edge(int s, (int t)).""")


    def setUp(self):
        s.clear("Foo")
        s.clear("Path")
        s.clear("Edge")

    def test_builtin_split(self):
        s.run("""Str(String a, String b, String c).
                 Str(a,b,c) :- (a,b,c) = $split("11::::22::33", "::", 3). """)

        a,b,c = s.query("Str(a,b,c)").first()
        self.assertEqual((a,b,c), ("11","","22::33"))

        s.clear("Str")

        s.run("""Str(a,b,c) :- (a,b,c) = $split("AA::BB::CC ::DD", "::", 3).""")
        a,b,c = s.query("Str(a,b,c)").first()
        self.assertEqual((a,b,c), ("AA","BB","CC ::DD"))

    def test_min(self):
        print "start test_min"
        s.run("Edge(s,t) :- s=0, t=1.")
        s.run("Edge(s,t) :- s=0, t=2.")
        s.run("Edge(s,t) :- s=0, t=3.")
        s.run("Edge(s,t) :- s=1, t=2.")
        s.run("Edge(s,t) :- s=1, t=3.")
        s.run("Edge(s,t) :- s=2, t=4.")
        s.run("Edge(s,t) :- s=4, t=5.")

        s.run("""Path(n,$min(d)) :- n=0, d=0;
                                 :- Path(s, d1), Edge(s,n), d=d1+1.""")
        self.assertEqual(s.query("Path(5, d)").first(), (5, 3))

def main():
    unittest.main()

if __name__ == '__main__':
    main()
