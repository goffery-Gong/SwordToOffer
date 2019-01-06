package part1;

public class MirrorTree {

    public void mirrorTree(BinaryTreeNode treeNode){
        if(treeNode!=null)
            return;

        //叶节点就返回
        if(treeNode.left==null && treeNode.right==null)
            return;

        //交换左右节点
        BinaryTreeNode temp=treeNode.left;
        treeNode.left=treeNode.right;
        treeNode.right=temp;

        //分析左节点/右节点之下
        if(treeNode.left!=null)
            mirrorTree(treeNode.left);
        if(treeNode.right!=null)
            mirrorTree(treeNode.right);
    }
}
