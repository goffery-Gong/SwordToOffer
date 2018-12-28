package part1;

/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/28 10:11
 * @Description:
 */
public class DeleteRepeatedNode extends MyLinkedList {
//    class Node<E> {
//        E value;
//        Node<E> next;
//    }

    public void deleteRepeatedNode(MyLinkedList list) throws Exception {
        if (list==null)
            throw new Exception("list不能为空");

        Node current = list.firstNode;
        int index = 1;

        //循环遍历链表
        while (current.next != null) {
            if (current.value == current.next.value) {
                // 循环删除当前重复的节点
                // 注意当除服数字在链表尾，current.next会为空
                while (current.next!=null && current.value == current.next.value) {
                    current = current.next;
                    list.remove(index);
                }
                //尽量不要让current为null
                if(current.next!=null)
                    current=current.next;
                list.remove(index);
            } else {
                current = current.next;
                index++;
            }
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(2);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(4);
        list.addLast(4);
        for (Object i : list) {
            System.out.print(i + " ");
        }

        try {
            new DeleteRepeatedNode().deleteRepeatedNode(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        for (Object i : list) {
            System.out.print(i + " ");
        }
    }
}
