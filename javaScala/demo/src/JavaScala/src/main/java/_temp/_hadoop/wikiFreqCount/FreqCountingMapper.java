package _temp._hadoop.wikiFreqCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by root on 7/20/16.
 */

public class FreqCountingMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        String[] words = value.toString().split(":")[1].trim().split(" ");
        for (String word : words)
        {
            context.write(new Text(word), new IntWritable(1));
        }
    }
    
}
