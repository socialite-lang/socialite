"""
  Testing join operations.
"""

import socialite as s
import unittest

class TestDynamicTable(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, int b) indexby a.""")
        s.decl("""Bar(int a, int b) indexby a.""")
        s.decl("""Baz(int a, int b) indexby a, indexby b.""")
        s.decl("""Qux(int a, int b) indexby b.""")
        s.decl("""Quux(int a, int b) sortby b.""")
        s.decl("""Corge(int a, int b) groupby(1).""")

        cls.initTables()

    @classmethod
    def initTables(cls):
        s.run("""Foo(a, b) :- a=$range(0, 10), b=42.""")
        s.run("""Foo(a, b) :- a=$range(10, 20), b=41.""")
        s.run("""Bar(a, b) :- a=42, b=0.""")
        s.run("""Bar(a, b) :- a=43, b=1.""")
        s.run("""Baz(a, b) :- a=42, b=0.""")
        s.run("""Baz(a, b) :- a=43, b=1.""")
        for i in xrange(10):
            s.run("""Quux(a, b) :- a={a}, b={b}.""".format(a=i, b=i))

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def setUp(self):
        s.clear("Qux")

    def test_simple_iteration(self):
        s.run("Qux(a, b) :- Foo(a, b).")
        expected = [(a,42) for a in xrange(0, 10)]
        expected.extend([(a,41) for a in xrange(10, 20)])
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_single_index(self):
        s.run("Qux(a, b) :- Foo(a, c), Bar(c, b).")
        expected = [(a,0) for a in xrange(0, 10)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_multi_index(self):
        s.run("Qux(a, b) :- Foo(a, c), Baz(c, b).")
        expected = [(a,0) for a in xrange(0, 10)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_multi_index2(self):
        s.run("Qux(a, c) :- Foo(a, c), Baz(c, a).")
        expected = [(0,42)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_sortby(self):
        s.run("Qux(a,b) :- Quux(a,b), b < 5.")
        expected = [(a,a) for a in xrange(0, 5)]

        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_sortby2(self):
        s.run("Qux(a,b) :- Quux(a,b), b >= 5.")
        expected = [(a,a) for a in xrange(5, 10)]

        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_groupby(self):
        s.run("Corge(a, $max(b)) :- Foo(b, a).")
        expected = [(42, 9), (41, 19)]

        result = []
        for a,b in s.query("Corge(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

class TestArrayTable(TestDynamicTable):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a:0..20, int b) indexby a.""")
        s.decl("""Bar(int a:0..100, int b) indexby a.""")
        s.decl("""Baz(int a:0..100, int b) indexby a, indexby b.""")
        s.decl("""Qux(int a:0..100, int b) indexby b.""")
        s.decl("""Quux(int a:0..100, int b).""")
        s.decl("""Corge(int a:0..100, int b) groupby(1).""")

        cls.initTables()
        
    def setUp(self):
        s.clear("Qux")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")


class TestDynamicNestedTable(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, (int b)) indexby a.""")
        s.decl("""Bar(int a, (int b)) indexby a.""")
        s.decl("""Baz(int a, (int b)) indexby a, indexby b.""")
        s.decl("""Qux(int a, (int b)) indexby b.""")
        s.decl("""Quux(int a, (int b)) sortby b.""")
        s.decl("""Corge(int a, (int b, int c)) groupby(2).""")

        cls.initTables()

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    @classmethod
    def initTables(cls):
        s.run("""Foo(a, b) :- a=$range(0, 10), b=$range(40, 45).""")
        s.run("""Bar(a, b) :- a=42, b=$range(101, 105).""")
        s.run("""Baz(a, b) :- a=$range(43, 50), b=$range(101, 105).""")
        s.run("""Quux(a, b) :- a=$range(44, 50), b=$range(101, 105).""")

    def setUp(self):
        s.clear("Qux")

    def test_simple_iteration(self):
        s.run("Qux(a, b) :- Foo(a, b).")
        expected = [(a, b) for a in xrange(0, 10) for b in xrange(40, 45)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_single_index(self):
        s.run("Qux(a, b) :- Foo(a, c), Bar(c, b).")
        expected = [(a, b) for a in xrange(0, 10) for b in xrange(101, 105)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_multi_index(self):
        s.run("Qux(a, b) :- Foo(a, c), Baz(c, b).")
        expected = [(a, b) for a in xrange(0, 10) for b in xrange(101, 105)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_multi_index2(self):
        s.run("Qux(a, x) :- Foo(a, c), x = a+104, Baz(c, x).")
        expected = [(0, 104)]
        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_sortby(self):
        s.run("Qux(a,b) :- Quux(a,b), b < 103.")
        expected = [(a, b) for a in xrange(44, 50) for b in xrange(101, 103)]

        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_sortby2(self):
        s.run("Qux(a,b) :- Quux(a,b), b >= 103.")
        expected = [(a, b) for a in xrange(44, 50) for b in xrange(103, 105)]

        result = []
        for a,b in s.query("Qux(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

    def test_groupby(self):
        s.run("Corge(a, 0, $max(b)) :- Foo(b, a).")
        s.run("Corge(a, 1, $min(b)) :- Foo(b, a).")
        expected = [(a, 0, 9) for a in xrange(40, 45)]
        expected.extend([(a, 1, 0) for a in xrange(40, 45)])

        result = []
        for a,b,c in s.query("Corge(a,b,c)"):
            result.append((a,b,c))

        self.assertEqual(sorted(result), sorted(expected))

class TestArrayNestedTable(TestDynamicNestedTable):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a:0..20, (int b)) indexby a.""")
        s.decl("""Bar(int a:0..100, (int b)) indexby a.""")
        s.decl("""Baz(int a:0..100, (int b)) indexby a, indexby b.""")
        s.decl("""Qux(int a:0..100, (int b)) indexby b.""")
        s.decl("""Quux(int a:0..100, (int b)) sortby b.""")
        s.decl("""Corge(int a:0..100, (int b, int c)) groupby(2).""")

        cls.initTables()
        
    def setUp(self):
        s.clear("Qux")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")


def main():
    unittest.main()

if __name__ == '__main__':
    main()
