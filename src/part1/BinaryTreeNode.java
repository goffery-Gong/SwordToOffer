package part1;

public class BinaryTreeNode {
    int value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    public void preOrderRec(BinaryTreeNode root){
        if(root==null)
            return;
        System.out.print(root.value+" ");
        preOrderRec(root.left);
        preOrderRec(root.right);
    }

    public void inOrderRec(BinaryTreeNode root){
        if(root==null)
            return;

        inOrderRec(root.left);
        System.out.println(root.value);
        inOrderRec(root.right);
    }
}
