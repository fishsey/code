package com.fishsey.bookRecys.crawler.persistence.bySpark;

import com.fishsey.bookRecys.dao.mybatis.impl.NewsDao;
import com.fishsey.bookRecys.entity.mybatis.NewsEntity;
import com.fishsey.bookRecys.tools.PersisTools;
import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by root on 7/31/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-mybatis.xml"})
public class FromHdfsToMysql
{

    @Autowired
    NewsDao newsDao;

    @Test
    public void test_1()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        String absDir = "hdfs://localhost:9000/";
        String path = absDir + "/user/root/dataset/wangyi/spark";
        try
        {
            JavaSparkContext jsc = new JavaSparkContext("local[10]", "HadfsToMysql");
            JavaStreamingContext jssc = new JavaStreamingContext(jsc, Durations.seconds(1));
            JavaDStream<String> news = jssc.textFileStream(path);
            news.foreachRDD(
                    rdd ->
                    {
                        try
                        {
                            List<NewsEntity> entities = rdd.map(PersisTools::persisToEntity)
                                    .filter(entity -> entity != null ? true : false)
                                    .collect();
                            if (entities.size() > 0)
                            {
                                System.out.println("batch insert entity: " + entities.size() + "\n" + System.currentTimeMillis() / 1000);
                                newsDao.batchInsert(entities);
                            }
                        }catch (Exception e)
                        {

                        }
                    }
            );
            jssc.start();
            jssc.awaitTermination();
        } catch (Exception e)
        {

        }

    }

}
