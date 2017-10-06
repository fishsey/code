package _temp._hadoop.wikiShortestPath;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GraphMain
{
   
    public static class Map extends Mapper<LongWritable, Text, IntWritable, Text>
    {
        private final static IntWritable one = new IntWritable(1);
        
        public void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException
        {
            //format: line[0] + "\t" + distance + ",null," + line[1]
            String[] inArray = value.toString().split("\t");
            int p = Integer.parseInt(inArray[0]);
            
            PageClass page = new PageClass(inArray[1]);
            
            //separate with '\t'
            context.write(new IntWritable(p), new Text(page.toString()));
            
            
            
            if (page.getdistance() != Integer.MAX_VALUE)
            {
                ArrayList<Integer> neighbors = page.getneighbors();
                
                if (neighbors != null && !neighbors.equals("null") && !neighbors.equals(""))
                {
                    for (int i = 0; i < neighbors.size(); i++)
                    {
                        int neighbour_id = neighbors.get(i);
                        int neighbour_distance = page.getdistance() + 1;
                        
                        ArrayList<Integer> neighbour_path = new ArrayList<Integer>();
                        if (page.getPath() != null && !page.getPath().equals("null") && !page.getPath().equals(""))
                        {
                            neighbour_path = new ArrayList<Integer>(page.getPath());// bug fixed: used shallow copy to avoid direct reference of the old object;
                        }
                        neighbour_path.add(p);
                        //ArrayList<Integer> neighbour_neighbour = new ArrayList<Integer>();
                        PageClass new_page = new PageClass(neighbour_distance, neighbour_path, null);
                        context.write(new IntWritable(neighbour_id), new Text(new_page.toString()));
                    }
                }
            }
        }
    }
    
    public static class Reduce extends Reducer<IntWritable, Text, IntWritable, PageClass>
    {
        private int endpage;
        private boolean found;
        
        public void setup(Context context) throws IOException,
                InterruptedException
        {
            Configuration conf = context.getConfiguration();
            endpage = conf.getInt("endpage", 1);
        }
        
        public void reduce(IntWritable key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException
        {
            Configuration conf = context.getConfiguration();
            
            int distance = Integer.MAX_VALUE;
            ArrayList<Integer> path = new ArrayList<Integer>();
            PageClass page = new PageClass();
            
            Iterator<Text> iter = values.iterator();
            while (iter.hasNext())
            {
                PageClass neighbors = new PageClass(iter.next().toString());
                
                //origin line neighbors is not null, but other lines is null
                if (neighbors.getneighbors() != null && neighbors.getneighbors().size() != 0)
                {
                    page = neighbors;
                }
                if (neighbors.getdistance() < distance)
                {
                    distance = neighbors.getdistance();
                    path = neighbors.getPath();
                }
            }
            if (key.get() == endpage && distance != Integer.MAX_VALUE)
            {
                path.add(key.get());//put the target node
                context.getConfiguration().setStrings(shortPath, path.toString());
                context.getConfiguration().setInt(shortDistance, distance);
                context.getConfiguration().setBoolean("found", true);
            }
            //update the min distance and path
            page.set(distance, path);
            context.write(key, page);
        }
    }
    
    public static void writeFile(int startPage, String source, Path inputPath, Job job) throws Exception
    {
        final int MAX = Integer.MAX_VALUE;
        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));
        
        final FileSystem fs = FileSystem.get(job.getConfiguration());
        OutputStreamWriter fstream = new OutputStreamWriter(fs.create(inputPath, true));
        BufferedWriter out = new BufferedWriter(fstream);
        
        String aLine = null;
        while ((aLine = in.readLine()) != null)
        {
            String[] alines = aLine.split(":");
            if (Integer.parseInt(alines[0]) == startPage)
            {
                //set the distance of the source node to 0;
                out.write(alines[0] + "\t" + 0 + ",null," + alines[1]);
                out.newLine();
                
            } else
            {
                //init set the distance of the all other nodes to source node is MAX;
                out.write(alines[0] + "\t" + MAX + ",null," + alines[1]);
                out.newLine();
            }
        }
        in.close();
        out.close();
    }
    
    static String shortPath = "shortPath";
    static String shortDistance = "shortDistance";
    static String abPath = "/root/AAA/";
    static String sourceDir = "dataset/pageRank";
    static final Path TMP_DIR = new Path(abPath + sourceDir);
    
    public static void main(String[] args) throws Exception
    {
         
        
        boolean cont = true; // flag to decide when to abort while loop
        int ct = 0; // decide if this is the first time run or not, first time run reads from original page file, other run reads from MapReduce output file
        int numLoop = 0; //the number of iterations
        int maxIters = 10;
        
        FileSystem fs;
        Job job = null;
        
        final int MAX = Integer.MAX_VALUE;
        String source =  "/root/AAA/dataset/wiki/wiki";//origin dataset
        int startPage = 2111690;//from
        int endPage = 4371964;//to
        
        System.out.println("Start Page is " + startPage);
        System.out.println("End Page is " + endPage);
        
        try
        {
            while (cont)
            {
                Configuration conf = new Configuration();
                conf.setInt("endpage", endPage);
                conf.setBoolean("found", false);
                job = Job.getInstance(conf);
                
                //job.setJarByClass(GraphMain.class);
                
                if (ct == 0)//first run of MapReducer
                {
                    Path Mapfile = new Path(TMP_DIR + "/input");
                    FileInputFormat.setInputPaths(job, Mapfile);
                    //process the origin dataset for first-map input
                    writeFile(startPage, source, Mapfile, job);
                }
                else
                    FileInputFormat.setInputPaths(job, new Path(TMP_DIR + "/output/o" + ct));
                
                if (ct > 1)
                {
                    fs = FileSystem.get(job.getConfiguration());
                    //delete last-last reducer output file with recursive
                    fs.delete(new Path(TMP_DIR + "/output/o" + (ct - 1)), true);
                }
                
                FileOutputFormat.setOutputPath(job, new Path(TMP_DIR + "/output/o" + (ct + 1)));
                
                job.setInputFormatClass(TextInputFormat.class);
                job.setOutputFormatClass(TextOutputFormat.class);
    
                job.setMapperClass(Map.class);
                job.setMapOutputKeyClass(IntWritable.class);
                job.setMapOutputValueClass(Text.class);
    
                job.setReducerClass(Reduce.class);
                job.setOutputKeyClass(IntWritable.class);
                job.setOutputValueClass(PageClass.class);
                
                //wait for complete
                job.waitForCompletion(true);
                
                if (conf.getBoolean("found", false)) //reach the result!!!!!!!
                {
                    cont = false;
                    System.out.println("shortest path found: " + conf.getStrings(shortPath));
                    System.out.println("shortest distance: " + conf.getStrings(shortDistance));
                    if (ct > 1)
                    {
                        fs = FileSystem.get(job.getConfiguration());
                        fs.delete(new Path(TMP_DIR + "/output/o" + (ct)), true);
                    }
                }
                if (numLoop >= maxIters)
                {
                    cont = false;
                    System.out.println("exceed max number of iteration");
                    if (ct > 1)
                    {
                        fs = FileSystem.get(job.getConfiguration());
                        fs.delete(new Path(TMP_DIR + "/output/o" + (ct)), true);
                    }
                }
                ct++;
                numLoop++;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            fs = FileSystem.get(job.getConfiguration());
            fs.delete(new Path(TMP_DIR + "/output"), true);
        }
        
    }
    
}

