package _learn.javaSe._reflex._anno.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ${DESCRIPTION}
 * Created by root on 7/13/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyAnnotation
{
    public String value();

    public String name() default "defaultName";

    public enum Color
    {
        BULE, RED, YELLOW

    }
    public Color appleColor() default Color.BULE;
}
