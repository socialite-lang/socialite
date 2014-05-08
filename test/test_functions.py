"""
  Testing functions in a query.
"""

import unittest

@returns(int)
def pymin(a, b):
    if a<b: return a
    return b

@returns(double)
def pyadd(a, b):
    return a+b

@returns(int)
def pyfoo(a,b):
    return a+b

@returns(int)
def pyErr(a, b, c=None):
    if a<b: return a
    return b


class TestFunctions(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
        `Foo(int a, int b) groupby(1).`

    def setUp(self):
        `clear Foo.`

    def test_aggr_pyfunc(self):
        @returns(int)
        def pymin(a, b):
            if a<b: return a
            return b

        `Foo(a, $pymin(b)) :- a=10, b=20;
                           :- a=10, b=21.`
        for a,b in `Foo(a,b)`:
            self.assertTrue(a==10 and b==20)

    def test_argcount_error(self):
        try:
            `Foo(a, b) :- a=10, b=$pyErr(1,2,3).`
        except SociaLiteException:
            pass
        else:
            self.fail("Expecting exception is not raised")

    def test_aggr_func_typecast(self):
        `Foo(a, b) :- a=12, b=a+23*((int)$pyadd(1,2)+1).`

        l=set(`Foo(a,b)`)
        exp=set([(12,104)])
        self.assertEqual(l, exp)

if __name__ == '__main__':
    unittest.main()
