import socialite as s


s.run("Foo(int a:0..1000, (int b)) sortby b.")
s.run("Foo(a,b) :- a=$range(10, 15), b=$range(0, 11100).")

for a,b in s.query("Foo(10,b)"):
    print a,b


