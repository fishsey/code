package _temp._spark.ml.models;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

/**
 * Created by root on 7/9/17.
 */
public class SVM
{
    //public static MatrixFactorizationModel getMfModel(JavaSparkContext jsc, String trainFile)
    //{
    //    
    //}

    @Test
    public void test_()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        Dataset<Row> data = SparkSession.builder().master("local[10]").getOrCreate()
                .read()
                .format("libsvm")
                .load("tempData/Training.txt");


        LogisticRegression lg = new LogisticRegression().setMaxIter(5);

        LogisticRegressionModel lgm = lg.fit(data);


    }
}
