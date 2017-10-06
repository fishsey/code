package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S2ToItemCooccurrenceReducer extends Reducer<IntWritable, IntWritable, IntWritable, VectorWritable>
{
    @Override
    //input: "itemId1: [itemID2, itemID2, . . .]" include repeat exist
    //output: "itemId1: VectorWritable([itemId1:num1, itemId2:num2, . . . ])"   CooccurrenceVector, calculate the
    // times of repeat-occr
    protected void reduce(IntWritable itemID, Iterable<IntWritable> itemList, Context context) throws IOException,
            InterruptedException
    {
        Vector coocuVector = new RandomAccessSparseVector(Integer.MAX_VALUE, 100);
        
        for (IntWritable cooItem : itemList)
        {
            coocuVector.set(cooItem.get(), coocuVector.get(cooItem.get()) + 1.0f);
        }
        
        context.write(itemID, new VectorWritable(coocuVector));
        
    }
}
