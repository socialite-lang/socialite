import socialite as s


s.run("Foo(int a:0..1000, (int b)).")

s.run("Foo(a,b) :- a=$range(10, 20), b=$range(0, 1000).")
s.run("Foo(a,b) :- a=$range(20, 30), b=$range(1000, 2000).")
s.run("Foo(a,b) :- a=$range(30, 40), b=$range(1000, 2000).")
s.run("Foo(a,b) :- a=$range(40, 50), b=$range(0, 1000).")

s.run("Bar(int a:0..1000, float b).")
s.run("Bar(a,b) :- a=$range(10,20), b=a+11.0f.")
s.run("Bar(a,b) :- a=$range(30,40), b=a+37.0f.")
s.run("Bar(a,b) :- a=$range(40,50), b=a+53.0f.")
s.run("Bar(a,b) :- a=$range(50,100), b=a+97.0f.")

s.run("Qux(int a:0..2000, float b) groupby(1).")
s.run("Qux(b, $sum(f)) :- Bar(a, f), Foo(a, b).")


s.run("Sum(int i:0..0, int sum) groupby(1).")
s.run("Sum(0, $sum(f)) :- Qux(b, f).")

_ f = s.query("Sum(0, f)").first()
