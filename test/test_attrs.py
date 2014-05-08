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

if __name__ == '__main__':
    unittest.main()
