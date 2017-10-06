package tools.temp;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by root on 6/6/17.
 */
public class _MybatisUtil
{
    //get sqlSession
    public static final String resource = "com.fishsey.bookRecys/mybatis/mybatis-config.xml";
    public static  SqlSessionFactory sqlSessionFactory;
    public static final ThreadLocal<SqlSession> sqlSession= new ThreadLocal<>();

    static {
        try
        {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static  SqlSession getSqlSession()
    {
        SqlSession session = sqlSession.get();
        if (session == null)
        {
            session = sqlSessionFactory.openSession(false);
            sqlSession.set(session);
        }
        return session;
    }


    public  static void closeSession() throws HibernateException
    {
        SqlSession s = sqlSession.get();
        if (s != null)
            s.close();
        sqlSession.set(null);
    }

}
