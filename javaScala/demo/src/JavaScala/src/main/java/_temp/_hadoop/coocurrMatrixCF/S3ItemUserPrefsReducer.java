package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S3ItemUserPrefsReducer extends Reducer<IntWritable, VectorOrPrefWritable, IntWritable,
        VectorOrPrefWritable>
{
    @Override
    //nothing done
    protected void reduce(IntWritable key, Iterable<VectorOrPrefWritable> values, Context context) throws
            IOException, InterruptedException
    {
        for (VectorOrPrefWritable value : values)
        {
            context.write(key, value);
        }
    }
}
