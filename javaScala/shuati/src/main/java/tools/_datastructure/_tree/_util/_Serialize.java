package tools._datastructure._tree._util;

import tools._datastructure._tree._base.TreeNode;

/**
 * Created by fishsey on 2017/3/21.
 */
public class _Serialize
{
    public static int index = -1;

    static String Serialize(TreeNode root)
    {
        StringBuffer sb = new StringBuffer();
        if (root == null)
        {
            sb.append("#,");
            return sb.toString();
        }
        sb.append(root.element + ",");
        sb.append(Serialize(root.left));
        sb.append(Serialize(root.right));
        return sb.toString();
    }


    static TreeNode Deserialize(String str)
    {
        index++;
        int len = str.length();
        if (index >= len)
        {
            return null;
        }
        String[] strr = str.split(",");
        TreeNode node = null;
        if (!strr[index].equals("#"))
        {
            node = new TreeNode(Integer.valueOf(strr[index]));
            node.left = Deserialize(str);
            node.right = Deserialize(str);
        }
        return node;
    }

}
