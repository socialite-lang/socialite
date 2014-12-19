package org.python.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.python.core.CodeFlag;
import org.python.core.CompileMode;
import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyList;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PySystemState;
import org.python.core.imp;
import org.python.core.util.RelativeFile;
import org.python.modules._systemrestart;
import org.python.modules.posix.PosixModule;
import org.python.modules.thread.thread;

public class socialite {
    static final String usageHeader =
        "usage: socialite [option] [file] [args] \n";

    private static final String usage = usageHeader +
        "Options and arguments:\n" + 
        "-d       : runs in distributed mode (also --dist). see conf/ for cluster info.\n" +
        "-tN      : sets the number of worker threads to be N.\n" +
        "-i       : inspect interactively after running script\n" +
        "-v       : runs with verbose messages (also --verbose).\n";

    public static boolean shouldRestart;

    public static void main(String[] args) {
        do {
            shouldRestart = false;
            run(args);
        } while (shouldRestart);
    }

    public static void run(String[] args) {
        // Parse the command line options
        SocialiteCommandLineOptions opts = new SocialiteCommandLineOptions();
        if (!opts.parse(args)) {
            if (opts.help) {
                System.err.println(usage);
            }
            int exitcode = opts.help ? 0 : -1;
            System.exit(exitcode);
        }

        // Setup the basic python system state from these options
        PySystemState.initialize(PySystemState.getBaseProperties(), opts.properties, opts.argv);

        PyList warnoptions = new PyList();
        for (String wopt : opts.warnoptions) {
            warnoptions.append(new PyString(wopt));
        }
        Py.getSystemState().setWarnoptions(warnoptions);
        PySystemState systemState = Py.getSystemState();  

        // Now create an interpreter
        InteractiveConsole interp = newInterpreter(opts);
     
        systemState.__setattr__("_jy_interpreter", Py.java2py(interp));

        // Print banner and copyright information (or not)
        if (opts.interactive && opts.notice) {
            System.err.println(JLineSociaLiteConsole.getDefaultBanner());
        }

        // was there a filename on the command line?
        if (opts.filename != null) {
            String path;
            try {
                 path = new File(opts.filename).getCanonicalFile().getParent();
            } catch (IOException ioe) {
                 path = new File(opts.filename).getAbsoluteFile().getParent();
            }
            if (path == null) {
                path = "";
            }
            Py.getSystemState().path.insert(0, new PyString(path));

            try {
               interp.globals.__setitem__(new PyString("__file__"),
                                          new PyString(opts.filename));
               FileInputStream file;
               try {
                   file = new FileInputStream(new RelativeFile(opts.filename));
               } catch (FileNotFoundException e) {
                   throw Py.IOError(e);
               }
               try {
                   if (PosixModule.getPOSIX().isatty(file.getFD())) {
                       opts.interactive = true;
                       interp.interact(null, new PyFile(file));
                       return;
                   } else {
                	   interp.execfile(file, opts.filename);
                   }
               } finally {
                   file.close();
               }
            } catch (Throwable t) {
                if (t instanceof PyException
                    && ((PyException)t).match(_systemrestart.SystemRestart)) {
                    // Shutdown this instance...
                    shouldRestart = true;
                    shutdownInterpreter();
                    interp.cleanup();
                    // ..reset the state...
                    Py.setSystemState(new PySystemState());
                    // ...and start again
                    return;
                } else {
                    Py.printException(t);
                    interp.cleanup();
                    System.exit(-1);
                }
            }
        }
        else {
            // if there was no file name on the command line, then "" is the first element
            // on sys.path.  This is here because if there /was/ a filename on the c.l.,
            // and say the -i option was given, sys.path[0] will have gotten filled in
            // with the dir of the argument filename.
            Py.getSystemState().path.insert(0, Py.EmptyString);
        }

        if (opts.fixInteractive || (opts.filename == null)) {
            opts.encoding = PySystemState.registry.getProperty("python.console.encoding");
            if (opts.encoding != null) {
                if (!Charset.isSupported(opts.encoding)) {
                    System.err.println(opts.encoding
                                       + " is not a supported encoding on this JVM, so it can't "
                                       + "be used in python.console.encoding.");
                    System.exit(1);
                }
                interp.cflags.encoding = opts.encoding;
            }
            try {
                interp.interact(null, null);
            } catch (Throwable t) {
                Py.printException(t);
            }
        }
        interp.cleanup();
    }

