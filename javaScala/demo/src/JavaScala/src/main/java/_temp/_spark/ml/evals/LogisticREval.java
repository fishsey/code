package _temp._spark.ml.evals;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import tools.usage.ConvenientMethod;

/**
 * Created by root on 7/10/17.
 */
public class LogisticREval
{
    static String master = "local[20]";
    public static void eval(LogisticRegressionModel model, String testFile)
    {
        JavaRDD<LabeledPoint> testRDD = SparkSession.builder()
                .master(master)
                .getOrCreate()
                .read()
                .option("delimiter", "\t")
                .textFile("tempData/temp.text")
                .toJavaRDD()
                .map(line -> ConvenientMethod.transToLabelPoint(line))
                .cache();

        Dataset<Row> testData = SparkSession.builder()
                .master(master)
                .getOrCreate()
                .createDataFrame(testRDD, LabeledPoint.class)
                .toDF();

        Dataset<Row> result = model.transform(testData);

        result.printSchema();

        result.show();

    }
}
