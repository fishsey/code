package _temp._spark.ml.models;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import tools.usage.ConvenientMethod;

/**
 * Created by root on 7/10/17.
 */
public class LogisticR
{
    public static LogisticRegressionModel getLR(String trainFile)
    {
        JavaRDD<LabeledPoint> trainRDD = SparkSession.builder()
                .master("local[20]")
                .getOrCreate()
                .read()
                .option("delimiter", "\t")
                .textFile(trainFile)
                .toJavaRDD()
                .map(line -> ConvenientMethod.transToLabelPoint(line))
                .cache();

        Dataset<Row> trainData = SparkSession.builder()
                .master("local[20]")
                .getOrCreate()
                .createDataFrame(trainRDD, LabeledPoint.class)
                .toDF();

        LogisticRegression lg = new LogisticRegression();
        LogisticRegressionModel lgm = lg.fit(trainData);
        return lgm;
    }


}
