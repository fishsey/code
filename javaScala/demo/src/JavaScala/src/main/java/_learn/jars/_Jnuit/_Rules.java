package _learn.jars._Jnuit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.junit.Assert.assertEquals;


/**
 * Created by root on 5/16/17.
 */
public class _Rules
{
    @Rule
    public TestName name = new TestName();

    @Test
    public void testA()
    {
        assertEquals("testA", name.getMethodName());
    }

    @Test
    public void testB()
    {
        assertEquals("testB", name.getMethodName());
    }
}
