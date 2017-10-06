package _temp._hadoop.wikiPageRank;

import java.util.ArrayList;


public class PageRankClass
{
    
    private double rank;
    private ArrayList<Integer> neighbors = new ArrayList<Integer>();
    
    public PageRankClass()
    {
        set(1, new ArrayList<Integer>());
    }
    
    public PageRankClass(double rank, ArrayList<Integer> neighbors)
    {
        set(rank, neighbors);
    }
    
    public PageRankClass(String first)
    {
        String[] input = first.split(",");
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        
        if (input[1] != null && !input[1].equals("null") && !input[1].equals(""))
        {
            //any white-space
            String[] neighborstring = input[1].trim().split("\\s+");
            for (int n = 0; n < neighborstring.length; n++)
            {
                neighbors.add(Integer.parseInt(neighborstring[n]));
            }
        } else
        {
            neighbors = null;
        }
        set(Double.parseDouble(input[0]), neighbors);
    }
    
    
    public PageRankClass get()
    {
        return this;
    }
    
    public ArrayList<Integer> getneighbors()
    {
        return neighbors;
    }
    
    public double getrank()
    {
        
        return rank;
        
    }
    
    public void set(double rank, ArrayList<Integer> neighbors)
    {
        this.rank = rank;
        this.neighbors = neighbors;
    }
    
    public void set(double rank)
    {
        this.rank = rank;
    }
    
    public String toString()
    {
        String neighbors_str = null;
        if (neighbors != null && neighbors.size() != 0)
        {
            neighbors_str = neighbors.toString().replaceAll("[^0-9]", " ");
        } else
        {
            neighbors_str = "null";
        }
        return Double.toString(rank) + "," + neighbors_str;
    }
    
}


