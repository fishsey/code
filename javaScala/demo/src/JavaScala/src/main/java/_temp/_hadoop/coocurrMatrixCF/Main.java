package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 * Created by root on 10/21/16.
 */
public class Main
{
    public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, URISyntaxException, InstantiationException
    {
        long start = System.currentTimeMillis();
        //String targetDir = "hdfs://localhost:9000/user/root/dataset/wiki/output";
        //String sourceDir = "hdfs://localhost:9000/user/root/dataset/wiki/input";

        String sourceDir = "/root/AAA/dataset/wiki/input";
        String targetDir = "/root/AAA/dataset/wikijps/output";

        FileSystem fs = FileSystem.get(new Configuration());
        if (fs.exists(new Path(targetDir)))
        {
            fs.delete(new Path(targetDir), true);
        }

        //seq init input file
        SeqFileTranslate.hander(sourceDir, targetDir);

        //process: 6 map-reducer
        for (int i = 1; i <= 6; i++)
        {
            System.out.println("process: " + i);
            Job job = Job.getInstance();
            job.setJarByClass(Main.class);

            job.setInputFormatClass(SequenceFileInputFormat.class);
            if (i != 6)
            {
                job.setOutputFormatClass(SequenceFileOutputFormat.class);
            }

            FileInputFormat.setInputDirRecursive(job, true);
            FileOutputFormat.setOutputPath(job, new Path(targetDir + "/result-" + i));
            
            //job.setNumReduceTasks(20);

            setup(job, i, targetDir);//set the environment for Map-Reducer

            job.waitForCompletion(true);

        }
        
        System.out.println((System.currentTimeMillis() - start) / 1000.0);
    }
    
    private static void setup(Job job, int i, String targetDir) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, ClassNotFoundException
    {
        Main obj = new Main();
        obj.getClass().getDeclaredMethod("setup" + i, job.getClass(), Class.forName("java.lang.String")).invoke(obj, job, targetDir);
    }
    
    private static void setup1(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S1ToUserRatingVectorMapper.class);
        job.setReducerClass(S1ToUserRatingVectorReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(VectorWritable.class);
    
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 0));
    
    }
    
    private static void setup2(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S2ToItemCooccurrenceMapper.class);
        job.setReducerClass(S2ToItemCooccurrenceReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(VectorWritable.class);
        
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 1));
    
    }
    
    private static void setup3(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S3ItemUserPrefsMapper.class);
        job.setReducerClass(S3ItemUserPrefsReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(VectorOrPrefWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(VectorOrPrefWritable.class);
        
        job.setNumReduceTasks(0);
    
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 1));
    
    }
    
    private static void setup4(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S4ItemCoocurrenceWrapperMapper.class);
        job.setReducerClass(S4ItemCoocurrenceWrapperReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(VectorOrPrefWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(VectorOrPrefWritable.class);
    
    
        job.setNumReduceTasks(0);
        
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 2));
    
    }
    
    private static void setup5(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S5MapToVectorAndPresMapper.class);
        job.setReducerClass(S5MapToVectorAndPresReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(VectorOrPrefWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(VectorAndPrefsWritable.class);
    
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 3));
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 4));
    
    }
    
    private static void setup6(Job job, String targetDir) throws IOException
    {
        job.setMapperClass(S6PartiaMultiplyMapper.class);
        job.setReducerClass(S6SumMultiplyReducer.class);
        
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(VectorWritable.class);
        
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(RecommendedItemsWritable.class);
    
        FileInputFormat.addInputPath(job, new Path(targetDir + "/result-" + 5));
    
    }
}
