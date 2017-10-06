package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S4ItemCoocurrenceWrapperMapper extends Mapper<IntWritable, VectorWritable, IntWritable,
        VectorOrPrefWritable>
{
    @Override
    //input: "itemId, VectorWritable"  CooccurrenceVector
    //output: "itemId, VectorOrPrefWritable"
    protected void map(IntWritable itemId, VectorWritable itemCoocurrenceVector, Context context) throws IOException,
            InterruptedException
    {
        context.write(itemId, new VectorOrPrefWritable(itemCoocurrenceVector.get()));
    }
}
