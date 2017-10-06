package _learn.javaSe._reflex._anno;

import _learn.javaSe._reflex._anno.impl.MyAnnotation;
import _learn.javaSe._reflex._anno.impl.MyAnnotationFields;
import _learn.javaSe._reflex._anno.impl.MyAnnotationMethod;
import _learn.javaSe._reflex._anno.impl.MyAnnotationMethodParas;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fishsey on 2017/3/20.
 */

//class-Annotation
@MyAnnotation(name = "class", value = "class-Annotation")
public class _Annotation
{
    @MyAnnotationFields(name = "field_hello", value = "field-Annotation")
    public String text1 = "hello";
    @MyAnnotationFields(name = "field_fishsey", value = "field-Annotation")
    public String text2 = "fishsey";


    @MyAnnotationMethod(name = "method", value = "method-Annotation")
    public void doSomething()
    {
    }


    public void methodParas(
            @MyAnnotationMethodParas(name = "s1", value = "methodParas-Annotation")
                    String[] s1,
            @MyAnnotationMethodParas(name = "s2", value = "methodParas-Annotation")
                    String s2)
    {
    }




    @Test
    public void testClassAnnotation()
    {
        Class aclass = _Annotation.class;
        Annotation annotation = aclass.getAnnotation(MyAnnotation.class);
        if (annotation instanceof MyAnnotation)
        {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.out.print("name: " + myAnnotation.name() + "\t");
            System.out.println("value: " + myAnnotation.value());
        }
    }

    @Test
    public  void testMethodAnnotation() throws NoSuchMethodException
    {
        Class aclass = _Annotation.class;
        Method method = aclass.getMethod("doSomething", null);
        Annotation annotation = method.getAnnotation(MyAnnotationMethod.class);
        if (annotation instanceof MyAnnotationMethod)
        {
            MyAnnotationMethod myAnnotation = (MyAnnotationMethod) annotation;
            System.out.print("name: " + myAnnotation.name() + "\t");
            System.out.println("value: " + myAnnotation.value());
        }
    }

    @Test
    public  void testMethodParasAnnotation() throws NoSuchMethodException
    {
        Class aclass = _Annotation.class;
        Method method = aclass.getMethod("methodParas", String[].class, String.class);
        Annotation[][] parasAnnos = method.getParameterAnnotations();
        Class[] parasTypes = method.getParameterTypes();

        for (int i1 = 0; i1 < parasAnnos.length; i1++)
        {
            Annotation[] parasAnno = parasAnnos[i1];
            Class parasType = parasTypes[i1];
            for (Annotation annotation : parasAnno)
            {
                if (annotation instanceof MyAnnotationMethodParas)
                {
                    MyAnnotationMethodParas myAnnotation = (MyAnnotationMethodParas) annotation;
                    System.out.print("param: " + parasType.getName() + "\t");
                    System.out.print("name: " + myAnnotation.name() + "\t");
                    System.out.println("value: " + myAnnotation.value());
                }
            }
        }

    }

    @Test
    public  void testFieldAnnotation()
    {
        Class aclass = _Annotation.class;
        Field[] fields = aclass.getFields();
        for (Field field : fields)
        {
            Annotation[] annos = field.getDeclaredAnnotations();
            for (Annotation annotation : annos)
            {
                if (annotation instanceof MyAnnotationFields)
                {
                    MyAnnotationFields myAnnotation = (MyAnnotationFields) annotation;
                    System.out.print("filedName: " + field.getName() + "\t");
                    System.out.print("name: " + myAnnotation.name() + "\t");
                    System.out.println("value: " + myAnnotation.value());
                }
            }
        }

    }
}








