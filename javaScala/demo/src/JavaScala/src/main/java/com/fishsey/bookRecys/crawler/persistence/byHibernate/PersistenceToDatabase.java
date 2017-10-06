package com.fishsey.bookRecys.crawler.persistence.byHibernate;

import com.alibaba.fastjson.JSONObject;
import com.fishsey.bookRecys.tools.jsonParse.JsonParseImpl;
import com.fishsey.bookRecys.tools.jsonParse.JsonReaderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by root on 7/21/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-hibernate.xml"})
public class PersistenceToDatabase
{
    @Autowired
    JsonReaderImpl jsonReader;
    @Autowired
    JsonParseImpl jsonParse;

    @Autowired
    PersistenceByHibernate hibernate;

    @Test
    public void test_hibernate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException
    {
        String parentAbsJson = "/root/AAA/dataset/douban/json";
        String parentAbsPic = "/root/AAA/dataset/douban/pic/";
        int count = 0;

        for (File childAbsJson : new File(parentAbsJson).listFiles())
        {
            String[] temp = childAbsJson.toString().split("/");
            String childAbsPic = parentAbsPic + temp[temp.length-1] + "/";
            hibernate.setPicAbs(childAbsPic);

            for (File file : childAbsJson.listFiles())
            {
                System.out.println(++count + "\t" + file);
                try
                {
                    List<JSONObject> pages = jsonReader.jsonParse(file.getAbsolutePath());
                    for (JSONObject page : pages)
                    {
                        jsonParse.parseJsonObjectForHibernate(page);
                    }
                    jsonParse.flush();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

}
