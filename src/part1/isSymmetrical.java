package part1;

public class isSymmetrical {
    public boolean isSymmetrical(BinaryTreeNode root){
        return isSymmetrical(root ,root);
    }

    private boolean isSymmetrical(BinaryTreeNode root1, BinaryTreeNode root2) {
        //递归终止条件
        if(root1 == null && root2==null)
            return true;
        if(root1==null || root2==null)
            return false;

        //根节点不相等就返回false
        if(root1.value!=root2.value)
            return false;

        //根节点相同再判断左右节点(前序遍历，对称前序遍历)
        return isSymmetrical(root1.left,root2.right)
                && isSymmetrical(root1.right,root2.left);
    }
}
