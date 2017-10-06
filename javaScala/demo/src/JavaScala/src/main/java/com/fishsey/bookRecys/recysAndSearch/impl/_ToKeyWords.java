package com.fishsey.bookRecys.recysAndSearch.impl;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.ml.feature.*;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class _ToKeyWords implements Serializable
{
    public static AtomicInteger NUM = new AtomicInteger(0);
    public static String[] keyWords;
    public static int TopK = 32;
    public static PrintStream cout;
    {
        try
        {
            cout = new PrintStream("tempData/newsWords-" + System.currentTimeMillis());
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    static class Node implements Comparable<Node>
    {
        int index;
        double tfidf;

        public Node(int index, double tfidf)
        {
            this.index = index;
            this.tfidf = tfidf;
        }

        @Override
        public int compareTo(Node o)
        {
            return -Double.compare(this.tfidf, o.tfidf);

        }

        @Override
        public String toString()
        {
            return "Node{" +
                    "index=" + _ToKeyWords.keyWords[index] +
                    ", tfidf=" + tfidf +
                    '}';
        }
    }

    @Test
    public void test_jdbc()
    {
        PropertyConfigurator.configure("tempData/log4j.properties");

        SparkSession sparkSession = SparkSession.builder()
                .appName("testJdbc").master("local[8]").getOrCreate();

        Dataset<Row> df = sparkSession
                .read()
                .format("jdbc")
                .option("url", "jdbc:mysql://10.65.1.62:3306")
                .option("dbtable", "wyNews.test")
                .option("user", "fishsey")
                .option("password", "0114")
                .load();

        Tokenizer tokenizer = new Tokenizer().setInputCol("keyWords").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(df);

        // TF
        CountVectorizerModel tf = new CountVectorizer()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .fit(wordsData);
        Dataset<Row> tfData = tf.transform(wordsData);

        //all words
        _ToKeyWords.keyWords = tf.vocabulary();

        // IDF
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(tfData);
        Dataset<Row> idfData = idfModel.transform(tfData);

        //persistence to disk
        idfData.select("newsId", "features").foreach((ForeachFunction<Row>) x -> persisTenceToDisk(x));

        cout.close();

    }

    private void persisTenceToDisk(Row row)
    {
        System.out.println(NUM.getAndIncrement());

        //output newId
        int newsId = row.getInt(0);
        cout.print(newsId + "\t");

        PriorityQueue<Node> pq = new PriorityQueue();
        //output topK words
        SparseVector words = (SparseVector) row.get(1);
        int[] indexs = words.indices();
        double[] tfidfs = words.values();
        for (int i = 0; i < indexs.length; i++)
        {
            pq.add(new Node(indexs[i], tfidfs[i]));
        }

        int number = _ToKeyWords.TopK;
        while (number-- > 0 && !pq.isEmpty())
        {
            Node temp = pq.poll();
            cout.print(temp.index + ":"+temp.tfidf + " ");
        }
        cout.print("\n");
    }

    //{
    //    Pattern pa = Pattern.compile(",\\[(.+?)\\]");
    //    Matcher match = pa.matcher(words.toString());
    //    ArrayList<String[]> res = new ArrayList<>();
    //    while (match.find())
    //    {
    //        res.add(match.group(1).split(","));
    //    }
    //    for (int i = 0; i < res.get(0).length; i++)
    //    {
    //        int index = Integer.parseInt(res.get(0)[i]);
    //        double tfidf = Double.parseDouble(res.get(1)[i]);
    //        pq.add(new Node(index, tfidf));
    //    }
    //}
}

