package socialite.util;

import org.antlr.runtime.RecognitionException;

import socialite.parser.Parser;

public class ParseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	Parser p;
	int line;
	int pos;
	
	public ParseException(String _msg) {
		super(_msg);
	}
	public ParseException(Parser _p, RecognitionException _e, String _msg) {
		super(_msg);
		p = _p;
		line = _e.line-1;
		pos = _e.charPositionInLine;
	}
	public ParseException(Parser _p, int _line, int _pos, String _msg) {
		super(_msg);
		p = _p;
		line = _line;
		pos = _pos;
	}
	
	public void setParser(Parser p) { this.p = p; }
	public void setLine(int line) { this.line = line; }
	public void setPos(int pos) { this.pos = pos; }
	
	public int getLine() { return line; }
	public int getPos() { return pos; }

	public String compileErrorMsg() {
		assert p!=null;
		return p.generateErrorMsg(this);
	}
}
