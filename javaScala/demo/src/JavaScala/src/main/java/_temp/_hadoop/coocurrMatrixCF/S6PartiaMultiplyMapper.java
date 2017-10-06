package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 10/21/16.
 */
public class S6PartiaMultiplyMapper extends Mapper<IntWritable, VectorAndPrefsWritable, IntWritable, VectorWritable>
{
    @Override
    //input "itemId: VectorAndPrefsWritable"  itemId : (cooccurrenceVector and "uerId1:preferValue1,
    // uerId2:preferValue2, . . .")
    //output: uerId1: cooccurrenceVector * preferValue1, uerId2: cooccurrenceVector * preferValue2, . . .
    protected void map(IntWritable ItemId, VectorAndPrefsWritable VectorAndPrefs, Context context) throws
            IOException, InterruptedException
    {
        Vector vector = VectorAndPrefs.getVector(); List<Long> userIds = VectorAndPrefs.getUserIDs();
        List<Float> prefs = VectorAndPrefs.getValues();
        
        for (int i = 0; i < userIds.size(); i++)
        {
            context.write(new IntWritable((int) userIds.get(i).longValue()), new VectorWritable(vector.times(prefs
                    .get(i))));
        }
    }
}
