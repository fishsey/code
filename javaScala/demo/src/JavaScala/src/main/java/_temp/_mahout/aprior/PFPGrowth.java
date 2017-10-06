package _temp._mahout.aprior;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.FileLineIterable;
import org.apache.mahout.common.iterator.StringRecordIterator;
import org.apache.mahout.fpm.pfpgrowth.convertors.ContextStatusUpdater;
import org.apache.mahout.fpm.pfpgrowth.convertors.SequenceFileOutputCollector;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.StringOutputConverter;
import org.apache.mahout.fpm.pfpgrowth.convertors.string.TopKStringPatterns;
import org.apache.mahout.fpm.pfpgrowth.fpgrowth.FPGrowth;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fishsey on 2017/3/19.
 */
public class PFPGrowth
{
    public static void main(String[] args) throws IOException
    {
        //environment and log4j setting
        System.setProperty("hadoop.home.dir", "C:\\Users\\ally1\\Desktop\\hadoop-2.6.2");
        PropertyConfigurator.configure("log4j.properties");

        String inputFile = "D:/baiduYun/syn/Github/dataset/fpTest";
        String outputFile = "D:/baiduYun/syn/Github/dataset/fpgrowth-output";
        //hdfs envir setup
        Path outputPath = new Path(outputFile);
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        //paras setup
        int minSupport = 1;
        int maxHeapSize = 5;//top-k freqItems for specify features
        Set<String> features = new HashSet<String>();
        features.add("1");
        features.add("4");
        String pattern = " +";//line split flag
        Charset encoding = Charset.forName("UTF-8");
        //output collectors
        SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, outputPath, Text.class, TopKStringPatterns.class);

        FPGrowth<String> fp = new FPGrowth<String>();
        //generate top-k freItems into outputPath
        fp.generateTopKFrequentPatterns(
                new StringRecordIterator(new FileLineIterable(new File(inputFile), encoding, false), pattern),
                fp.generateFList(new StringRecordIterator(new FileLineIterable(new File(inputFile), encoding, false), pattern), minSupport),
                minSupport,
                maxHeapSize,
                features,
                new StringOutputConverter(new SequenceFileOutputCollector<Text, TopKStringPatterns>(writer)),
                new ContextStatusUpdater(null));
        writer.close();
        //read the freqItems result
        List<Pair<String, TopKStringPatterns>> frequentPatterns = FPGrowth.readFrequentPattern(conf, outputPath);
        for (Pair<String, TopKStringPatterns> entry : frequentPatterns)
        {
            System.out.print(entry.getFirst()+"-"); //each feature
            System.out.println(entry.getSecond()); //the freqItems for specify feature
        }


        //generate freItems
        //List<Pair<String, Long>> freq = fp.generateFList(new StringRecordIterator(new FileLineIterable(new File(inputFile), encoding, false), pattern), minSupport);
        //freq.forEach(System.out::println);

    }
}

