package _temp._hibernate.test;

import _temp._hibernate.dao.BaseDaoImpl;
import _temp._hibernate.entity.Author;
import _temp._hibernate.entity.Page;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * Created by root on 7/26/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-hibernate.xml"})
public class test
{
    @Autowired
    BaseDaoImpl baseDaoHibernate;

    Random random = new Random(11);
    //Random random = new Random();

    @Test
    public void test_()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        Session session = baseDaoHibernate.getSession();
        System.out.println(session);
        //session.beginTransaction();
        //
        //Author a1 = getAuthor();
        //Page p1 = getPage();
        //
        ////a1.getPages().add(p1);
        //a1.setPage(p1);
        //FISH
        ////p1.setAuthor(a1);
        //p1.getAuthors().add(a1);
        //
        //
        //session.save(a1);
        //session.getTransaction().commit();

    }

    public Page getPage()
    {
        Page p1 = new Page();
        p1.setBookid(random.nextInt((int) 1e8));
        p1.setTitle(String.valueOf(Math.random()));
        p1.setUrl(String.valueOf(Math.random()));
        return p1;
    }

    public Author getAuthor()
    {
        Author p1 = new Author();
        p1.setName(String.valueOf(Math.random()));
        return p1;
    }
}
