package _temp._hibernate.dao;

import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class BaseDaoImpl<T> extends HibernateTemplate
{
    public Session getSession()
    {
        return this.getSessionFactory().openSession();
    }

}
