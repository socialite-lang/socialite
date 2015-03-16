"""
  Testing functions in a query.
"""

import unittest

class TestTableStmts(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)

    def test_drop_non_exit_table(self):
        try:
            `drop NonExistingTable42.`
            self.fail("")
        except:
            pass

    def test_drop_table(self):
        `TestX(int a, int b).
         TestX(a,b) :- a=10, b=20.`
        (a,b) = `TestX(a,b)`.next()
        self.assertEqual(a, 10)
        self.assertEqual(b, 20)

        `drop TestX.`

        `TestX(int a, int b).
         TestX(a,b) :- a=11, b=20+1.`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual(a, 11)
        self.assertEqual(b, 21)

        `drop TestX.`

        `TestX(int a, int b).
         TestX(a,b) :- a=12, b=21+1.`

        (a,b) = `TestX(a,b)`.next()
        self.assertEqual(a, 12)
        self.assertEqual(b, 22)

    def test_clear_table(self):
        `TestC1(int a, int b).`
        `clear TestC1.`

        `TestC1(a,b) :- a=1, b=42.`
        `clear TestC1.`
        try:
            `TestC1(a,b)`.next()
            self.fail("Expected exception(StopIteration) not raised")
        except StopIteration:
            pass

if __name__ == '__main__':
    unittest.main()
