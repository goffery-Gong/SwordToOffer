package part1;

import java.util.Stack;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/11 15:17
 * @Description:
 */
public class BSTtoDeList {
    TreeNode head;
    TreeNode lastNodeInList;

    /**
     * 递归，中序遍历
     * @param pRootOfTree
     * @return
     */
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

    /**
     * 非递归，使用栈实现中序遍历的非递归算法，便可以找出节点的先后关系，依次连接即可
     * @param root
     * @return
     */
    public TreeNode Convert(TreeNode root) {
        if(root==null)
            return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode p = root;
        TreeNode pre = null;// 用于保存中序遍历序列的上一节点
        boolean isFirst = true;
        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if(isFirst){
                root = p;// 将中序遍历序列中的第一个节点记为root
                pre = root;
                isFirst = false;
            }else{
                pre.right = p;
                p.left = pre;
                pre = p;
            }
            p = p.right;
        }
        return root;
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