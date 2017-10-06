package _temp._spark.ml.models;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.ml.clustering.KMeans;
import org.apache.spark.ml.clustering.KMeansModel;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class tfIdf
{
    @Test
    public void test_1()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        SparkSession sparkSession = SparkSession.builder()
                .appName("testJdbc").master("local[8]").getOrCreate();

        List<Row> data = Arrays.asList(
                RowFactory.create(0.0, "Hi I heard about Spark"),
                RowFactory.create(0.0, "I wish Java could use case classes"),
                RowFactory.create(1.0, "Logistic regression models are neat")
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
        });

        Dataset<Row> sentenceData = sparkSession.createDataFrame(data, schema);
        Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(sentenceData);

        sentenceData.show(false);
        wordsData.select("words").show(false);

        // TF
        int numFeatures = 200;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);
        Dataset<Row> featurizedData = hashingTF.transform(wordsData);

        featurizedData.select("rawFeatures").show(false);;


        // IDF
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);
        rescaledData.select("features").show(false);



        for (Row r : rescaledData.select("features", "label").takeAsList(3))
        {
            Vector features = r.getAs(0);
            Double label = r.getDouble(1);

            System.out.println(features + "\t" + label);
        }
    }

    @Test
    public void test_jdbc()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        SparkSession sparkSession = SparkSession.builder()
                .appName("testJdbc").master("local[8]").getOrCreate();

        Dataset<Row> df = sparkSession
                .read()
                .format("jdbc")
                .option("url", "jdbc:mysql://10.65.1.62:3306")
                .option("dbtable", "wyNews.newskeywordstest")
                .option("user", "fishsey")
                .option("password", "0114")
                .load();

        Tokenizer tokenizer = new Tokenizer().setInputCol("keyWords").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(df);

        // TF
        int numFeatures = 200;
        HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);
        Dataset<Row> featurizedData = hashingTF.transform(wordsData);

        //featurizedData.select("rawFeatures").show(3, false);;


        // IDF
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);

        //rescaledData.select("features").show(3, false);

        KMeans kMeans = new KMeans().setK(10).setSeed(11).setFeaturesCol("features").setPredictionCol("pred");
        KMeansModel model = kMeans.fit(rescaledData);
        Dataset<Row> kDf = model.transform(rescaledData);

        kDf.select("pred").show(100, false);;



    }
}

