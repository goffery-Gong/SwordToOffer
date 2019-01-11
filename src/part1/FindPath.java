package part1;

import java.util.Stack;

/**
 * @Auther: PC
 * @Date: 2019/1/11 09:49
 * @Description:
 */
public class FindPath {
    public void findPath(BinaryTreeNode root, int num) {
        if (root == null)
            return;

        Stack<Integer> stack = new Stack<>();
        int curSum = 0;
        //前序遍历根节点，通过栈保存节点；如果节点和为num，则输出
        findPath(root, num, stack, curSum);
    }

    private void findPath(BinaryTreeNode root, int num, Stack<Integer> stack, int curSum) {
        boolean isLeaf = root.left == null && root.right == null;
        stack.push(root.value);
        curSum += root.value;

        //当访问到叶节点，并且num符合
        if (isLeaf && curSum == num)
            for (Integer i : stack)
                System.out.println(i);

        if (root.left != null)
            findPath(root.left, num, stack, curSum);
        if (root.right != null)
            findPath(root.right, num, stack, curSum);

        //再返回父节点前，在路径上删除当前节点
        //两种情况会访问到这：叶节点；某节点的子路径都访问完了，没有符合条件的路径，要返回上一层节点
        stack.pop();
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);

        new FindPath().findPath(node, 10);
    }
}
