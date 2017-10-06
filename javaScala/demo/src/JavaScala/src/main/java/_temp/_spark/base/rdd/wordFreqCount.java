package _temp._spark.base.rdd;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import scala.Tuple2;
import tools.usage.Cmp;
import tools.usage.ConvenientMethod;

import java.util.Arrays;


/**
 * Created by root on 3/10/17.
 */
public class wordFreqCount
{
    @Test
    public void test_()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        //System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\hadoop-2.6.2");

        long start = System.currentTimeMillis();

        //String fileName = "D:/baiduYun/syn/Github/dataset/wiki/wiki10k";
        String fileName = "/root/AAA/dataset/wiki/wiki";
        //String fileName = "/root/AAA/dataset/wiki/enwiki-latest-pages-articles.xml";


        JavaSparkContext jsc = new JavaSparkContext("local[40]", "just for test");
        jsc.textFile(fileName)
                //.cache()
                .flatMap(line -> Arrays.asList(line.split(":")[1].trim().split(" ")).iterator())
                //.flatMap(line -> Arrays.asList(line.trim().split("[^A-Za-z]+")).iterator())
                .groupBy(word -> word)
                .map(tuple -> new Tuple2<Object, Integer>(tuple._1, ConvenientMethod.getLenString(tuple._2)))
                .top(1, Cmp.com)
                .forEach(System.out::println);


        long end = System.currentTimeMillis();
        System.out.println("during time: " + (end - start)/1000.0);

    }
}

