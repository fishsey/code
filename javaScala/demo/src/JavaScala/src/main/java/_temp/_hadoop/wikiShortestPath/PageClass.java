package _temp._hadoop.wikiShortestPath;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

public class PageClass implements Writable
{
    
    private int distance;
    private ArrayList<Integer> path = new ArrayList<Integer>();
    private ArrayList<Integer> neighbors = new ArrayList<Integer>();
    
    public PageClass()
    {
        set(Integer.MAX_VALUE, new ArrayList<Integer>(), new ArrayList<Integer>());
    }
    
    public PageClass(int distance, ArrayList<Integer> path, ArrayList<Integer> neighbors)
    {
        set(distance, path, neighbors);
    }
    
    public PageClass(String first)
    {
        //first: distance + ",null," + line[1]
        String[] input = first.split(",");
        ArrayList<Integer> path = new ArrayList<Integer>();
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        
        if (input[1] != null && !input[1].equals("null") && !input[1].equals(""))
        {
            //any white space chars
            String[] pathstring = input[1].trim().split("\\s+");
            for (int n = 0; n < pathstring.length; n++)
            {
                path.add(Integer.parseInt(pathstring[n]));
            }
        } else
        {
            path = null;
        }
        if (input[2] != null && !input[2].equals("null") && !input[2].equals(""))
        {
            String[] neighborstring = input[2].trim().split("\\s+");
            for (int n = 0; n < neighborstring.length; n++)
            {
                neighbors.add(Integer.parseInt(neighborstring[n]));
            }
        } else
        {
            neighbors = null;
        }
        set(Integer.parseInt(input[0]), path, neighbors);
    
    }
    
    
    public PageClass get()
    {
        
        return this;
    }
    
    public ArrayList<Integer> getPath()
    {
        return path;
    }
    
    public ArrayList<Integer> getneighbors()
    {
        return neighbors;
    }
    
    public int getdistance()
    {
        
        return distance;
        
    }
    
    public void set(int distance, ArrayList<Integer> path, ArrayList<Integer> neighbors)
    {
        this.path = path;
        this.neighbors = neighbors;
        this.distance = distance;
    }
    
    public void set(int distance, ArrayList<Integer> path)
    {
        this.path = path;
        this.distance = distance;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException
    {
        //mr_pageRank.PageClass p = new mr_pageRank.PageClass();
        distance = in.readInt();
        int path_size = in.readInt();
        
        for (int i = 0; i < path_size; i++)
        {
            path.add(in.readInt());
            
        }
        int neighbors_size = in.readInt();
        for (int i = 0; i < neighbors_size; i++)
        {
            neighbors.add(in.readInt());
            
        }
        
    }
    
    @Override
    public void write(DataOutput out) throws IOException
    {
        out.writeInt(distance);
        if (path != null && path.size() != 0)
        {
            out.writeInt(path.size());
            for (int i = 0; i < path.size(); i++)
            {
                out.writeInt(path.get(i));
            }
        } else
        {
            out.writeInt(0);
        }
        if (neighbors != null && neighbors.size() != 0)
        {
            out.writeInt(neighbors.size());
            for (int i = 0; i < neighbors.size(); i++)
            {
                out.writeInt(neighbors.get(i));
            }
        } else
        {
            out.writeInt(0);
        }
    }
    
    @Override
    public String toString()
    {
        String path_str = null;
        String neighbors_str = null;
        
        if (path != null && path.size() != 0)
        {
            path_str = path.toString().replaceAll("[^0-9]", " ");
        } else
        {
            path_str = "null";
        }
        if (neighbors != null && neighbors.size() != 0)
        {
            neighbors_str = neighbors.toString().replaceAll("[^0-9]", " ");
        } else
        {
            neighbors_str = "null";
        }
        return Integer.toString(distance) + "," + path_str + "," + neighbors_str;
    }
}


