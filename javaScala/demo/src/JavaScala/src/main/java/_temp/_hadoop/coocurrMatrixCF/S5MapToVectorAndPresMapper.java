package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S5MapToVectorAndPresMapper extends Mapper<IntWritable, VectorOrPrefWritable, IntWritable,
        VectorOrPrefWritable>
{
    @Override
    //noting done
    protected void map(IntWritable key, VectorOrPrefWritable value, Context context) throws IOException,
            InterruptedException
    {
        context.write(key, value);
    }
}
