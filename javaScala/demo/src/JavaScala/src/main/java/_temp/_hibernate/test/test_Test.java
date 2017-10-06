package _temp._hibernate.test;

import _temp._hibernate.dao.BaseDaoImpl;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by root on 8/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:_temp/spring/spring-hibernate.xml"})
public class test_Test
{
    @Autowired
    BaseDaoImpl baseDaoHibernate;
    static {
        PropertyConfigurator.configure("tempData/log4j.properties");
    }

    @Test
    public void test_()
    {
        Session session = baseDaoHibernate.getSession();
        session.beginTransaction();

        _temp._hibernate.entity.Test t1 = new _temp._hibernate.entity.Test();

        t1.setId(1);
        t1.setAge(1);

        session.save(t1);
        session.getTransaction().commit();
    }
}
