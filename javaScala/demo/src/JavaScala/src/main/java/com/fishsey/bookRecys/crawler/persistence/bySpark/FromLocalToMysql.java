package com.fishsey.bookRecys.crawler.persistence.bySpark;

import com.fishsey.bookRecys.dao.mybatis.impl.NewsDao;
import com.fishsey.bookRecys.entity.mybatis.NewsEntity;
import com.fishsey.bookRecys.tools.PersisTools;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-mybatis.xml"})
public class FromLocalToMysql
{

    @Autowired
    PersisTools persisTools;

    @Autowired
    NewsDao newsDao;

    @Test
    public void test_()
    {
        //PropertyConfigurator.configure("tempData/log4j.properties");

        String path = "/root/AAA/dataset/spider/doc/wangyi/csv/test";
        JavaSparkContext jsc = new JavaSparkContext("local[10]", "testApp");

        List<NewsEntity> entitsys = jsc.textFile(path)
                .map(PersisTools::persisToEntity)
                .filter(entity -> entity!=null ? true : false)
                .collect();

        newsDao.batchInsert(entitsys);

    }
}
