package _temp._spark.base.sql;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

/**
 * Created by root on 8/2/17.
 */
public class App
{
    @Test
    public void test_Json()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");
        SparkSession sparkSession = SparkSession.builder()
                .appName("test").master("local[4]").getOrCreate();

        Dataset<Row> df = sparkSession.read().json("/root/AAA/dataset/spider/doc/douban/json/douBan_0003/1-1500655347.54.json");

        df.show(2, true);
        df.printSchema();
        df.select("Id").cube("Id").sum("ID");
    }
}
