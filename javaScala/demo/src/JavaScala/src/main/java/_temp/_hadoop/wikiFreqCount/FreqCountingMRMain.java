package _temp._hadoop.wikiFreqCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by root on 7/21/16.
 * import org.apache.hadoop.fs.Path;
 * import org.apache.hadoop.io.IntWritable;
 */

public class FreqCountingMRMain
{
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        
        long start = System.currentTimeMillis();
        
        //System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\hadoop-2.6.2");

        //set path of input and output
        //String abPath = "hdfs://localhost:9000/user/root/";
        String abPath = "/root/AAA/";
        //String abPath = "D:/baiduYun/syn/Github/";
        String input = abPath + "dataset/wiki/input";
        String output = abPath + "dataset/wiki/output";

        Configuration confs= new Configuration();
        FileSystem fs = FileSystem.get(confs);
        if (fs.exists(new Path(output)))
        {
            fs.delete(new Path(output), true);
        }

        confs.setInt("maxValue", 0);


        Job job = Job.getInstance(confs);
        job.setJarByClass(FreqCountingMRMain.class);

        FileInputFormat.setInputPaths(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.setMapperClass(FreqCountingMapper.class);
        job.setCombinerClass(FreqCountingCombiner.class);
        job.setReducerClass(FreqCountingReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //job.setNumReduceTasks(4);

        job.waitForCompletion(true);

        System.out.println(System.currentTimeMillis() - start);
    }
}
