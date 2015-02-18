package socialite.util;

import java.net.URL;
import java.util.Arrays;

import org.stringtemplate.v4.InstanceScope;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.compiler.CompiledST;


public class MySTGroupFile extends STGroupFile {

	public MySTGroupFile(String fullyQualifiedFileName, String encoding,
			char delimiterStartChar, char delimiterStopChar) {
		super(fullyQualifiedFileName, encoding, delimiterStartChar, delimiterStopChar);
	}
	public MySTGroupFile(URL resource, String encoding,
			char delimiterStartChar, char delimiterStopChar) {
		super(resource, encoding, delimiterStartChar, delimiterStopChar);
	}

	@Override
	public synchronized ST getInstanceOf(String name) {
		return super.getInstanceOf(name);
	}
	
	@Override
	protected synchronized ST getEmbeddedInstanceOf(Interpreter interp,                        
            InstanceScope scope,
            String name) {
		return super.getEmbeddedInstanceOf(interp, scope, name);
	}
	
	@Override
	public ST createStringTemplate(CompiledST impl) {
        MyST st = new MyST();                                                        
        st.impl = impl;
        st.groupThatCreatedThisInstance = this;
        
        if ( impl.formalArguments!=null ) {
        	st.initFormalArgs();
                /*st.locals = new Object[impl.formalArguments.size()];
                Arrays.fill(st.locals, ST.EMPTY_ATTR);*/
        }
        return st;
	}

    @Override
    public synchronized void load() { super.load(); }
	
	@Override
	public ST createStringTemplateInternally(ST proto) {
		return new MyST(proto);
	}
	
	class MyST extends ST {
		public MyST(ST proto) { super(proto); }
		public MyST() { super(); }

		public void initFormalArgs() {
			assert impl.formalArguments!=null;
			locals = new Object[impl.formalArguments.size()];
	        Arrays.fill(locals, ST.EMPTY_ATTR);
		}
		
		@Override 
		public String render() {
			synchronized (groupThatCreatedThisInstance) {
				return super.render();
			}
		}
	}	
}
