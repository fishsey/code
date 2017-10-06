package com.fishsey.bookRecys.tools;

import com.fishsey.bookRecys.dao.mybatis.impl.NewsDao;
import com.fishsey.bookRecys.dao.mybatis.impl.NewsWordsDao;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by root on 8/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-mybatis.xml"})
public class TrainAndTestSplit
{
    @Autowired
    NewsWordsDao newsKeyWordsDao;
    @Autowired
    NewsDao newsdao;


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
                .option("dbtable", "wyNews.newskeywords")
                .option("user", "fishsey")
                .option("password", "0114")
                .load();

        Dataset<Row>[] dfs = df.randomSplit(new double[]{0.9, 0.1});

        List train = dfs[0].toJavaRDD()
                .map(row -> PersisTools.toNewsKeyWordsEntity((Integer) row.get(0), (String) row.get(1)))
                .collect();
        System.out.println(train.size());

        List test = dfs[1].toJavaRDD()
                .map(row -> PersisTools.toNewsKeyWordsEntity((Integer) row.get(0), (String) row.get(1)))
                .collect();
        System.out.println(test.size());

        newsKeyWordsDao.batchInsertTrain(train);
        newsKeyWordsDao.batchInsertTest(test);

    }
}
