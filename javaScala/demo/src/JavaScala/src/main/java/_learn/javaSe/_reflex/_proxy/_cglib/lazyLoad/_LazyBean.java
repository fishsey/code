package _learn.javaSe._reflex._proxy._cglib.lazyLoad;

import net.sf.cglib.proxy.Enhancer;

public class _LazyBean
{
    private String name;
    private int age;

    //lazy attribute, loaded if need
    public PropertyBean propertyBeanLazyLoader;//
    public PropertyBean propertyBeanDispatcher;

    public _LazyBean()
    {
    }

    public _LazyBean(String name, int age)
    {
        this.name = name;
        this.age = age;
        this.propertyBeanLazyLoader = createPropertyBeanLazyLoader();
        this.propertyBeanDispatcher = createPropertyBeanDispatcher();
    }


    /**
     * 只第一次懒加载
     *
     * 对需要延迟加载的对象添加回调方法。
     * 在CGLib的实现中只要去访问 lazy 对象的 getter方法，就会自动触发（代理类）回调方法。
     */
    private PropertyBean createPropertyBeanLazyLoader()
    {
        Enhancer enhancer = new Enhancer();
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassLazyLoader());
        return pb;
    }


    /**
     * 每次都懒加载
     */
    private PropertyBean createPropertyBeanDispatcher()
    {
        Enhancer enhancer = new Enhancer();
        PropertyBean pb = (PropertyBean) enhancer.create(PropertyBean.class, new ConcreteClassDispatcher());
        return pb;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }

    public PropertyBean getpropertyBeanLazyLoader()
    {
        return propertyBeanLazyLoader;
    }
    public void setpropertyBeanLazyLoader(PropertyBean propertyBean)
    {
        this.propertyBeanLazyLoader = propertyBean;
    }

    public PropertyBean getPropertyBeanDispatcher()
    {
        return propertyBeanDispatcher;
    }
    public void setPropertyBeanDispatcher(PropertyBean propertyBeanDispatcher)
    {
        this.propertyBeanDispatcher = propertyBeanDispatcher;
    }

}

