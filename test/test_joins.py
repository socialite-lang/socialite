"""
  Testing join operations.
"""

import unittest

class TestJoin(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
        `Foo(int a, int b).  
         Bar(int a, int b).`
        `Foo(a,b) :- a=$range(0, 5), b=$range(0,3).`

    def setUp(self):
        `clear Bar.`

    def test_not_contains_with_dontcare(self):
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), !Foo(_,b).`

        l=[]
        for a,b in `Bar(a,b)`:
            l.append((a,b))
        exp = [(4,3), (5,3), (6,3)]
        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

        `clear Bar.`
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), !Foo(a,_).`

        l=set(`Bar(a,b)`)
        exp=set([(5,0), (5,1), (5,2), (5,3), (6,0), (6,1), (6,2), (6,3)])
        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

    def test_contains_with_dontcare(self):
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), Foo(_,b).`

        l=set([])
        for a,b in `Bar(a,b)`:
            l.add((a,b))
        exp = set([(4,0), (4,1), (4,2), (5,0), (5,1), (5,2), (6,0), (6,1), (6,2)])

        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

        `clear Bar.`
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), Foo(a,_).`

        l=set(`Bar(a,b)`)
        exp=set([(4,0), (4,1), (4,2), (4,3)])
        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

    def test_not_contains(self):
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), !Foo(a,b).`

        l=set()
        for a,b in `Bar(a,b)`:
            l.add((a,b))
        exp=set([(4,3), (5,0), (5,1), (5,2), (5,3), (6,0), (6,1), (6,2), (6,3)])
        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

        `clear Bar.`
        `Bar(a,b) :- a=$range(4, 7), b=$range(0,4), Foo(a,b).`

        l=set(`Bar(a,b)`)
        exp=set([(4,0), (4,1), (4,2)])
        self.assertEqual(l, exp, "Unexpected tuples in Bar(a,b). Expecting "+str(exp)+", but "+str(l))

    def test_not_contains2(self):
        `StopWords(String s).
         StopWords(a) :- a=$splitIter("a an and the of in to for with", " ").`
        `Titles(String s).
         Titles(t) :- t="k-means clustering with scikit-learn".
         Titles(t) :- t="gradient boosted regression trees in scikit-learn".
         Titles(t) :- t="know thy neighbor: an introduction to scikit-learn and k-nn".
         Titles(t) :- t="sentiment classification using scikit-learn".`
        `Words(String w, int cnt).
         Words(w, $inc(1)) :- Titles(t), w=$splitIter(t, " "), !StopWords(w).`

        _,cnt=list(`Words("scikit-learn", cnt)`)[0]
        self.assertEqual(cnt, 4)


    def test_outjoin(self):
        `Qux(int a, (int b)).`

        `Bar(a, b) :- a=$range(0, 2), b=1.
         Qux(a, b) :- Foo(a, c), Bar(b, c).`


        l=set(`Qux(a,b)`)
        exp=set([(0,0), (0,1), (1,0), (1,1), (2,0), (2,1), (3,0), (3,1), (4,0), (4,1)])

        self.assertEqual(l, exp, "Unexpected tuples in Qux(a,b). Expecting "+str(exp)+", but "+str(l))

#import inspect
#print "line", inspect.getlineno(inspect.currentframe())-1

if __name__ == '__main__':
    unittest.main()
