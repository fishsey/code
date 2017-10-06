package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;

/**
 * Created by root on 10/21/16.
 */
public class S3ItemUserPrefsMapper extends Mapper<IntWritable, VectorWritable, IntWritable, VectorOrPrefWritable>
{
    @Override
    //input: "userId, VectorWritable(itemId1:1.0, itemId2:1.0, ...)" userVector
    //output: "itemId1: VectorOrPrefWritable(userId, 1.0)", "itemId2: VectorOrPrefWritable(userId, 1.0)", ...
    protected void map(IntWritable usrID, VectorWritable userRatingVector, Context context) throws IOException,
            InterruptedException
    {
        Iterable<Vector.Element> itemPres = userRatingVector.get().nonZeroes();

        for (Vector.Element element : itemPres)
        {
            context.write(new IntWritable(element.index()), new VectorOrPrefWritable((long) usrID.get(), (float)
                    element.get()));
        }
    }
}
