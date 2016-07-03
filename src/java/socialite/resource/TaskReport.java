package socialite.resource;

import com.intellij.util.containers.ConcurrentHashMap;
import gnu.trove.impl.sync.TSynchronizedIntFloatMap;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.hash.TIntFloatHashMap;

// TODO: merge EvalProgress with TaskReport
public class TaskReport {
    boolean success = true;
    boolean done = false;
    String errorMsg="";

    TSynchronizedIntFloatMap progressMap;
    public TaskReport() {
        progressMap = new TSynchronizedIntFloatMap(new TIntFloatHashMap());
    }
    public synchronized void addErrorMessage(String _errorMsg) {
        success = false;
        errorMsg += _errorMsg+"\n";
    }
    public String getErrorMessage() {
        return errorMsg;
    }
    public void setDone() { done = true; }
    public boolean isDone() { return done; }
    public boolean isSucceeded() { return success; }
    public boolean isFailed() { return !success; }

    public void updateProgress(int ruleid, float percent) {
        progressMap.put(ruleid, percent);
    }
    public float getProgress(int ruleid) {
        return progressMap.get(ruleid);
    }
    public TIntFloatMap getProgressMap() {
        return progressMap;
    }
}
