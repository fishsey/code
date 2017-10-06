package _temp._hadoop.maxTemperature;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MaxTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable>
{

    AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
    {
        int maxValue = Integer.MIN_VALUE;
        for (IntWritable value : values)
        {
            atomicInteger.incrementAndGet();
            maxValue = Math.max(maxValue, value.get());
        }
        context.write(new Text(key), new IntWritable(maxValue));
        context.write(new Text(key), new IntWritable(atomicInteger.intValue()));
    }

}
