
from pysocialite.rpc.queryCallback import QueryCallbackService
from pysocialite.rpc.queryCallback.ttypes import *
from pysocialite.thriftUtil import translate

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.server import TServer

import logging
logging.basicConfig()


callbackDefaultPort = 51000
port = callbackDefaultPort
def getCallbackPort():
    return port

def translateList(ttupleList):
    retlist = []
    for ttuple in ttupleList:
        tup = translate(ttuple)
        retlist.append(tup)

    return retlist

class CallbackHandler:
    def __init__(self):
        self.queueMap = {}

    def register(self, queryid, queue):
        self.queueMap[queryid] = queue
  
    def returnTuples(self, queryid, ttupleList):
        tupleList = translateList(ttupleList.tupleList)
        for tup in tupleList:
            self.queueMap[queryid].put(tup)
        if ttupleList.last:
            self.queueMap[queryid].put(None)


def startCallbackServer(callbackHandler):
    def callbackServer():
        global port, callbackDefaultPort
        import socket
        processor = QueryCallbackService.Processor(callbackHandler)
        tfactory= TTransport.TBufferedTransportFactory()
        pfactory = TCompactProtocol.TCompactProtocolFactory()
        while True: 
            try:
                transport = TSocket.TServerSocket(port=port)
                server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)
                server.serve()
            except socket.error:
                port += 1
                if port > callbackDefaultPort + 20:
                    raise Exception("Cannot connect to a SociaLite server")
            else:
                break

    import thread
    thread.start_new_thread(callbackServer, ())


callbackHandler = CallbackHandler()
startCallbackServer(callbackHandler)

def registerQueryQueue(queryid, queue):
    callbackHandler.register(queryid, queue)       
