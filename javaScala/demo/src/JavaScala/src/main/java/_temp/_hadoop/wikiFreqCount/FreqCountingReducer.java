package _temp._hadoop.wikiFreqCount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FreqCountingReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{
    @Override
    //input: key-(1,1,1,...)
    //output: key-len(1,1,1,...)
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
    {
        
        Configuration conf = context.getConfiguration();
        int maxValue = conf.getInt("maxValue", 0);
        
        int count = 0;
        for (IntWritable value : values)
        {
            count += value.get();
        }
        //update the maxValue
        if (count > maxValue)
        {
            conf.setInt("maxValue", count);
            context.write(key, new IntWritable(count));
        }
    }
    
}
