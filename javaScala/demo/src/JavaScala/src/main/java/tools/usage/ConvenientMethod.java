package tools.usage;

import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.ml.linalg.DenseVector;

import java.util.Iterator;

/**
 * Created by root on 7/8/17.
 */
public class ConvenientMethod
{
    public static int getLen(Iterable<String[]> iter)
    {
        int count = 0;
        Iterator itab = iter.iterator();
        while (itab.hasNext())
        {
            itab.next();
            count++;
        }
        return count;
    }

    public static int getLenString(Iterable<String> iter)
    {
        int count = 0;
        Iterator itab = iter.iterator();
        while (itab.hasNext())
        {
            itab.next();
            count++;
        }
        return count;
    }

    public static LabeledPoint transToLabelPoint(String iter)
    {
        String[] fields = iter.trim().split("\t");
        double label = Double.parseDouble(fields[fields.length-1]);
        double[] values = new double[fields.length-1];
        for (int i = 0; i < fields.length-1; i++)
        {
            values[i] = Double.parseDouble(fields[i]);
        }

        return new LabeledPoint(label, new DenseVector(values));
    }
}
