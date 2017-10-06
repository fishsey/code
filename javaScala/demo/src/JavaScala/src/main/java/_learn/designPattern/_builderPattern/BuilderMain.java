package _learn.designPattern._builderPattern;

/**
 * Created by root on 4/13/17.
 */
public class BuilderMain
{
    public static void main(String args[])
    {
    
    }
}

class Product
{
    private String name;
    private String type;
    
    public void showProduct()
    {
        System.out.println("brand name: " + name);
        System.out.println("type: " + type);
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}

abstract class Builder
{
    //build product
    public abstract void setPart(String arg1, String arg2);
    //return product
    public abstract Product getProduct();
}

class ConcreteBuilder extends Builder
{
    private Product product = new Product();
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setPart(String arg1, String arg2)
    {
        product.setName(arg1);
        product.setType(arg2);
    }
}

class Director
{
    private Builder builder = new ConcreteBuilder();
    
    public Product getAProduct()
    {
        builder.setPart("BWM: ", "X7");
        return builder.getProduct();
    }
    
    public Product getBProduct()
    {
        builder.setPart("AUDI: ", "Q5");
        return builder.getProduct();
    }
}
