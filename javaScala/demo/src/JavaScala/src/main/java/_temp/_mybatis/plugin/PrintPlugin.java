package _temp._mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * Created by root on 7/26/17.
 */
@Intercepts({@Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class})})
public class PrintPlugin implements Interceptor
{
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        System.out.println("\nbefore intercept ... ");

        Object object = invocation.proceed();

        handler(invocation);

        System.out.println("after intercept ... ");
        return object;
    }
    
    private void handler(Invocation invocation)
    {
        System.out.println();

        //get object: mappedStatement
        Object[] args = invocation.getArgs();
        System.out.println(args[1]);
        System.out.println(((MappedStatement)args[0]).getSqlSource().getBoundSql(args[1]).getSql());

        //Executor exec = (Executor) invocation.getTarget();
        //MetaObject metaobject = SystemMetaObject.forObject(exec);
        //metaobject = SystemMetaObject.forObject(metaobject.getValue("configuration"));
        //
        //String[] inforNames = metaobject.getGetterNames();
        //Arrays.sort(inforNames);
        //System.out.println();
        //
        System.out.println();
    }
    
    @Override
    public Object plugin(Object target)
    {
        //System.out.println("plugin: " + "generate proxy object ... ");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties)
    {
        //System.out.println("setProperties: " + properties.get("dbType"));
        //System.out.println();
    }
}
