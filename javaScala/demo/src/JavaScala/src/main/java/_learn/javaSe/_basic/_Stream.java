package _learn.javaSe._basic;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by fishsey on 2016/8/4.
 */
public class _Stream
{
    @Test
    public void test_1()
    {
        //create stream
        List<String> ls_1 = Stream.of("a", "b", "c")
                .collect(Collectors.toList());//转换成集合
        System.out.println(ls_1);
        System.out.println();

        List<String> ls_2 = Arrays.stream(new String[]{"a", "b", "c"})
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(ls_2);
        System.out.println();

        List<Integer> ls_3 = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .peek(x -> System.out.println(x)) //return origin stream and add a op
                .flatMap(numbers ->  numbers.stream())
                .collect(Collectors.toList());
        System.out.println(ls_3);
        System.out.println();

        new ArrayList<>(Arrays.asList("a", "b", "c")).stream().forEach(System.out::print);
    }

    @Test
    public void test_2()
    {
        //将所有元素拼接成一个字符串
        String str = Arrays.asList("aa", "bb", "cc").stream()
                .collect(Collectors.joining("/", "[", "]"));
        System.out.println(str);
    }

    @Test
    public void test_3()
    {
        //比较器
        String max_1 = Arrays.stream(new String[]{"aa", "bbb", "cccc"})
                //.max(String::compareTo)
                .max(Comparator.comparing(elem -> elem.length()))
                .get();
        System.out.println(max_1);
        System.out.println();


        Optional<String> max_2 =  Arrays.stream(new String[]{"aa", "bbb", "cccc"})
                .collect(Collectors.maxBy(Comparator.comparing(String::length)));
        System.out.println(max_2.get());
    }

    @Test
    public void test_4()
    {
        //数据分块收集
        Map<Boolean,List<String>> map = Arrays.asList("aa", "ab", "bc").stream()
                .collect(Collectors.partitioningBy(x -> x.startsWith("a")));
        map.forEach((key, value) -> System.out.println(key + " " + value));
    }


    @Test
    public void test_5()
    {
        //r_2 == r_1
        Random random = new Random();
        ArrayList<Integer> r_2 = IntStream.range(0, 10)
                .map(x -> random.nextInt(6)+1)
                .collect(ArrayList<Integer>::new,
                        (list, elem) -> list.add(elem),
                        (list1, list2) -> list1.addAll(list2));


        Map<Integer, java.lang.Double> r_3 = IntStream.range(0, 10)
                .mapToObj(x -> random.nextInt(6)+1)
                //.collect(Collectors.groupingBy(num -> num, Collectors.counting()));
                .collect(Collectors.groupingBy(num -> num, Collectors.summingDouble(n -> 1.0/10)));
        System.out.println(r_3);
    }

}

