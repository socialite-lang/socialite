"""
  Testing recursive rules.
"""

import unittest

class TesetRecursion(unittest.TestCase):
    def __init__(self, methodName='runTest'):
        unittest.TestCase.__init__(self, methodName)
    
    def test_recur1(self):
        `Edge(int s, (int t)) indexby s.

         Edge(s,t) :- s=1, t=2.
         Edge(s,t) :- s=1, t=4.
         Edge(s,t) :- s=1, t=5.
         Edge(s,t) :- s=1, t=8.

         Edge(s,t) :- s=2, t=3.

         Edge(s,t) :- s=3, t=4.

         Edge(s,t) :- s=5, t=3.
         Edge(s,t) :- s=5, t=6.

         Edge(s,t) :- s=6, t=1.
         Edge(s,t) :- s=6, t=7.

         Edge(s,t) :- s=8, t=7.`

        `Path(int n,int dist) indexby n.
         Path(n,$min(d)) :- n=1, d=0;
                          :- Path(s,d1), Edge(s,n), d=d1+1.`

        _,d = list(`Path(7,d)`)[0]
        self.assertEqual(d, 2)

        `clear Edge.
         drop Path.`
    def test_recur2(self):
        `Edge(int s,(int t)) indexby s.

         Edge(s,t) :- s=1, t=2.
         Edge(s,t) :- s=1, t=4.
         Edge(s,t) :- s=1, t=5.
         Edge(s,t) :- s=1, t=8.

         Edge(s,t) :- s=2, t=3.

         Edge(s,t) :- s=3, t=4.

         Edge(s,t) :- s=5, t=3.
         Edge(s,t) :- s=5, t=6.

         Edge(s,t) :- s=6, t=1.
         Edge(s,t) :- s=6, t=7.

         Edge(s,t) :- s=8, t=7.`

        `Path(int n,int dist, int pre) indexby n.
         Path(n,$min(d), p) :- n=1, d=0, p=1;
                          :- Path(p,d1,_), Edge(p,n), d=d1+1.`

        _,d,prev = list(`Path(7,d, prev)`)[0]
        self.assertEqual(d, 2)
        self.assertEqual(prev, 8)

        `clear Edge.
         drop Path.`

if __name__ == '__main__':
    unittest.main()
