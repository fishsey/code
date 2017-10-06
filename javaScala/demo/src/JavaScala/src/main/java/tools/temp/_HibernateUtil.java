package tools.temp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class _HibernateUtil
{
    public static final SessionFactory sessionFactory;
    public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

    static {
        try
        {
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public  static  Session getCurrentSession() throws HibernateException
    {
        Session s = session.get();
        if (s == null)
        {
            s = sessionFactory.openSession();
            session.set(s);
        }
        return s;
    }

    public  static void closeSession() throws HibernateException
    {
        Session s = session.get();
        if (s != null)
            s.close();
        session.set(null);
    }

    public static void main(String args[])
    {
        Session s = _HibernateUtil.getCurrentSession();

    }

}
