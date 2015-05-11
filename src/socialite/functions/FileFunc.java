package socialite.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import socialite.util.SociaLiteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileFunc {
    public static final Log L= LogFactory.getLog(FileFunc.class);
    public static Iterator<String> listdir(String dir) {
        try {
            FileSystem fs = FileSystem.get(new Configuration());
            Path f = new Path(dir);
            if (fs.isFile(f)) {
                return (new ArrayList<String>()).iterator();
            } else {
                List<String> files = new ArrayList<String>(fs.listStatus(f).length);
                for (FileStatus stat:fs.listStatus(f)) {
                    files.add(dir+"/"+stat.getPath().getName());
                }
                return files.iterator();
            }
        } catch (IOException e) {
            L.error("Error while accessing "+dir+":"+e);
            return (new ArrayList<String>()).iterator();
        }
    }
    public static int close(Object _fs) {
        FSDataOutputStream fs = (FSDataOutputStream)_fs;
        try {
            fs.close();
        } catch (IOException e) {
            L.error("Exception while closing file output stream:"+e);
        }
        return 0;
    }
    public static FSDataOutputStream create(String filename) {
        try {
            FileSystem fs = FileSystem.get(new Configuration());
            Path f = new Path(filename);
            return fs.create(f);
        } catch (IOException e) {
            L.error("Error while creating " + filename + ":" + e);
            throw new SociaLiteException(e);
        }
    }
    public static int write(Object _os, String s) {
        FSDataOutputStream os = (FSDataOutputStream)_os;
        try {
            synchronized(os) {
                os.write(s.getBytes());
            }
            return s.length() + 1;
        } catch (IOException e) {
            L.error("Error while writing [" + s + "]:" + e);
            return -1;
        }
    }
    public static int writeLine(Object _os, String line) {
        FSDataOutputStream os = (FSDataOutputStream)_os;
        try {
            synchronized(os) {
                os.write(line.getBytes());
                os.write("\n".getBytes());
            }
            return line.length() + 1;
        } catch (IOException e) {
            L.error("Error while writing line[" + line + "]:" + e);
            return -1;
        }
    }
}