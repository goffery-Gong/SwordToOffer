package part1;

import java.util.*;


public class PrintBinaryTree {
    /**
     * 二叉树的层次遍历（放入list中）
     * 使用队列api
     *
     * @param root
     * @return
     */
    public ArrayList<Integer> printTree(BinaryTreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();//offer、poll方法返回一个特定的值
            list.add(node.value);

            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
        }
        return list;
    }

    /**
     * 二叉树的层次遍历——广度优先遍历
     * 通过ArrayList模仿队列
     *
     * @param root
     * @return
     */
    public ArrayList<Integer> PrintFromTopToBottom(BinaryTreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<BinaryTreeNode> queue = new ArrayList<>();
        if (root == null) {
            return list;
        }
        queue.add(root);
        while (queue.size() != 0) {
            BinaryTreeNode temp = queue.remove(0);//返回第一个
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
            list.add(temp.value);//添加到末尾
        }
        return list;
    }

    /**
     * 按照行打印二叉树
     *
     * @param root
     */
    public void printTreeByRow(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        //使用两个变量来保存本行没打印的节点、下一行节点数
        int toBePrint = 1;
        int nextLevel = 0;
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();//offer、poll方法返回一个特定的值/poll移除并返问队列头部的元素
            toBePrint--;
            System.out.print(node.value + " ");

            if (node.left != null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
            if (toBePrint == 0) {
                System.out.println();
                toBePrint = nextLevel;
                nextLevel = 0;
            }
        }
    }

    /**
     * 之字形打印二叉树
     *
     * @param root
     */
    public ArrayList<ArrayList<Integer>> printTreeByZRow(BinaryTreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();//结果数组
        if (root == null) {
            return list;
        }
        Stack<BinaryTreeNode> stack1 = new Stack<>();
        Stack<BinaryTreeNode> stack2 = new Stack<>();
        ArrayList<Integer> temp = new ArrayList<>();//内层临时数组
        stack1.push(root);
        int numofLine = 1;

        while (!stack1.empty() || !stack2.empty()) {
            if ((numofLine & 1) == 1) {//当打印奇数层，先保存左子节点到stack2
                while (!stack1.empty()) {
                    BinaryTreeNode node = stack1.pop();
//                    System.out.print(node.value + " ");
                    temp.add(node.value);
                    if (node.left != null)
                        stack2.push(node.left);

                    if (node.right != null)
                        stack2.push(node.right);
                }
                if (!temp.isEmpty()) {
                    list.add(new ArrayList<>(temp));
                    temp.clear();
//                    System.out.println( );
                    numofLine++;
                }
            } else {//当打印偶数层，先保存右子节点到stack1
                while (!stack2.empty()) {
                    BinaryTreeNode node = stack2.pop();
//                    System.out.print(node.value + " ");
                    temp.add(node.value);
                    if (node.right != null) {
                        stack1.push(node.right);
                    }
                    if (node.left != null) {
                        stack1.push(node.left);
                    }
                }
                if (!temp.isEmpty()) {
                    list.add(new ArrayList<>(temp));
                    temp.clear();
//                    System.out.println();
                    numofLine++;
                }
            }
        }
        return list;
    }

    /**
     * 通过ArrayList的双向性，实现z遍历
     *
     *  大家的实现很多都是将每层的数据存进ArrayList中，偶数层时进行reverse操作，
     *  在海量数据时，这样效率太低了。
     *  *
     *  * 下面的实现：不必将每层的数据存进ArrayList中，偶数层时进行reverse操作，按打印顺序存入
     *  * 思路：利用Java中的LinkedList的底层实现是双向链表的特点。
     *  *     1)可用做队列,实现树的层次遍历
     *  *     2)可双向遍历,奇数层时从前向后遍历，偶数层时从后向前遍历
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(BinaryTreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        if (pRoot == null) {
            return ret;
        }
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        queue.addLast(null);//层分隔符
        queue.addLast(pRoot);
        boolean leftToRight = true;

        while (queue.size() != 1) {
            BinaryTreeNode node = queue.removeFirst();
            if (node == null) {//到达层分隔符
                Iterator<BinaryTreeNode> iter = null;
                if (leftToRight) {
                    iter = queue.iterator();//从前往后遍历
                } else {
                    iter = queue.descendingIterator();//从后往前遍历
                }
                leftToRight = !leftToRight;
                while (iter.hasNext()) {
                    BinaryTreeNode temp = iter.next();
                    list.add(temp.value);
                }
                ret.add(new ArrayList<>(list));
                list.clear();
                queue.addLast(null);//添加层分隔符
                continue;//一定要continue
            }
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);
        System.out.println(new PrintBinaryTree().printTreeByZRow(node));
        /*List a=new PrintBinaryTree().Print(node);
        System.out.println(a);*/
    }
}
