"""
  Testing join operations.
"""

import socialite as s
import unittest

class TestBase(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        s.run("Bar(int a:0..1000, float b).")
        s.run("Qux(int a:0..2000, float b) groupby(1).")
        s.run("Sum(int i:0..0, float sum) groupby(1).")
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

        s.run("Bar(a,b) :- a=$range(10,20), b=11.0f.")
        s.run("Bar(a,b) :- a=$range(30,40), b=37.0f.")
        s.run("Bar(a,b) :- a=$range(40,50), b=53.0f.")
        s.run("Bar(a,b) :- a=$range(50,100), b=97.0f.")

    @classmethod
    def tearDownClass(cls):
        s.drop("*")

    def run_simple_rule(self):
        s.run("Qux(b, $sum(f)) :- Bar(a, f), Foo(a, b).")
        s.run("Sum(0, $sum(f)) :- Qux(b, f).")

        _, _sum = s.query("Sum(0, f)").first()
        xsum=0.0
        for b,f in s.query("Qux(b,f)"):
            xsum+=f
        print "xsum:", xsum, "sum:", _sum


        self.assertEquals(_sum, xsum)
        #self.assertEquals(_sum, 1945000.0)

class TestShardByCol0(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)).")

    def setUp(self):
        s.clear("Qux")
        s.clear("Sum")

    def test_simple(self):
        s.run("Qux(b, f) :- b=$range(0, 2000), f=b+131.2f.")
        s.run("Sum(0, $sum(f)) :- Qux(b,f).")
        _, _sum = s.query("Sum(0, f)").first()
        xsum=0.0
        for b,f in s.query("Qux(b,f)"):
            xsum+=f
        print "xsum:", xsum, "sum:", _sum

        self.assertEquals(_sum, xsum)
 
@unittest.skip("")
class TestShardByCol1(TestBase):
    @classmethod
    def setUpMoreClass(cls):
        s.run("Foo(int a:0..1000, (int b)) shardby b.")

    def setUp(self):
        s.clear("Qux")
        s.clear("Sum")

    def test_simple(self):
        for i in range(50):
            self.run_simple_rule()
            self.setUp()


def main():
    unittest.main()

if __name__ == '__main__':
    main()
