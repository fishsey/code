import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fishsey on 2017/8/12.
 */
public class Tools
{
    public static ArrayList<Integer> toIntsArrayList(String line)
    {
        String[] keys = line.trim().split(" ");
        ArrayList<Integer> results = new ArrayList<>();
        for (String key : keys)
        {
            results.add(Integer.parseInt(key.trim()));
        }
        return results;
    }

    public static int[] toIntsArray(String line)
    {
        String[] keys = line.trim().split(" ");
        int[] result = new int[keys.length];
        int count = 0;
        for (String key : keys)
        {
            result[count++] = Integer.parseInt(key.trim());
        }
        return result;
    }

    public static  void printArray(int[] array)
    {
        System.out.println(array.length);
        for (int i : array)
        {
            System.out.print(i + " ");
        }
    }


    //returan all the index in text that matcher regx
    private static void isMatch(String input, ArrayList<Integer> result)
    {
        String[] temp = input.split(" ");
        String text = temp[0];
        String regx = temp[1];
        StringBuffer targetRe = new StringBuffer();
        for (int i = 0; i < regx.length(); i++)
        {
            targetRe.append(regx.replace(regx.charAt(i), '.') + "|");
        }
        Pattern pr = Pattern.compile(targetRe.toString());
        Matcher match = pr.matcher(text);

        while (match.find())
        {
            if (match.group().length() > 0)
            {
                result.add(match.start());
            }
        }
    }
}
