package _temp._spring._injection._setterInjection.impl;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by root on 8/3/17.
 */
public class ChineseFactoryBean implements FactoryBean<Chinese>
{

    @Override
    public Chinese getObject() throws Exception
    {
        return new Chinese();
    }

    @Override
    public Class<?> getObjectType()
    {
        return Chinese.class;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }
}
