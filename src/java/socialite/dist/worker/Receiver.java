package socialite.dist.worker;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import socialite.dist.EvalRefCount;
import socialite.dist.msg.WorkerMessage;
import socialite.eval.Command;
import socialite.eval.Manager;
import socialite.util.FastQueue;

public class Receiver implements Runnable {
    public static final Log L=LogFactory.getLog(WorkerNode.class);
    static FastQueue<RecvTask> theRecvQueue = new FastQueue<RecvTask>("recvq");
    public static FastQueue<RecvTask> recvq() { return theRecvQueue; }

    WorkerConnPool connPool;
    FastQueue<RecvTask> recvq;
    Manager manager;
    CmdListener cmdListener;
    public Receiver(FastQueue<RecvTask> _recvq, WorkerConnPool _connPool, Manager _manager, CmdListener _cmd) {
        recvq = _recvq;
        manager = _manager;
        connPool = _connPool;
        cmdListener = _cmd;
    }
    @Override
    public void run() {
        while(true) {
            RecvTask t=null;
            WorkerMessage msg;
            try {
                t = recvq.reserve();
                msg=connPool.recv(t.nodeAddr, t.selectedChannel);
                if (msg==null) continue;
                Command cmd=msg.get();
                cmd.setReceived();
                manager.addCmd(cmd);
                EvalRefCount.getInst().dec(msg.getEpochId());
            } catch (InterruptedException ie) {
                break;
            } catch (Exception e) {
                L.error("Exception while recv:"+e);
                L.error(ExceptionUtils.getStackTrace(e));
            } finally {
                recvq.pop(t);
            }
        }
    }
}
