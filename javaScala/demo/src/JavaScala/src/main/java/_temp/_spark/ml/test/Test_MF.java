package _temp._spark.ml.test;

import _temp._spark.ml.evals.MFEval;
import _temp._spark.ml.models.MF;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 7/8/17.
 */
public class Test_MF
{
    @org.junit.Test
    public void test_MF() throws IOException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        String trainFile = "tempData/ws5Train";
        String testFile = "tempData/ws5Test";

        JavaSparkContext jsc = new JavaSparkContext("local[60]", "just for test");
        MatrixFactorizationModel mf = MF.getMfModel(jsc, trainFile);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        double rmse = MFEval.rmseEvaluate(jsc, mf, testFile);
        double mae = MFEval.maeEvaluate(jsc, mf, testFile);
        System.out.println(rmse + "\t" + mae);

    }

    @org.junit.Test
    public void test_()
    {
        m1(new String[]{"1", "2", "3"});
    }

    public void m1(String... strs)
    {
        Arrays.stream(strs).forEach(System.out::println);
    }
}
