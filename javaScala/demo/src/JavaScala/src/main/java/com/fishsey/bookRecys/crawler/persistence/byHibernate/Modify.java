package com.fishsey.bookRecys.crawler.persistence.byHibernate;

import com.fishsey.bookRecys.dao.hibernate.impl.BaseDaoImpl;
import com.fishsey.bookRecys.entity.hibernate.Page;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 8/3/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:com.fishsey.bookRecys/spring/spring-hibernate.xml"})
public class Modify
{
    @Autowired
    private BaseDaoImpl baseDaoIml;

    @Test
    public void test_()
    {
        long start = System.currentTimeMillis();
        int count = 0;
        Session session = baseDaoIml.getSessionFactory().openSession();


        session.beginTransaction();
        List res = session.createQuery("select bookid from Page where contentIntro='' and catalog=''").list();
        session.getTransaction().commit();


        session.beginTransaction();
        while (count < res.size())
        {
            System.out.println(count);
            session.delete(baseDaoIml.get(Page.class, (Serializable) res.get(count++)));
        }
        session.getTransaction().commit();


        System.out.println(System.currentTimeMillis() - start);

  }
}
