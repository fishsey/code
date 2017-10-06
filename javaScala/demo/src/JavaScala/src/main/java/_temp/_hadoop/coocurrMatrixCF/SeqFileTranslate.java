package _temp._hadoop.coocurrMatrixCF;

/**
 * Created by root on 10/17/16.
 */

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

class SequenceFileMapper extends Mapper<LongWritable, Text, LongWritable, Text>
{
    
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        context.write(key, value);
    }
}


class SequenceFileReducer extends Reducer<LongWritable, Text, LongWritable, Text>
{
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException,
            InterruptedException
    {
        for (Text value : values)
        {
            context.write(key, value);
        }
    }
}


public class SeqFileTranslate
{
    public  static void hander(String sourceDir, String targetDir) throws IOException, ClassNotFoundException, InterruptedException
    {
        Job job1 = Job.getInstance();
        job1.setJarByClass(SeqFileTranslate.class);

        //the path of input and output
        FileInputFormat.addInputPath(job1, new Path(sourceDir));
        FileInputFormat.setInputDirRecursive(job1, true);
        FileOutputFormat.setOutputPath(job1, new Path(targetDir + "/result-0"));

        //mapper and reducer class
        job1.setMapperClass(SequenceFileMapper.class);
        job1.setReducerClass(SequenceFileReducer.class);

        //the type of map-output
        //the type of reducer-output
        job1.setMapOutputKeyClass(LongWritable.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputKeyClass(LongWritable.class);
        job1.setOutputValueClass(Text.class);
        
        //job1.setInputFormatClass(SequenceFileInputFormat.class);
        job1.setOutputFormatClass(SequenceFileOutputFormat.class);
        job1.waitForCompletion(true);
    }

}
