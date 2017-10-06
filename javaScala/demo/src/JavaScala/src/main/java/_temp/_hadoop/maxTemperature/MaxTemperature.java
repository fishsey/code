package _temp._hadoop.maxTemperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

/**
 * Created by root on 7/21/16.
 * import org.apache.hadoop.fs.Path;
 * import org.apache.hadoop.io.IntWritable;
 */

public class MaxTemperature
{
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
    {
        //System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\code\\hadoop-2.6.2");
        PropertyConfigurator.configure("tempData/log4j.properties");

        //handle input and output
        String abPath = "/root/AAA/";
        //String abPath = "hdfs://localhost:9000/user/root/";
        //String abPath = "D:/Syn/";
        String input = abPath + "dataset/ncdc/ncdc_all/input";
        String output = abPath + "dataset/ncdc/ncdc_all/output";
        FileSystem fs = FileSystem.get(new Configuration());
        if (fs.exists(new Path(output)))
        {
            fs.delete(new Path(output), true);
        }

        Job job = Job.getInstance();
        job.setJarByClass(MaxTemperature.class);

        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.setMapperClass(MaxTemperatureMapper.class);
        job.setCombinerClass(MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
        
    }
}
