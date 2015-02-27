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
        self.assertEqual((a,b,c), ("11","","22::33"))

        `clear Str.`

        `Str(a,b,c) :- (a,b,c) = $split("AA::BB::CC ::DD", "::", 3).`
        a,b,c = `Str(a,b,c)`.next()
        self.assertEqual((a,b,c), ("AA","BB","CC ::DD"))

    def test_obj_array_ret(self):
        `TestX(Object a, Object b).
         TestY(Object a, Object b).
         TestX(a,b) :- x=$TestFunc.getObjArray(42, 43), a=$itemAt(x, 0), b=$itemAt(x, 1).
         TestY(a,b) :- (a,b)=$TestFunc.getObjArrayIterator(40, 41).`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual((a,b), (42,43))
        `drop TestX.`

        (a,b) = `TestY(a,b)`.next()
        self.assertEqual((a,b), (40,41))
        `drop TestY.`

    def test_prim_array_ret(self):
        `TestX(int a, int b).
         TestX(a,b) :- (a,b)=$TestFunc.getIntArray(42, 43).`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual((a,b), (42,43))
        `drop TestX.`

        `TestY(int a, int b).
         TestY(a,b) :- x=$TestFunc.getIntArray(42, 43), a=$itemAt(x, 0), b=$itemAt(x, 1).`

        (a,b) = `TestY(a,b)`.next()
        self.assertEqual((a,b), (42,43))
        `drop TestY.`

    def test_prim_array_iter(self):
        `TestX(int a, int b).
         TestY(int a, int b).
         TestX(a,b) :- (a,b)=$TestFunc.getIntArrayIterator(42, 43).
         TestY(a,b) :- x=$TestFunc.getIntArrayIterator(40, 41), a=$itemAt(x, 0), b=$itemAt(x, 1).`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual((a,b), (42,43))

        (a,b) = `TestY(a,b)`.next()
        self.assertEqual((a,b), (40,41))
        `drop TestX.
 	     drop TestY.`

    def test_func_expr(self):
        `TestX(int a, int b).
         TestY(int a, int b).
         TestX(a,b) :- a=10, b=10.
         TestX(a,b) :- a=10, b=11.
         TestY(a,b) :- TestX(a, c), b=$pymin(10, 11)+42.`

        
        `drop TestX.
         drop TestY.`
            
if __name__ == '__main__':
    unittest.main()
