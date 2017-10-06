package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S2ToItemCooccurrenceMapper extends Mapper<IntWritable, VectorWritable, IntWritable, IntWritable>
{
    @Override
    //input: "userId, VectorWritable(itemId1:1.0, itemId2:1.0, ...)"
    //output: itemId1:itemId1, itemId1:itemId2, . . .
    protected void map(IntWritable userId, VectorWritable userRatingVector, Context context) throws IOException,
            InterruptedException
    {
        Iterable<Vector.Element> itemsList = userRatingVector.get().nonZeroes();
        

        for (Vector.Element item1 : itemsList)
        {
            for (Vector.Element item2 : itemsList)
            {
                if (item1.index() != item2.index())
                {
                    context.write(new IntWritable(item1.index()), new IntWritable(item2.index()));
                }
            }
        }
        
    }
}
