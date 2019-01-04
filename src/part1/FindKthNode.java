package part1;

public class FindKthNode {
    class Node {
        int value;
        Node next;
    }

    /**
     * 需要遍历两次链表的方法
     *
     * @param head
     * @param k
     */
    public void findKthNode(MyLinkedList.Node head, int k) {
        MyLinkedList.Node current = head;
        int length = 0;
        int flag = 1; //事先current已经指向了第一个节点，所以flag从1开始

        while (current != null) {
            length++;
            if (current.next == null)
                break;
            current = current.next;
        }

        if (head == null || k > length || k <= 0)
            return;

        current = head;
        while (flag != (length - k + 1)) {
            flag++;
            if (current.next == null)
                break;
            current = current.next;
        }
        System.out.println(current.value);
    }

    /**
     * 只需要遍历一次的解法
     *
     * @param head
     * @param k
     */
    public void findKthNodeBest(MyLinkedList.Node head, int k) {
        if (head == null || k <= 0)
            return;

        MyLinkedList.Node front = head;
        MyLinkedList.Node behind = null;

        for (int i = 0; i < k - 1; i++) {
            if (front.next != null)    //防止空指针
                front = front.next;
            else
                return;
        }

        behind = head;
        while (front.next != null) {
            front = front.next;
            behind = behind.next;
        }
        System.out.println(behind.value);
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(4);
        for (Object i : list) {
            System.out.print(i + " ");
        }
        System.out.println();
        new FindKthNode().findKthNodeBest(list.firstNode, 3);
    }
}
