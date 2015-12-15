package socialite.dist;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import socialite.codegen.CodeGenMain;
import socialite.parser.Rule;

public class ErrorRecord {
	static ErrorRecord theInst = new ErrorRecord();
	public static ErrorRecord getInst() { return theInst; }
	
	ConcurrentHashMap<Integer, CodeGenMain> ruleToCodeMap;
	ConcurrentHashMap<CodeGenMain, String> errorLog;
	public ErrorRecord() {
		ruleToCodeMap = new ConcurrentHashMap<Integer, CodeGenMain>();
		errorLog = new ConcurrentHashMap<CodeGenMain, String>();
	}
	public void registerRules(List<Rule> rules, CodeGenMain code) {
		for (Rule r:rules) {
			ruleToCodeMap.put(r.id(), code);
		}
	}
	public void removeRules(CodeGenMain code, List<Rule> rules) {
		errorLog.remove(code);
		for (Rule r:rules) {
			ruleToCodeMap.remove(r.id());
		}
	}
	
	public void addErrorMsg(int ruleid, String errorMsg) {
		CodeGenMain code = ruleToCodeMap.get(ruleid);
		String prevMsg = errorLog.get(code);
		if (prevMsg!=null) {
			errorMsg = prevMsg +"\n"+ errorMsg;
		}
		errorLog.put(code, errorMsg);
	}
	
	public void clearError(CodeGenMain code) {
		errorLog.remove(code);
	}
	public boolean hasError(CodeGenMain code) {
		return errorLog.containsKey(code);		
	}	
	public String getErrorMsg(CodeGenMain code) {
		return errorLog.get(code);
	}
}
