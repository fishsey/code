package _temp._spark.ml.test;

import _temp._spark.ml.evals.LogisticREval;
import _temp._spark.ml.models.LogisticR;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.ml.classification.LogisticRegressionModel;

/**
 * Created by fishsey on 2017/7/10.
 */
public class Test_LogisticR
{
    @org.junit.Test
    public void test_logisticR()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        String trainFile = "tempData/Training.txt";
        String testFile = "tempData/HDFS.txt";

        LogisticRegressionModel lrm = LogisticR.getLR(trainFile);
        LogisticREval.eval(lrm, testFile);
    }



}
