package _temp._spark.base.streaming;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Created by root on 7/30/17.
 */
public class Socket
{
    @Test
    public void test_() throws InterruptedException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        JavaSparkContext jsc = new JavaSparkContext("local[2]", "just for test");

        JavaStreamingContext jssc = new JavaStreamingContext(jsc, Durations.seconds(5));

        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 8090);

        //lines.print();

        lines.flatMap(x -> Arrays.asList(x.trim().split("[ ]+")).iterator())
                .mapToPair(word -> new Tuple2<Object, Integer>(word, 1))
                .reduceByKey((x, y) -> x+y)
                .print();

        jssc.start();
        jssc.awaitTermination();
    }


    @Test
    public void test_server() throws Exception
    {
        new _Server(8090).run();
    }
}
