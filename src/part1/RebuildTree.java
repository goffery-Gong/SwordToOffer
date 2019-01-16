package part1;

import java.util.Arrays;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/15 09:58
 * @Description:
 */
public class RebuildTree {
    /**
     * 重建二叉树
     * @param preOrder
     * @param midOrder
     * @return
     */
    BinaryTreeNode reBuildTree(int[] preOrder, int[] midOrder) {
        if (preOrder == null || midOrder == null)
            return null;
        return reBuildTree(preOrder, 0, preOrder.length - 1, midOrder, 0, midOrder.length - 1);
    }

    private BinaryTreeNode reBuildTree(int[] preOrder, int preStart, int preEnd, int[] midOrder, int midStart, int midEnd) {
        if (preStart > preEnd || midStart > midEnd)
            return null;
        //前序遍历的第一个为根节点，构建根节点
        BinaryTreeNode root = new BinaryTreeNode(preOrder[preStart]);


        //对应到中序遍历，划分左右分支
        int border = midStart;
        while (border <= midEnd && midOrder[border] != preOrder[preStart])
            border++;

        int leftLength = border - midStart;
        int leftPreEnd = preStart + leftLength;//前序遍历序列左分支长度
        root.left = reBuildTree(preOrder, preStart + 1, leftPreEnd, midOrder, midStart, border - 1);
        root.right = reBuildTree(preOrder, leftPreEnd + 1, preEnd, midOrder, border + 1, midEnd);
        return root;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] b = {4, 7, 2, 1, 5, 3, 8, 6};
        BinaryTreeNode root = new RebuildTree().reBuildTree(a, b);
        System.out.println(root);
    }

}
