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
def pybar(a, b, c=None):
    if a<b: return a
    return b

@returns(int)
def pyerr(a, b):
    raise Exception("Exception in a function")

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
            `Foo(a, b) :- a=10, b=$pybar(1,2,3).`
        except SociaLiteException:
            pass
        else:
            self.fail("Expected exception is not raised")

    def test_aggr_func_typecast(self):
        `Foo(a, b) :- a=12, b=a+23*((int)$pyadd(1,2)+1).`

        l=set(`Foo(a,b)`)
        exp=set([(12,104)])
        self.assertEqual(l, exp)

    def test_error_in_func(self):
        try:
            `Foo(a, b) :- a=10, b=$pyerr(1,2).`
        except SociaLiteException:
            pass
        else:
            self.fail("Expected exception is not raised from $pyerr")

    def test_builtin_split(self):
        `Str(String a, String b, String c).
         Str(a,b,c) :- (a,b,c) = $split("11::::22::33", "::", 3). `

        a,b,c = `Str(a,b,c)`.next()
        self.assertEqual(a, "11")
        self.assertEqual(b, "")
        self.assertEqual(c, "22::33")

        `clear Str.`

        `Str(a,b,c) :- (a,b,c) = $split("AA::BB::CC ::DD", "::", 3).`
        a,b,c = `Str(a,b,c)`.next()
        self.assertEqual(a, "AA")
        self.assertEqual(b, "BB")
        self.assertEqual(c, "CC ::DD")

    def test_prim_array_ret(self):
        `TestX(int a, int b).
         TestX(a,b) :- (a,b)=$TestFunc.getIntArray(42, 43).`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual(a, 42)
        self.assertEqual(b, 43)
        `drop TestX.`

    def test_prim_array_ret_as_array(self):
        `TestX(int a, int b).
         TestX(a,b) :- x=$TestFunc.getIntArray(42, 43), a=$itemAt(x, 0), b=$itemAt(x, 1).`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual(a, 42)
        self.assertEqual(b, 43)
        `drop TestX.`


if __name__ == '__main__':
    unittest.main()
