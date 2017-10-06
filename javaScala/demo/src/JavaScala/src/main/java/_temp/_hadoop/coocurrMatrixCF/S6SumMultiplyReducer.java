package _temp._hadoop.coocurrMatrixCF;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.mahout.cf.taste.hadoop.RecommendedItemsWritable;
import org.apache.mahout.cf.taste.impl.recommender.ByValueRecommendedItemComparator;
import org.apache.mahout.cf.taste.impl.recommender.GenericRecommendedItem;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.math.VectorWritable;

import java.io.IOException;
import java.util.*;

/**
 * Created by root on 10/21/16.
 */
public class S6SumMultiplyReducer extends Reducer<IntWritable, VectorWritable, IntWritable, RecommendedItemsWritable>
{
    private int recommendItemNumsPerUser = 10;
    
    @Override
    protected void reduce(IntWritable userId, Iterable<VectorWritable> values, Context context) throws IOException,
            InterruptedException
    {

        //calculate the sum of multiply
        Vector vectorSum = null;
        for (VectorWritable value : values)
        {
            vectorSum = vectorSum == null ? value.get() : vectorSum.plus(value.get());
        }

        //from small to large, small-heaper
        Queue<RecommendedItem> topItems = new PriorityQueue<>(recommendItemNumsPerUser, Collections.reverseOrder
                (ByValueRecommendedItemComparator.getInstance()));

        Iterable<Vector.Element> userItemPresVector = vectorSum.nonZeroes();

        for (Vector.Element perferElem : userItemPresVector)
        {
            int index = perferElem.index(); float value = (float) perferElem.get();
            if (topItems.size() < recommendItemNumsPerUser)
            {
                topItems.add(new GenericRecommendedItem(index, value));
            } else if (value > topItems.peek().getValue())
            {
                topItems.add(new GenericRecommendedItem(index, value)); topItems.poll();
            }
        }

        List<RecommendedItem> recommendedItems = new ArrayList<RecommendedItem>(topItems.size());
        recommendedItems.addAll(topItems);
        Collections.sort(recommendedItems, ByValueRecommendedItemComparator.getInstance());
        context.write(userId, new RecommendedItemsWritable(recommendedItems));
    }
}
