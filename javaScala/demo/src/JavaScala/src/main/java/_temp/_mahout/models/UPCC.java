package _temp._mahout.models;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by root on 8/3/17.
 */
public class UPCC
{
    static String trainFile = "tempData/qosTrain";
    static String testFile = "tempData/qosTest";
    static int numsNeighbor = 20;


    public  Recommender UPCCModel(DataModel datamodel)
    {
        UserSimilarity similarity = null;
        UserNeighborhood neighborhood = null;
        Recommender recommender = null;
        try
        {
            similarity = new PearsonCorrelationSimilarity(datamodel);
            neighborhood = new NearestNUserNeighborhood(numsNeighbor, similarity, datamodel);
            recommender = new GenericUserBasedRecommender(datamodel, neighborhood, similarity);
        } catch (TasteException e)
        {
            e.printStackTrace();
        }
        return recommender;
    }


    public void eval() throws TasteException, IOException
    {
        RandomUtils.useTestSeed();

        DataModel dataModel = new FileDataModel(new File(trainFile));
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = x -> UPCCModel(x);

        //evaluate the result
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        System.out.println(score);
        System.out.println("...................");
    }

    @Test
    public void test_() throws IOException, TasteException
    {
        eval();
    }
}
