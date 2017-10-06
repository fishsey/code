package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by root on 10/22/16.
 */
public class FileHander
{
    FileFilter fileFilter = x -> x.toString().contains("/part");
    
    //clean all files in dir
    public static void cleanDir(FileSystem fileSystem, String dir) throws IOException
    {
        if (fileSystem.exists(new Path(dir)))
        {
            fileSystem.delete(new Path(dir), true);
        }
        fileSystem.mkdirs(new Path(dir));
    }
    
}


