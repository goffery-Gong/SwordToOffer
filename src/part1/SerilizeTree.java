package part1;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/14 22:07
 * @Description:
 */
public class SerilizeTree {

    List<String> list = new ArrayList<>();
    int index = -1;

    /**
     * 序列化二叉树函数
     * null用%替代
     *
     * @param root
     */
    List<String> Serilize(BinaryTreeNode root) {
        if (root == null) {
            list.add("%");
            return list;
        }
        //前序遍历二叉树
        list.add(String.valueOf(root.value));
        Serilize(root.left);
        Serilize(root.right);

        return list;
    }

    /**
     * 反序列化二叉树
     * @param list
     * @return
     */
    BinaryTreeNode Deserilize(List<String> list) {
        BinaryTreeNode root = new BinaryTreeNode();
        return Deserilize(root, list);
    }

    private BinaryTreeNode Deserilize(BinaryTreeNode root, List<String> list) {
        index++;
        if (index >= list.size())
            return root;
        String num = list.get(index);
        if (num != "%") {
            root = new BinaryTreeNode();
            root.value = Integer.valueOf(num);
            root.left = Deserilize(root.left, list);
            root.right = Deserilize(root.right, list);
        }
        return root;
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);
        List<String> list = new SerilizeTree().Serilize(node);
        System.out.println(list);
        BinaryTreeNode root = new SerilizeTree().Deserilize(list);
        System.out.println(root);
    }
}
