"""
  Testing functions in a query.
"""

import socialite as s
import unittest

class TestFunctions(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, int b) groupby(1).""")
        s.decl("""Bar(int a, int b, int c) groupby(1).""")
        s.decl("""Path(int a, int b) groupby(1).""")
        s.decl("""Edge(int s, (int t)).""")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def setUp(self):
        s.clear("Foo")
        s.clear("Path")
        s.clear("Edge")
        s.clear("Bar")

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

    def test_aggr(self):
        s.run("Foo(a,$min(b)) :- a=10, b=20.")
        s.run("Foo(a,$min(b)) :- a=10, b=30.")
        s.run("Foo(a,$min(b)) :- a=10, b=10.")
        s.run("Foo(a,$min(b)) :- a=20, b=100.")
        s.run("Foo(a,$min(b)) :- a=20, b=10.")

        expected = [(10,10), (20, 10)]
        result = []
        for a,b in s.query("Foo(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_multiple_aggr(self):
        s.run("Bar(a,$min(b), $max(c)) :- a=10, b=10, c=10.")
        s.run("Bar(a,$min(b), $max(c)) :- a=10, b=1, c=100.")
        s.run("Bar(a,$min(b), $max(c)) :- a=10, b=100, c=1.")

        expected = [(10,1, 100)]
        result = []
        for a,b,c in s.query("Bar(a,b,c)"):
            result.append((a,b,c))

        self.assertEqual(sorted(result), sorted(expected))

    def test_aggr_trailing_args(self):
        s.run("Bar(a,$min(b), c) :- a=10, b=10, c=10.")
        s.run("Bar(a,$min(b), c) :- a=10, b=10, c=20.")
        s.run("Bar(a,$min(b), c) :- a=10, b=1, c=1.")

        expected = [(10,1, 1)]
        result = []
        for a,b,c in s.query("Bar(a,b,c)"):
            result.append((a,b,c))

        self.assertEqual(sorted(result), sorted(expected))

class TestFunctions2(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, (int b, int c)) groupby(2).""")
        s.decl("""Bar(int a, (int b, int c, int d)) groupby(2).""")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def setUp(self):
        s.clear("Foo")
        s.clear("Bar")

    def test_aggr(self):
        s.run("Foo(a,b,$min(c)) :- a=10, b=10, c=30.")
        s.run("Foo(a,b,$min(c)) :- a=10, b=10, c=20.")
        s.run("Foo(a,b,$min(c)) :- a=10, b=10, c=10.")
        s.run("Foo(a,b,$min(c)) :- a=10, b=20, c=50.")
        s.run("Foo(a,b,$min(c)) :- a=10, b=20, c=20.")

        expected = [(10,10,10), (10,20,20)]
        result = []
        for a,b,c in s.query("Foo(a,b,c)"):
            result.append((a,b,c))
        self.assertEqual(sorted(result), sorted(expected))

    def test_multiple_aggr(self):
        s.run("Bar(a,b,$min(c), $max(d)) :- a=10, b=10, c=20, d=10.")
        s.run("Bar(a,b,$min(c), $max(d)) :- a=10, b=10, c=10, d=20.")
        s.run("Bar(a,b,$min(c), $max(d)) :- a=10, b=10, c=10, d=30.")

        expected = [(10,10,10,30)]
        result = []
        for a,b,c,d in s.query("Bar(a,b,c,d)"):
            result.append((a,b,c,d))

        self.assertEqual(sorted(result), sorted(expected))

    def test_aggr_trailing_args(self):
        s.run("Bar(a,b,$min(c), d) :- a=10, b=10, c=10, d=10.")
        s.run("Bar(a,b,$min(c), d) :- a=10, b=10, c=0, d=20.")

        expected = [(10,10,0,20)]
        result = []
        for a,b,c,d in s.query("Bar(a,b,c,d)"):
            result.append((a,b,c,d))

        self.assertEqual(sorted(result), sorted(expected))


def main():
    unittest.main()

if __name__ == '__main__':
    main()
