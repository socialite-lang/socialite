from pysocialite.rpc.standalone import StandaloneService

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.protocol import TMultiplexedProtocol

import os

if __name__ == '__main__':
    try:
        port = int(os.environ["SocialiteStandalonePort"])
        transport = TSocket.TSocket('localhost', port)
        transport = TTransport.TFramedTransport(transport)
        transport.open()
        protocol = TCompactProtocol.TCompactProtocol(transport)

        muxproto = TMultiplexedProtocol.TMultiplexedProtocol(protocol, "StandaloneService")
        client = StandaloneService.Client(muxproto)
        client.terminate()
        transport.close()

    except Exception as ex:
        print(('%s' % (ex.message)))
