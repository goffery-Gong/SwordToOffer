package part1;

import java.util.Stack;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/5 10:07
 * @Description:
 */
public class IsBalanceTree {
    /**
     * 方法1，从上到下，有重复遍历
     *
     * @param root
     * @return
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null)
            return true;
        //先分别从root开始计算左右子树的深度，然后进行判断
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
        if (Math.abs(left - right) > 1)
            return false;
        //然后递归从上至下判断节点
        return IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }

    //树深度
    private int TreeDepth(TreeNode root) {
        if (root == null)
            return 0;
        int left = TreeDepth(root.left);
        int right = TreeDepth(root.right);
//        return (left > right) ? (left + 1) : (right + 1);
        return 1 + Math.max(left, right);
    }

    /**
     * 通过后续遍历，从下向上判断节点是否平衡
     * 不平衡就直接跳出
     *
     * @param root
     * @return
     */
    private boolean imbalance = true;

    public boolean IsBalancedBack(TreeNode root) {
        if (root == null)
            return true;
        getDepth(root);
        return imbalance;
    }


    private int getDepth(TreeNode root) {
        int left = 0;
        int right = 0;
        if (imbalance) {
            if (root.left != null)
                left = getDepth(root.left);
            if (root.right != null)
                right = getDepth(root.right);
            if (Math.abs(left - right) > 1)
                imbalance = false;
            return (left > right) ? (left + 1) : (right + 1);
        }
        return -1;
    }

    public void reverse(char[] chars, int low, int high) {
        while (low < high) {
            char temp = chars[low];
            chars[low] = chars[high];
            chars[high] = temp;
            low++;
            high--;
        }
    }

    private void swap(char[] chars, int low, int high) {
        char temp = chars[low];
        chars[low] = chars[high];
        chars[high] = temp;
    }

    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c'};
        new IsBalanceTree().swap(chars, 0, 1);
        for (char c : chars)
            System.out.print(c+" ");
    }
}
