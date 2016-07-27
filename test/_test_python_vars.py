"""
  Testing Python variables in a query.
"""
import unittest

@returns(int)
def pyfoo(a,b):
    return a+b

class TestVars(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
        `Foo(int a, int b) groupby(1).`
        `Bar(String a, int b) groupby(1).`

    def setUp(self):
        `clear Foo.`
        `clear Bar.`

    def test_python_var(self):
        var1 = 123
        var2 = 456
        `Foo(a,b) :- a=$var1, b=$var2.`
        a,b = `Foo(a,b)`.next()
        self.assertTrue(a==var1 and b==var2)

        var3 = "var3"
        `Bar(a,b) :- a=$var3, b=$var2.`
        a,b = `Bar(a,b)`.next()
        self.assertTrue(a==var3 and b==var2)

    def test_non_existing_var(self):
        var1 = 123
        try:
            `Foo(a,b) :- a=$var1, b=$var2.`
            self.fail("Expected exception is not raised")
        except NameError:
            pass

    def test_unsupported_type(self):
        var1 = (123, 234)
        try:
            `Foo(a,b) :- a=$var1, b=42.`
            self.fail("Expected exception is not raised")
        except SociaLiteException: pass

    def test_table_iter(self):
        a=10
        `Foo(a,b) :- a=10, b=20.`
        for x,y in `Foo($a, b)`:
            self.assertTrue(x==10 and y==20);

        b=20
        x, y = `Foo(a, $b)`.next()
        self.assertTrue(x==10 and y==20);

    def test_var_in_expr(self):
        x=1; y=2;
        `Foo(a,b) :- a=$x+$y, b=a+$y.`
        i,j = `Foo(a,b)`.next()
        self.assertTrue(i==x+y and j==x+y+y)

    """
    # This raises exception at Preprocessing time, hence is disabled.
    # Test it manually as necessary.
    def test_syntax_error(self):
        var1 = 123
        var2 = 456
        `Foo(a,b) :- a=$var1, b=$var2$var2.`
    """


if __name__ == '__main__':
    unittest.main()
