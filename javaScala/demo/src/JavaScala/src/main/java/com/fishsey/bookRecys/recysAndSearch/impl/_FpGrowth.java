package com.fishsey.bookRecys.recysAndSearch.impl;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by root on 8/10/17.
 */
public class _FpGrowth
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
                .option("dbtable", "wyNews.test")
                .option("user", "fishsey")
                .option("password", "0114")
                .load();

        JavaRDD<HashSet<String>> originWords = df.select("keywords")
                .toJavaRDD()
                .map(keys -> new HashSet<>(Arrays.asList(keys.getString(0).split(" "))))
                .cache();

        FPGrowthModel<String> model = new FPGrowth()
                .setMinSupport(0.1)
                .setNumPartitions(10)
                .run(originWords);

        for (FPGrowth.FreqItemset<String> freqItemset : model.freqItemsets().toJavaRDD().collect())
        {
            System.out.println(freqItemset);
        }


    }
}
