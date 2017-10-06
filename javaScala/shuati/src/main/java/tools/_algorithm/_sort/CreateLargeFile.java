package tools._algorithm._sort;

import java.io.*;

public class CreateLargeFile
{
    public static void main(String[] args) throws Exception
    {
        //生产 1个 int 数值泄露文件
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("largedata.dat")));
        for (int i = 0; i < 1e4; i++)
            output.writeInt((int) (Math.random() * 1000000));
        output.close();

        //Display first 100 numbers
        DataInputStream input =
                new DataInputStream(new FileInputStream("largedata.dat"));
        for (int i = 0; i < 100; i++)
            System.out.print(input.readInt() + " ");
        input.close();
    }
}
