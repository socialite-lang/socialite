package socialite.functions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

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
}
