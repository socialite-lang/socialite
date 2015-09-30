

`Edge(int a, int b) indexby a.
 Edge(a,b) :- a=10, b=20.
 Edge(a,b) :- a=11, b=20.
 Edge(a,b) :- a=20, b=30.
 Edge(a,b) :- a=30, b=40.
 Edge(a,b) :- a=25, b=33. 
 Edge(a,b) :- a=10, b=25.
 Edge(a,b) :- a=33, b=100.
 Edge(a,b) :- a=40, b=50. 
 Edge(a,b) :- a=50, b=60.
 Edge(a,b) :- a=60, b=100.`

`Path(int a:0..100, int dist).
 Path(t, $min(d)) :- t=10, d=0;
                  :- Path(s, d1), Edge(s, t), d=d1+1.`

"""
>>> for s,d in `Path(s,d)`:
...     print s,d
... 
10 0
20 1
25 1
30 2
33 2
40 3
50 4
60 5
100 3
"""


`Edge2(int a:0..100, (int b)) indexby a.
 Edge2(a,b) :- a=10, b=20.
 Edge2(a,b) :- a=11, b=20.
 Edge2(a,b) :- a=20, b=30.
 Edge2(a,b) :- a=30, b=40.
 Edge2(a,b) :- a=25, b=33. 
 Edge2(a,b) :- a=10, b=25.
 Edge2(a,b) :- a=33, b=100.
 Edge2(a,b) :- a=40, b=50. 
 Edge2(a,b) :- a=50, b=60.
 Edge2(a,b) :- a=60, b=100.`

`Path2(int a, int dist).
 Path2(t, $min(d)) :- t=10, d=0;
                  :- Path2(s, d1), Edge2(s, t), d=d1+1.`


