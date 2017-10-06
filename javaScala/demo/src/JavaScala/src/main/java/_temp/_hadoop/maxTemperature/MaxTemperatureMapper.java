package _temp._hadoop.maxTemperature;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by root on 7/20/16.
 */

public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
    private static final int MISSING = -9999;//mark the loss value

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        String line = value.toString();
        String year = line.split("\t")[0];

        int airTemperature = Integer.parseInt(line.split("\t")[4]);
        if (airTemperature != MISSING)
        {
            context.write(new Text(year), new IntWritable(airTemperature));
        }
    }


}
