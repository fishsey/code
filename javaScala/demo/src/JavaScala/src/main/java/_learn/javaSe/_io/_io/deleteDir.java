package _learn.javaSe._io._io;

import org.junit.Test;

import java.io.File;

/**
 * Created by fishsey on 2017/9/20.
 */
public class deleteDir
{
    @Test
    public void test_()
    {
        deleteFolder("tempData/mlTemp");
    }

    public boolean deleteFolder(String url)
    {
        File file = new File(url);
        if (!file.exists())
        {
            return false;
        } else if (file.isFile())
        {
            file.delete();
            return true;
        } else
        {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                String root = files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                deleteFolder(root);
            }
            file.delete();
            return true;
        }
    }
}
