package _learn.shuati.temp;

import java.util.ArrayList;
import java.util.Scanner;

public class Main3
{

    public static void main(String[] args)
    {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext())
        {
            ArrayList<String> alst = new ArrayList<>();
            int n = cin.nextInt();
            cin.nextLine();
            for (int i=0; i<n; i++)
            {
                alst.add(cin.nextLine());
            }
            getResult(alst);
        }
    }

    private static void getResult(ArrayList<String> alst)
    {
        System.out.println("YES");
    }
}
