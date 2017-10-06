package _learn.jars._Jnuit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by root on 5/16/17.
 */
public class _TestJunit
{
    int a, b;

    @Before
    public void setUp()
    {
        a = 1120;
        b = 1120;
    }

    @After
    public void tearDown()
    {
        System.out.println("test junit complete");
    }


    @Test
    public void testAdd()
    {
        assertEquals(a, b);
    }

    @Test
    @Ignore("not ready for test")
    public void testAddIgnore()
    {
        assertEquals(a, b*2);
    }

    @Test(timeout = 100)
    public void infinity()
    {
        while (true);
    }

    @Test(expected = ArithmeticException.class)
    public void division()
    {
        int i = 10/0;
    }
}
