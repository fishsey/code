package com.fishsey.bookRecys.tools.jsonParse;

import com.alibaba.fastjson.JSONObject;
import com.fishsey.bookRecys.crawler.persistence.byHibernate.PersistenceByHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by root on 7/21/17.
 */
@Component
public class JsonParseImpl
{

    @Autowired
    PersistenceByHibernate hibernate;

    Class<PersistenceByHibernate> cls;

    @PostConstruct
    public void init()
    {
        cls = (Class<PersistenceByHibernate>) hibernate.getClass();
    }

    public void parseJsonObjectForHibernate(JSONObject page) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, UnsupportedEncodingException
    {
        try
        {
            for (String key : page.keySet())
            {
                String value = page.getString(key);
                //System.out.println(key + "\t" + value);
                invoke(key, value);
            }
            cls.getDeclaredMethod("last").invoke(this.hibernate);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void invoke(String key, String value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        cls.getDeclaredMethod(key, String.class, String.class).invoke(this.hibernate, key, value);
    }

    @Transactional(readOnly = false)
    public void flush() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        cls.getDeclaredMethod("flush").invoke(this.hibernate);
    }
}
