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
  void gc() throws (1:TQueryError err),
  void runSimple(1: QueryMessage query) throws (1:TQueryError err),
  void run(1: QueryMessage query, 2: string addr, 3: i32 port, 4: i64 queryid) throws (1:TQueryError err),
  lang.TTuple getFirstTuple(1: QueryMessage query) throws (1:TQueryError err),
  void clear(1: string table) throws (1:TQueryError err),
  void drop(1: string table) throws (1:TQueryError err),
  string getEnumKey(1: string kind, 2: i32 id) throws (1:TQueryError err),
  i32 getEnumId(1: string kind, 2: string key) throws (1:TQueryError err),
  list<string> getEnumKeyList(1: string kind) throws (1:TQueryError err),
}
