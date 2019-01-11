package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/11 11:12
 * @Description:
 */
public class CloneLinkedList {
    RandomListNode clone(RandomListNode pHead) {
        cloneNodes(pHead);
        setRandomNode(pHead);
        return cutList(pHead);
    }

    //第一步，将节点复制到原节点之后
    private void cloneNodes(RandomListNode phead) {
        RandomListNode cur = phead;
        while (cur != null) {
            RandomListNode temp = new RandomListNode();
            temp.label = cur.label;
            temp.next = cur.next;
            temp.random = null;

            cur.next = temp;
            cur = temp.next;
        }
    }

    //第二步，设定clone出的节点的random指向
    private void setRandomNode(RandomListNode phead) {
        RandomListNode cur = phead;
        while (cur != null) {
            RandomListNode temp = cur.next;
            if (cur.random != null)
                temp.random = cur.random.next;
            cur = temp.next;
        }
    }

    //第三步，将长链表拆成两个子链表，偶数位形成的链表为clone链表
    private RandomListNode cutList(RandomListNode phead) {
        RandomListNode listNode = phead;
        RandomListNode cloneNode=null;
        RandomListNode newHead=null;

        //给cloneNode赋第一个值，并将listNode向后移动
        if (listNode != null) {
            cloneNode = listNode.next; //cloneNode的第一个节点为长链的第二个节点
            newHead = cloneNode;
            listNode.next = cloneNode.next;//listNode的下一个指向cloneNode的下一个节点
            listNode = listNode.next;     //将listNode指向新的位置
        }

        //开始循环
        while (listNode != null) {
            cloneNode.next = listNode.next;
            cloneNode = cloneNode.next;
            listNode.next = cloneNode.next;
            listNode = listNode.next;
        }
        return newHead;
    }

    private class RandomListNode {
        int label;
        RandomListNode next;
        RandomListNode random;
    }
}
