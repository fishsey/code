package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.item.VectorAndPrefsWritable;
import org.apache.mahout.cf.taste.hadoop.item.VectorOrPrefWritable;
import org.apache.mahout.math.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/21/16.
 */
public class S5MapToVectorAndPresReducer extends Reducer<IntWritable, VectorOrPrefWritable, IntWritable,
        VectorAndPrefsWritable>
{
    @Override
    protected void reduce(IntWritable itemId, Iterable<VectorOrPrefWritable> vectorOrPrefValues, Context context)
            throws IOException, InterruptedException
    {
        List<Long> userIds = new ArrayList<>(); List<Float> prefs = new ArrayList<>(); Vector vector = null;
        for (VectorOrPrefWritable vectorOrPrefValue : vectorOrPrefValues)
        {
            if (vectorOrPrefValue.getVector() == null)
            {
                userIds.add(vectorOrPrefValue.getUserID()); prefs.add(vectorOrPrefValue.getValue());
            } else vector = vectorOrPrefValue.getVector();
        }
        
        if (vector != null) context.write(itemId, new VectorAndPrefsWritable(vector, userIds, prefs));
        
        
    }
}
