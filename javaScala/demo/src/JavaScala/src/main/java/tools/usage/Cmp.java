package tools.usage;

import scala.Tuple2;

/**
 * Created by root on 7/8/17.
 */
public class Cmp implements java.util.Comparator<Tuple2<Object, Integer>>, java.io.Serializable
{
    public static final Cmp com = new Cmp();


    @Override
    public int compare(Tuple2<Object, Integer> t1, Tuple2<Object, Integer> t2)
    {
        return t1._2 > t2._2 ? 1 : (t1._2 == t2._2 ? 0 : -1);
    }
}
