`Foo(int a:0..100, int b).
 Foo(a,b) :- a=$range(0, 100), b=42.`

`Bar(int a:0..200, (int b)).
 Bar(a,b) :- Foo(a,b).`

count = 0
for a,b in `Bar(a,b)`:
    count += 1
assert count == 100
print "count:", count

`Foo2(int a:0..100, int b).
 Foo2(a,b) :- a=$range(0,100), b=42.` 
`Bar2(int a:0..200, int b).
 Bar2(a,b) :- a=$range(0,200), b=43.`
`Zoo2(int a:0..100, (int b)).`
`Zoo2(a,b) :- Foo2(a, x), Bar2(b, y), y == x+1.`
count=0
for a,b in `Zoo2(a,b)`:
    count+=1
assert count == 20000
print "count:", count

`Rank(int i:0..100, int s:iter, double rank).
 Rank(_, 0, r) :- r=42.1.`

`Rank(a, 1, r) :- Rank(a, 0, r1), r=r1+1.1.`
