package _temp._spark.ml.evals;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;

/**
 * Created by root on 7/9/17.
 */
public class MFEval
{
    public static double rmseEvaluate(JavaSparkContext jsc, MatrixFactorizationModel mf, String testFile)
    {
        System.out.println("begin eva . . . ");
        Broadcast<MatrixFactorizationModel> model = jsc.broadcast(mf);

        long samples = jsc.textFile(testFile).count();
        double rmse = jsc.textFile(testFile)
                .map(line -> line.split("\t"))
                .map(lines -> Double.parseDouble(lines[2]) - model.value().predict((int) Float.parseFloat(lines[0]), (int) Float.parseFloat(lines[1])))
                //.map(lines -> Double.parseDouble(lines[2]) - mf.predict((int) Float.parseFloat(lines[0]), (int) Float.parseFloat(lines[1])))
                .map(error -> Math.pow(error, 2))
                .reduce((x, y) -> x + y);
        return Math.sqrt(rmse / samples);

    }


    public static double maeEvaluate(JavaSparkContext jsc, MatrixFactorizationModel mf, String testFile)
    {
        System.out.println("begin eva . . . ");
        Broadcast<MatrixFactorizationModel> model = jsc.broadcast(mf);

        long samples = jsc.textFile(testFile).count();
        double mae = jsc.textFile(testFile)
                .map(line -> line.split("\t"))
                .map(lines -> Double.parseDouble(lines[2]) - model.value().predict((int) Float.parseFloat(lines[0]), (int) Float.parseFloat(lines[1])))
                //.map(lines -> Double.parseDouble(lines[2]) - mf.predict((int) Float.parseFloat(lines[0]), (int) Float.parseFloat(lines[1])))
                .map(error -> Math.abs(error))
                .reduce((x, y) -> x + y);
        return Math.sqrt(mae / samples);
    }
}
