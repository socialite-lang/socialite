namespace java socialite.rpc.query
namespace py pysocialite.rpc.query

include "lang.thrift"

struct QueryMessage {
  1: string query
}

service QueryService {
  void runSimple(1: QueryMessage query),
  void run(1: QueryMessage query, 2: string addr, 3: i32 port, 4: i64 queryid),

  lang.TTuple getFirstTuple(1: QueryMessage query),
}
