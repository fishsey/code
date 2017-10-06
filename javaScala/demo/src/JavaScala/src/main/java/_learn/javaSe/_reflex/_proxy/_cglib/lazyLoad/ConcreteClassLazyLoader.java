package _learn.javaSe._reflex._proxy._cglib.lazyLoad;

import net.sf.cglib.proxy.LazyLoader;


class ConcreteClassLazyLoader implements LazyLoader
{
    @Override
    public Object loadObject() throws Exception
    {
        System.out.println("before lazyLoader...");

        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("LazyLoader");

        System.out.println(propertyBean);
        System.out.println("after lazyLoader...");
        System.out.println();

        return propertyBean;
    }
}
