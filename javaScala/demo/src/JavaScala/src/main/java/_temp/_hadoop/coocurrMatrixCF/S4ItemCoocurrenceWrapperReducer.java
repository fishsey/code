package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S4ItemCoocurrenceWrapperReducer extends Reducer<IntWritable, VectorOrPrefWritable, IntWritable,
        VectorOrPrefWritable>
{
    @Override
    protected void reduce(IntWritable itemId, Iterable<VectorOrPrefWritable> itemCoocurrenceVector, Context context)
            throws IOException, InterruptedException
    {
        for (VectorOrPrefWritable elem : itemCoocurrenceVector)
        {
            context.write(itemId, elem);
        }
    }
}
