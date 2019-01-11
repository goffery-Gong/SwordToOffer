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

    public void backOrderRec(BinaryTreeNode root){
        if(root==null)
            return;

        backOrderRec(root.left);
        backOrderRec(root.right);
        System.out.println(root.value);
    }
    /**
     * 在节点下添加新子树
     * @param leftVal
     * @param rigthVal
     */
    void add(int leftVal, int rigthVal){
        BinaryTreeNode node=new BinaryTreeNode();
        BinaryTreeNode node2=new BinaryTreeNode();
        node.value=leftVal;
        node2.value=rigthVal;
        left=node;
        right=node2;
    }
}