    /**
     * Returns a new python interpreter using the InteractiveConsole subclass from the
     * <tt>python.console</tt> registry key.
     * <p>

     * When stdin is interactive the default is {@link JLineConsole}. Otherwise the
     * featureless {@link InteractiveConsole} is always used as alternative consoles cause
     * unexpected behavior with the std file streams.
     */
    private static InteractiveConsole newInterpreter(SocialiteCommandLineOptions opts) {
        if (opts.interactive) {
        	return new JLineSociaLiteConsole(opts);
        } else {
            return new JLineSociaLiteConsole(opts);
        }
    }

    /**
     * Run any finalizations on the current interpreter in preparation for a SytemRestart.
     */
    public static void shutdownInterpreter() {
        // Stop all the active threads and signal the SystemRestart
        thread.interruptAllThreads();
        Py.getSystemState()._systemRestart = true;
        // Close all sockets -- not all of their operations are stopped by
        // Thread.interrupt (in particular pre-nio sockets)
        try {
            imp.load("socket").__findattr__("_closeActiveSockets").__call__();
        } catch (PyException pye) {
            // continue
        }
    }
}

class SocialiteCommandLineOptions {
    public String filename;
    public boolean interactive, notice;
    public boolean fixInteractive;
    public boolean help;
    public boolean dist;
    public String[] argv;
    public Properties properties;
    public List<String> warnoptions = Generic.list();
    public String encoding;
    public String division;
    public int threadNum;

    public SocialiteCommandLineOptions() {
        filename = null;
        interactive = notice = true;
        properties = new Properties();
        help = false;
        dist = false;
        threadNum = -1;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
        try {
            System.setProperty(key, value);
        } catch (SecurityException e) {
            // continue
        }
    }
    boolean argumentExpected(String arg) {
        System.err.println("Argument expected for the " + arg + " option");
        return false;
    }

    public boolean parse(String[] args) {
        int index = 0;
        while (index < args.length && args[index].startsWith("-")) {
            String arg = args[index];
            if (arg.equals("-h") || arg.equals("-?") || arg.equals("--help")) {
                help = true;
                return false;
            } else if (arg.equals("-i")) {
                fixInteractive = true;
                interactive = true;
            } else if (arg.startsWith("-t")) {
            	if (arg.length() > 2) {
            		try {
            			threadNum = Integer.parseInt(arg.substring(2));
            			if (threadNum<=0) System.err.println(arg+":invalid option, ignored");
            		} catch (NumberFormatException e) {
            			System.err.println(arg+":invalid option, ignored");
            		}
            	}
            } else if (arg.equals("-v") || arg.equals("--verbose")) {
            	Options.verbose++;
            } else if (arg.equals("-vv")) {
                Options.verbose += 2;
            } else if (arg.equals("-vvv")) {
                Options.verbose +=3 ;
            } else if (arg.equals("-d") || arg.equals("--dist")) {
                dist = true;
            } else if (arg.startsWith("-W")) {
                if (arg.length() > 2) {
                    warnoptions.add(arg.substring(2));
                } else if ((index + 1) < args.length) {
                    warnoptions.add(args[++index]);
                } else {
                    return argumentExpected(arg);
                }
            } else {
                System.err.println("Unknown option: " + arg);
                return false;
            }
            index += 1;
        }
        notice = interactive;
        if (index < args.length) {
        	filename = args[index++];
        	if (!fixInteractive) {
                interactive = false;
            }
            notice = false;
        }
        
        int n = args.length - index + 1;
        argv = new String[n];
        if (filename != null) {
            argv[0] = filename;
        } else {
            argv[0] = "";
        }

        for (int i = 1; i < n; i++, index++) {
            argv[i] = args[index];
        }

        return true;
    }
}
