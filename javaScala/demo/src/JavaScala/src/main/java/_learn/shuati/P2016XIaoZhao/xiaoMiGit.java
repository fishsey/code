package _learn.shuati.P2016XIaoZhao;

import java.util.ArrayDeque;
import java.util.Stack;

/**
 * xiaomi Git
 * Created by root on 1/9/17.
 */
public class xiaoMiGit
{
    public static void main(String args[])
    {
        String[] matrix = {"01011", "10100", "01000", "10000", "10000"};
        int indexA = 1;
        int indexB = 2;
    
        System.out.println(getSplitNode(matrix, indexA, indexB));
        
    }
    
    /**
     * 返回git树上两点的最近分割点
     * 
     * @param matrix 接邻矩阵，表示git树，matrix[i][j] == '1' 当且仅当git树中第i个和第j个节点有连接，节点0为git树的跟节点
     * @param indexA 节点A的index
     * @param indexB 节点B的index
     * @return 整型
     */
    public static int getSplitNode(String[] matrix, int indexA, int indexB)
    {
    
        int n = matrix.length;
        int[] isUsed = new int[n];//mark every node if used or not used
        int[] parent = new int[n];//mark the parent of every node
        for (int i=0; i<n; i++)
        {
            isUsed[i] = 0; //o means not used
            parent[0] = -1; //-1 means null
        }
        
        //updateParentWithBFS(matrix, isUsed, parent);
        updateParentWithDFS(matrix, isUsed, parent);
    
    
        ArrayDeque<Integer> pathA = getPath(parent, indexA);
        ArrayDeque<Integer> pathB = getPath(parent, indexB);
    
        System.out.println(pathA);
        System.out.println(pathB);
        
        return getLatestAncestor(pathA, pathB);
        
    }


    public static ArrayDeque<Integer> getPath(int[] parent, int indexA)
    {
        ArrayDeque<Integer> path = new ArrayDeque<>();
        path.addFirst(indexA);
        while (parent[indexA] != -1 )
        {
            indexA = parent[indexA];
            path.addFirst(indexA);
        }
        return  path;

    }

    //[0,1,2,3], [0,1, 7, 4] return 1 as the latest ancestor
    public static int getLatestAncestor(ArrayDeque<Integer> pathA, ArrayDeque<Integer> pathB)
    {
        int parent = 0;
        while (!pathA.isEmpty() && !pathB.isEmpty())
        {
            int temp = pathA.pollFirst();
            if (temp == pathB.pollFirst())
                parent = temp;
            else
                return parent;
        }
        return parent;
    }
    

    //bfs
    public static void updateParentWithBFS(String[] matrix, int[] isUsed, int[] parent)
    {
        Stack<Integer> stack = new Stack<>();
        //init all vars
        stack.push(0); //push the root node
        parent[0] = -1;
        isUsed[0] = 1;
        
        while (!stack.isEmpty())
        {
            int node = stack.pop();
            for (int i=0; i<matrix.length; i++)
            {
                //if <node, i> has edge and node i is not used, then push in stack
                if (matrix[node].charAt(i)=='1' && isUsed[i] == 0)
                {
                    stack.push(i);
                    parent[i] = node;
                    isUsed[i] = 1;
                }
            }
        }
        
    }


    //dfs, has fag mark
    public static void updateParentWithDFS(String[] matrix, int[] isUsed, int[] parent)
    {
        Stack<Integer> stack = new Stack<>();
        //init all vars
        stack.push(0); //push the root node
        parent[0] = -1;
        isUsed[0] = 1;
        int flag;
        while (!stack.isEmpty())
        {
            flag = 0;
            int node = stack.peek();
            for (int i=0; i<matrix.length; i++)
            {
                //if <node, i> has edge and node i is not used, then push in stack
                if (matrix[node].charAt(i)=='1' && isUsed[i] == 0)
                {
                    stack.push(i);
                    parent[i] = node;
                    isUsed[i] = 1;
                    flag = 1;
                    break;
                }
            }
            
            if (flag == 0)//not unused edge
                stack.pop();
        }
        
    }

    
    
   
}
