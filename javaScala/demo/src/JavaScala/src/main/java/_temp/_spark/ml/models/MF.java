package _temp._spark.ml.models;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;

import java.io.Serializable;


/**
 * Created by root on 12/30/16.
 */
public class MF implements Serializable
{
    public  static int rank = 50;
    public  static int iterations = 20;
    public  static double lamda = 0.01;

    public static MatrixFactorizationModel getMfModel(JavaSparkContext jsc, String trainFile)
    {
        RDD<Rating> data = jsc.textFile(trainFile)
                .map(line ->
                {
                    String[] temp = line.trim().split("\t");
                    return new Rating((int) Float.parseFloat(temp[0]), (int) Float.parseFloat(temp[1]), Double.parseDouble(temp[2]));
                }).rdd();
        return org.apache.spark.mllib.recommendation.ALS.train(data, rank, iterations);
    }

}
