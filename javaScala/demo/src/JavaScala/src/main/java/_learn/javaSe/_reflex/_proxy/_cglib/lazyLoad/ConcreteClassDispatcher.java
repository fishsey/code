package _learn.javaSe._reflex._proxy._cglib.lazyLoad;

import net.sf.cglib.proxy.Dispatcher;

/**
 * Created by fishsey on 2017/7/6.
 */
class ConcreteClassDispatcher implements Dispatcher
{

    @Override
    public Object loadObject() throws Exception
    {
        System.out.println("before Dispatcher...");

        PropertyBean propertyBean = new PropertyBean();
        propertyBean.setKey("Dispatcher");
        System.out.println(propertyBean);

        System.out.println("after Dispatcher...");
        System.out.println();
        return propertyBean;
    }

}
