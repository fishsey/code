package _learn.jars._Jnuit;

/**
 * Created by root on 5/17/17.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class _Parameterized
{

    private int expected;
    private int first;
    private int second;
    private _Cal cal;

    public _Parameterized(int expectedResult, int firstNumber, int secondNumber)
    {
        this.expected = expectedResult;
        this.first = firstNumber;
        this.second = secondNumber;
    }

    @Before
    public void initialize()
    {
        cal = new _Cal();
    }


    @Parameterized.Parameters
    //based on construct pass paras: (int expectedResult, int firstNumber, int secondNumber)
    public static Collection addedNumbers()
    {
        return Arrays.asList(new Integer[][]{{3, 1, 2}, {5, 2, 3}, {7, 3, 4}, {9, 4, 5},});
    }

    @Test
    public void sum()
    {
        System.out.println("Addition with parameters : " + first + " and " + second);
        assertEquals(expected, cal.sum(first, second));
    }

    class _Cal
    {
        public int sum(int a, int b)
        {
            return a + b;
        }
    }

}


