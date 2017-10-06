package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S6PartiaMultiplyCombiner extends Reducer<IntWritable, VectorWritable, IntWritable, VectorWritable>
{
    @Override
    protected void reduce(IntWritable userId, Iterable<VectorWritable> values, Context context) throws IOException,
            InterruptedException
    {
        Vector partialVectorSum = null;
        
        for (VectorWritable value : values)
        {
            partialVectorSum = partialVectorSum == null ? value.get() : partialVectorSum.plus(value.get());
        }
        
        context.write(userId, new VectorWritable(partialVectorSum));
        
    }
}
