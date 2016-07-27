"""
  Testing join operations.
"""

import socialite as s
import unittest

class TestBase(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.run("Bar(int a:0..1000, int b).")
        s.run("Qux(int a:0..2000, int b) groupby(1).")
        s.run("Sum(int i:0..0, int sum) groupby(1).")
        cls.setUpMoreClass()
        cls.initTables()

    @classmethod
    def setUpMoreClass(cls):
        pass
 
    @classmethod
    def initTables(cls):
        s.run("Foo(a,b) :- a=$range(10, 20), b=$range(0, 1000).")
        s.run("Foo(a,b) :- a=$range(20, 30), b=$range(1000, 2000).")
        s.run("Foo(a,b) :- a=$range(30, 40), b=$range(1000, 2000).")
        s.run("Foo(a,b) :- a=$range(40, 50), b=$range(0, 1000).")

        s.run("Bar(a,b) :- a=$range(10,20), b=a+11.")
        s.run("Bar(a,b) :- a=$range(30,40), b=a+37.")
        s.run("Bar(a,b) :- a=$range(40,50), b=a+53.")
        s.run("Bar(a,b) :- a=$range(50,100), b=a+97.")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def setUp(self):
        s.clear("Qux")
        s.clear("Sum")

    def run_simple_rule(self):
        s.run("Qux(b, $sum(f)) :- Bar(a, f), Foo(a, b).")
        s.run("Sum(0, $sum(f)) :- Qux(b, f).")

        _, _sum = s.query("Sum(0, f)").first()

        self.assertEquals(_sum, 1945000.0)

class TestShardByCol0(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)).")

    def test_simple(self):
        self.run_simple_rule()


class TestShardByCol0Sorted(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)) sortby b.")
        
    def test_simple(self):
        self.run_simple_rule()

class TestShardByCol1(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)) shardby b.")

    def test_simple(self):
        self.run_simple_rule()

class TestShardByCol1Sorted(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)) shardby b, sortby b.")

    def test_simple(self):
        self.run_simple_rule()


def main():
    unittest.main()

if __name__ == '__main__':
    main()
