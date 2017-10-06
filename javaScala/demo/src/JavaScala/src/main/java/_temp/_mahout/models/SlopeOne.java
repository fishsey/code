package _temp._mahout.models;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.AbstractRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import tools.apache.mahout.slopeone.SlopeOneRecommender;

import java.io.File;
import java.io.IOException;


/**
 * Created by root on 8/3/17.
 */
public class SlopeOne
{
    static String trainFile = "tempData/ws5Train";
    static String testFile = "tempData/ws5Test";

    public  static AbstractRecommender slopeOneModel() throws IOException, TasteException
    {
        DataModel datamodel = new FileDataModel(new File(trainFile));

        //Recommender recommender = new SlopeOneRecommender(datamodel, Weighting.WEIGHTED, Weighting.WEIGHTED, new MemoryDiffStorage(datamodel, Weighting.WEIGHTED, Long.MAX_VALUE));
        SlopeOneRecommender recommender = new SlopeOneRecommender(datamodel);

        return recommender;
    }
}
