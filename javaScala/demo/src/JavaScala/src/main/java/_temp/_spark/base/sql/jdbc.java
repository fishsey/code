package _temp._spark.base.sql;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

/**
 * Created by root on 8/2/17.
 */
public class jdbc
{
    @Test
    public void test_()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        SparkSession sparkSession = SparkSession.builder()
                .appName("testJdbc").master("local[8]").getOrCreate();

        Dataset<Row> df = sparkSession
                .read()
                .format("jdbc")
                .option("url", "jdbc:mysql://10.65.1.62:3306")
                .option("dbtable", "wyNews.news")
                .option("user", "fishsey")
                .option("password", "0114")
                .load();

        //df.select("newsid", "title", "text").show(false);;



    }
}
