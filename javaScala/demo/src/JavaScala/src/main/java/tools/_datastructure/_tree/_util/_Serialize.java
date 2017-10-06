package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/3/21.
 */
public class _Serialize
{
    public static int index = -1;

    static String serialize(TreeNode root)
    {
        StringBuffer sb = new StringBuffer();
        if (root == null)
        {
            sb.append("#,");
            return sb.toString();
        }
        sb.append(root.element + ",");
        sb.append(serialize(root.left));
        sb.append(serialize(root.right));
        return sb.toString();
    }


    static TreeNode deserialize(String str)
    {
        int len = str.length();
        if (len <= 0)
        {
            return null;
        }
        return  deserialize(str.split(","));

        //index++;
        //int len = str.length();
        //if (index >= len)
        //{
        //    return null;
        //}
        //String[] strr = str.split(",");
        //TreeNode node = null;
        //if (!strr[index].equals("#"))
        //{
        //    node = new TreeNode(Integer.valueOf(strr[index]));
        //    node.left = Deserialize(str);
        //    node.right = Deserialize(str);
        //}
        //return node;
    }

    static TreeNode deserialize(String[] strr)
    {
        index++;
        TreeNode node = null;
        if (!strr[index].equals("#"))
        {
            node = new TreeNode(Integer.valueOf(strr[index]));
            node.left = deserialize(strr);
            node.right = deserialize(strr);
        }
        return node;
    }

}
