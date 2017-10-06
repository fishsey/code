package tools._datastructure._tree._util;

import java.util.PriorityQueue;
import java.util.Scanner;

public class _HuffmanCode
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a text: ");
        String text = input.nextLine();

        int[] counts = getCharacterFrequency(text); // Count frequency

        System.out.printf("%-15s%-15s%-15s%-15s\n", "ASCII Code", "Character", "Frequency", "Code");

        TreeNode tree = getHuffmanTree(counts); // Create a Huffman tree
        String[] codes = getCode(tree); // Get codes

        for (int i = 0; i < codes.length; i++)
            if (counts[i] != 0) // (char)i is not in text if counts[i] is 0
                System.out.printf("%-15d%-15s%-15d%-15s\n", i, (char) i + "", counts[i], codes[i]);
    }


    /**
     * Get Huffman codes for the characters
     * This method is called once after a Huffman tree is built
     */
    public static String[] getCode(TreeNode root)
    {
        if (root == null) return null;
        String[] codes = new String[256];
        assignCode(root, codes);
        return codes;
    }

    /* Recursively get codes to the leaf node */
    private static void assignCode(TreeNode root, String[] codes)
    {
        if (root.left != null)
        {
            root.left.code = root.code + "0";
            assignCode(root.left, codes);

            root.right.code = root.code + "1";
            assignCode(root.right, codes);
        } else
        {
            codes[(int) root.element] = root.code;
        }
    }


    /**
     * Get a Huffman tree from the codes
     */
    public static TreeNode getHuffmanTree(int[] counts)
    {
        // Create a heap to hold trees
        PriorityQueue<TreeNode> heap = new PriorityQueue<TreeNode>(); // Defined in Listing 24.10

        //into heap
        for (int i = 0; i < counts.length; i++)
        {
            if (counts[i] > 0)
            {
                heap.add(new TreeNode(counts[i], (char) i)); // A leaf node tree
            }
        }

        //poll and merge
        while (heap.size() > 1)
        {
            TreeNode t1 = heap.poll(); // Remove the smallest weight tree
            TreeNode t2 = heap.poll(); // Remove the next smallest weight
            heap.add(new TreeNode(t1, t2)); // Combine two trees
        }

        return heap.poll(); // The final tree
    }


    /**
     * Get the frequency of the characters
     */
    public static int[] getCharacterFrequency(String text)
    {
        int[] counts = new int[256]; // 256 ASCII characters

        for (int i = 0; i < text.length(); i++)
            counts[(int) text.charAt(i)]++; // Count the character in text

        return counts;
    }


    /**
     * Define a Huffman coding tree
     */
    public static class TreeNode implements Comparable<TreeNode>
    {
        Character element; // Stores the character for a leaf node
        int weight; // weight of the subtree rooted at this node
        TreeNode left; // Reference to the left subtree
        TreeNode right; // Reference to the right subtree
        String code = ""; // The code of this node from the root

        /**
         * Create a tree with two subtrees
         */
        public TreeNode(TreeNode t1, TreeNode t2)
        {
            this.left = t1;
            this.right = t2;
            this.weight = t1.weight + t2.weight;
        }

        /**
         * Create a tree containing a leaf node
         */
        public TreeNode(int weight, char element)
        {
            this.weight = weight;
            this.element = element;
        }


        /**
         * Compare trees based on their weights
         */
        public int compareTo(TreeNode o)
        {
            if (this.weight > o.weight)
                return 1;
            else if (this.weight == o.weight)
                return 0;
            else
                return -1;
        }

    }
}
