"""
  Testing join operations.
"""

import socialite as s
import unittest

class TestDynamicTable(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, int b) indexby a.""")
        s.decl("""Corge(int a, int b) groupby(1).""")

        cls.initTables()

    @classmethod
    def initTables(cls):
        s.run("""Foo(a, b) :- a=$range(0, 20000), b=42.""")
        s.run("""Foo(a, b) :- a=$range(20000, 40000), b=41.""")
        s.run("""Foo(a, b) :- a=$range(40000, 60000), b=21.""")
        s.run("""Foo(a, b) :- a=$range(60000, 80000), b=91.""")
 
    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def test_groupby(self):
        s.run("Corge(a, $sum(b)) :- Foo(b, a).")
        expected = [(42, sum(range(0, 20000))), (41, sum(range(20000, 40000))), 
                    (21, sum(range(40000,60000))), (91, sum(range(60000, 80000)))]

        result = []
        for a,b in s.query("Corge(a,b)"):
            result.append((a,b))

        self.assertEqual(sorted(result), sorted(expected))

class TestArrayTable(TestDynamicTable):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a:0..80000, int b) indexby a.""")
        s.decl("""Corge(int a:0..100, int b) groupby(1).""")

        cls.initTables()
        
    @classmethod
    def tearDownClass(cls):
        s.drop("*")


class TestDynamicNestedTable(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a, (int b)) indexby a.""")
        s.decl("""Corge(int a, (int b, int c)) groupby(2).""")

        cls.initTables()

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    @classmethod
    def initTables(cls):
        s.run("""Foo(a, b) :- a=$range(0, 20000), b=42.""")
        s.run("""Foo(a, b) :- a=$range(20000, 40000), b=41.""")
        s.run("""Foo(a, b) :- a=$range(40000, 60000), b=21.""")
        s.run("""Foo(a, b) :- a=$range(60000, 80000), b=91.""")

    def test_groupby(self):
        s.run("Corge(a, 0, $sum(b)) :- Foo(b, a).")
        expected = [(42, 0, sum(range(0, 20000))), (41, 0, sum(range(20000, 40000))), 
                    (21, 0, sum(range(40000,60000))), (91, 0, sum(range(60000, 80000)))]

        result = []
        for a,b,c in s.query("Corge(a,b,c)"):
            result.append((a,b,c))

        self.assertEqual(sorted(result), sorted(expected))

class TestArrayNestedTable(TestDynamicNestedTable):
    @classmethod
    def setUpClass(cls):
        s.decl("""Foo(int a:0..80000, (int b)) indexby a.""")
        s.decl("""Corge(int a:0..100, (int b, int c)) groupby(2).""")

        cls.initTables()
        
    @classmethod
    def tearDownClass(cls):
        s.drop("*")


if __name__ == '__main__':
    unittest.main()
