namespace java socialite.rpc
namespace py pysocialite.rpc

struct ScalarVal {
  1: i64 num,
  2: double real,
  3: string str,
}

struct TTuple {
  1: ScalarVal col0,
  2: ScalarVal col1,
  3: ScalarVal col2,
  5: map<i16, ScalarVal> colValMap,
}


struct IntVal {
  1: i32 val 
}

struct LongVal {
  1: i64 val 
}

struct FloatVal {
  1: double val 
}

struct DoubleVal {
  1: double val 
}

struct StringVal {
  1: string val 
}

struct ObjectVal {
  1: binary val 
}

struct IntCol {
  1: list<i32> col
}

struct LongCol {
  1: list<i64> col
}

struct FloatCol {
  1: list<double> col
}

struct DoubleCol {
  1: list<double> col
}

struct StringCol {
  1: list<string> col 
}

struct ObjectCol {
  1: list<binary> col 
}
