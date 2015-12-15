namespace java socialite.rpc.queryCallback
namespace py pysocialite.rpc.queryCallback

include "lang.thrift"

struct TTupleList {
  1: list<lang.TTuple> tupleList,
  2: bool last,
}

service QueryCallbackService {
  void returnTuples(1: i64 queryid, 2: TTupleList tupleList),
}
