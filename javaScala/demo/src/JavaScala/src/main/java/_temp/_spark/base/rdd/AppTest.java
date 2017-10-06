package _temp._spark.base.rdd;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;
import org.junit.Test;
import scala.Tuple2;
import tools.usage.Cmp;
import tools.usage.ConvenientMethod;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 12/26/16.
 */
public class AppTest
{
    @Test
    public void test_Broadcast()
    {
        //System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\code\\hadoop-2.6.2");
        //System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\code\\spark-2.0.2-bin-hadoop2.6");

        PropertyConfigurator.configure("tempData/log4j.properties");
        JavaSparkContext jsc = new JavaSparkContext("local[2]", "just for test");

        Broadcast bdList = jsc.broadcast(Arrays.asList('1', '2', '3'));

        jsc.parallelize(Arrays.asList('1', '2', '3')).map(x -> bdList.value() + "/" + x)
                .collect()
                .forEach(System.out::println);

    }


    @Test
    public void test_Accu()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        JavaSparkContext jsc = new JavaSparkContext("local[2]", "just for test");

        LongAccumulator accu = jsc.sc().longAccumulator();

        jsc.parallelize(Arrays.asList("1", "2", "3"))
                //.foreach(System.out::println);
                //.foreach(x -> accu.add(Long.parseLong(x)));
                .foreach(x -> accu.add(Integer.parseInt(x)));

        System.out.println(accu.value());

    }
    
    @Test
    public void test_userPurchase()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        JavaSparkContext jsc = new JavaSparkContext("local[4]", "just for test");

        JavaRDD<String[]> data = jsc.textFile("tempData/UserPurchaseHistory.csv")
                .map(line -> line.split(",")).cache();

        //count users
        long uniqueUsers = data.map(line -> line[0])
                .distinct()
                .count();
        System.out.println("users number: " + uniqueUsers);

        //sum revenue
        double sums = data.map(line -> Double.parseDouble(line[2]))
                .reduce((x, y) -> x + y);
        System.out.println("sum revenue: " + sums);
        
        //most popular product
        Tuple2<Object, Integer> temp = data.groupBy(lines -> lines[1])
                .map(tuple -> new Tuple2<Object, Integer>(tuple._1, ConvenientMethod.getLen(tuple._2)))
                .max(new Cmp());
        System.out.println(temp);

        //another way to find the most popular product
        data.<Object, Integer>mapToPair(lines -> new Tuple2<Object, Integer>(lines[1], 1))
                .reduceByKey((x, y) -> x + y)
                .top(1, new Cmp())
                .forEach(System.out::println);
    }


    @Test
    public void test_()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        JavaSparkContext jsc = new JavaSparkContext("local[4]", "just for test");
        System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\code\\spark-2.0.2-bin-hadoop2.6");

        List<String> result = jsc.textFile("D:\\Syn\\dataset\\wiki\\temp")
                .flatMap(line -> Arrays.asList(line.split(":")[1].trim().split(" ")).iterator())
                .groupBy(elem -> elem)
                .mapToPair(tuple -> new Tuple2<Integer, String>(ConvenientMethod.getLenString(tuple._2), tuple._1))
                .sortByKey()
                .map(tuple -> tuple._2)
                .top(10);
        result.forEach(System.out::println);

        //System.out.println(lineNums);
    }
}


