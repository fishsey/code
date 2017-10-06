package com.fishsey.bookRecys.recysAndSearch.impl;

import com.fishsey.bookRecys.dao.mybatis.impl.NewsDao;
import com.fishsey.bookRecys.dao.mybatis.impl.NewsWordsDao;
import com.fishsey.bookRecys.entity.mybatis.NewsEntity;
import com.fishsey.bookRecys.recysAndSearch.impl.nlk.AnaylyzerImpl;
import com.fishsey.bookRecys.tools.PersisTools;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by root on 8/4/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-mybatis.xml"})
public class _ToWords
{
    @Autowired
    NewsWordsDao newsWordsDao;

    @Autowired
    NewsDao newsdao;

    @Autowired
    AnaylyzerImpl anaylyzer;


    @Test
    public void test_() throws InstantiationException, IllegalAccessException
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        int i = 1;
        int size = 200;
        int interval = 50;
        List<NewsEntity> newsEntity = newsdao.selectList(0, size);
        int lens = newsEntity.size();

        for (; i <= size; i += interval + 1)
        {
            System.out.println(i + "\t" + (i + interval));

            toPersistence(newsEntity.subList(i, i+interval > lens ? lens : i+interval));

            System.out.println("........................................");
        }
    }

    public void toPersistence(List<NewsEntity> newsEntity) throws IllegalAccessException, InstantiationException
    {
        List keysEntity = newsEntity
                .stream()
                //.parallelStream()
                .map(entity -> PersisTools.toNewsKeyWordsEntity(entity.getNewsId(), anaylyzer.anaylyzerWords(entity.getTitle(), entity.getText())))
                .collect(Collectors.toList())
        ;

        newsWordsDao.batchInsertData(keysEntity);

    }
}
