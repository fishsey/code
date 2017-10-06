package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/11/16.
 */
public class S1ToUserRatingVectorReducer extends Reducer<IntWritable, IntWritable, IntWritable, VectorWritable>
{
    @Override
    //input: "userId, [itemId1, itemId2, ...]"
    //output: "userId, VectorWritable(itemId1:1.0, itemId2:1.0, ...)"
    protected void reduce(IntWritable userId, Iterable<IntWritable> values, Context context) throws IOException,
            InterruptedException
    {
        Vector userVector = new RandomAccessSparseVector(Integer.MAX_VALUE, 100);
        for (IntWritable itemId : values)
        {
            userVector.set(itemId.get(), userVector.get(itemId.get()) + 1.0);
        }
        context.write(userId, new VectorWritable(userVector));
    }
}
