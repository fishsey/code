package _temp._hadoop.coocurrMatrixCF;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 10/11/16.
 */
public class S1ToUserRatingVectorMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable>
{
    private static final Pattern NUMBERS = Pattern.compile("(\\d+)");
    
    @Override
    //input-test: "userId: itemId1, itemId2, . . ."
    //output: "userId: itemId1", "userId: itemId2", . . .
    protected void map(LongWritable key, Text text, Context context) throws IOException, InterruptedException
    {
        String line = text.toString();
        Matcher m = NUMBERS.matcher(line);
        m.find();
        
        IntWritable userId = new IntWritable(Integer.parseInt(m.group()));
        IntWritable itemId;
        
        while (m.find())
        {
            itemId = new IntWritable(Integer.parseInt(m.group()));
            context.write(userId, itemId);
        }
        
    }
}
