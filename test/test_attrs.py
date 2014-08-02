"""
  Testing attribute/function access in a query.
"""

import unittest

class TesetAttrs(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
        `Foo(int i, Avg avg) groupby(1).
         Bar(int i).`

    def setUp(self):
        `clear Foo.
         clear Bar.`

    def test_field(self):
        `Foo(i, $avg(n)) :- i=$range(0, 10), n=$range(1, 4).
         Bar(i) :- Foo(0, avg), i=(int)avg.value.`

        a = list(`Bar(a)`)[0]
        self.assertTrue(a==2)

    def test_func(self):
        `Bar(i) :- i=(int)$Math.ceil(4.2).`
        a = list(`Bar(a)`)[0]
        self.assertTrue(a==5)

    def test_str(self):
        `Qux(String w) indexby w.
         Qux(w) :- (w, unused)=$Str.split("qux  unused trailing strs..  ", " ").`
        w = list(`Qux(w)`)[0]
        self.assertTrue(w=='qux')

    def test_exception1(self):
        try:
            `Foo(i, $avg(n)) :- i=$range(0, 10), n=$range(1, 4).
             Bar(i) :- Foo(0, avg), i=(int)avg.XXX .`
        except SociaLiteException:
            pass
        else:
            self.fail("Expected exception is not raised")

    def test_exception2(self):
        try:
            `Bar(i) :- i=(int)$Math.XXX(4.2).`
        except SociaLiteException:
            pass
        else:
            self.fail("Expected exception is not raised")

if __name__ == '__main__':
    unittest.main()
