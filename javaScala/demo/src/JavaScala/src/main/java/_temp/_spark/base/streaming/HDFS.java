package _temp._spark.base.streaming;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;

/**
 * Created by root on 7/24/17.
 */
public class HDFS
{
    @Test
    public void test_HDFS() throws InterruptedException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        JavaSparkContext jsc = new JavaSparkContext("local[10]", "just for test");
        JavaStreamingContext jssc = new JavaStreamingContext(jsc, Durations.seconds(30));
        JavaDStream<String> lines = jssc.textFileStream("hdfs://localhost:9000/user/root/dataset/wangyi/spark");

        lines.print();
        //lines.count().print();



        //lines.flatMap(x -> Arrays.asList(x.trim().split("[ ]+")).iterator())
        //        .mapToPair(word -> new Tuple2<Object, Integer>(word, 1))
        //        .reduceByKey((x, y) -> x+y)
        //        .print();

        jssc.start();
        jssc.awaitTermination();
    }


}
