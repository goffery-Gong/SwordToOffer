package part1;

public class HasSubTree {
    class BinaryTreeNode{
        double value; //注意是double类型
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public boolean hasSubTree(BinaryTreeNode root1, BinaryTreeNode root2){
        boolean result=false;

        //递归遍历树1，找到与树2根节点相同的节点。
        if(root1!=null && root2!=null){
            //找到了入口，进行下一步判断
            if(equal(root1.value,root2.value))
                result=doTree1HasTree2(root1, root2);
            if(!result)
                result=hasSubTree(root1.left, root2);
            if(!result)
                result=hasSubTree(root1.right,root2);
        }
        return result;
    }

    private boolean doTree1HasTree2(BinaryTreeNode root1, BinaryTreeNode root2) {
        if(root2==null)
            return true;
        if(root1==null)
            return false;
        //子节点值不同，返回
        if(!equal(root1.value,root2.value))
            return false;
        //节点值相同，则递归判断各自的左右节点
        return doTree1HasTree2(root1.left, root2.left)
                && doTree1HasTree2(root1.right,root2.right);
    }

    //double 类型的数值比较不能直接用==
    private boolean equal(double value, double value1) {
        if((value-value1>-0.000001) && (value-value1<0.000001))
            return true;
        else
            return false;
    }
}
