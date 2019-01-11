package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/11 15:17
 * @Description:
 */
public class BSTtoDeList {
    TreeNode head;
    TreeNode lastNodeInList;

    TreeNode doBSTtoDeList(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return null;
        //转换左子树
        if (pRootOfTree.left != null)
            doBSTtoDeList(pRootOfTree.left);

        if (lastNodeInList == null) {
            lastNodeInList = pRootOfTree;
            head = pRootOfTree;
        } else {
            lastNodeInList.right = pRootOfTree;
            pRootOfTree.left = lastNodeInList;
            lastNodeInList = pRootOfTree;
        }

        if (pRootOfTree.right != null)
            doBSTtoDeList(pRootOfTree.right);

        return head;
    }


    /**
     * 右，中，左中序遍历，直接返回倒序排列的链表
     * @param pRootOfTree
     * @return
     */
    public TreeNode doBSTtoDeListBetter(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return pRootOfTree;

        doBSTtoDeListBetter(pRootOfTree.right);
        if (head == null) {
            head = pRootOfTree;
        } else {
            head.left = pRootOfTree;
            pRootOfTree.right = head;
            head = pRootOfTree;
        }
        doBSTtoDeListBetter(pRootOfTree.left);

        return head;
    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}