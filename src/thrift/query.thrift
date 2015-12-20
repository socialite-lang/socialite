namespace java socialite.rpc.query
namespace py pysocialite.rpc.query

include "lang.thrift"

struct QueryMessage {
  1: string query
}

exception TQueryError {
  1: string message
}

service QueryService {
  void runSimple(1: QueryMessage query) throws (1:TQueryError err),
  void run(1: QueryMessage query, 2: string addr, 3: i32 port, 4: i64 queryid) throws (1:TQueryError err),
  lang.TTuple getFirstTuple(1: QueryMessage query) throws (1:TQueryError err),
}
