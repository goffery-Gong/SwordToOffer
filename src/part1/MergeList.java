package part1;

public class MergeList {
    class Node {
        int value;
        Node next;
    }

    /**
     * 递归调用，合并有序链表
     * @param head1
     * @param head2
     * @return
     */
    public Node merge(Node head1, Node head2){
        if(head1==null)
            return head2;
        else if(head2==null)
            return head1;

        Node mergeHead;

        if(head1.value < head2.value){
            mergeHead=head1;
            mergeHead.next=merge(head1.next, head2);
        }else {
            mergeHead=head2;
            mergeHead.next=merge(head1, head2.next);
        }
        return mergeHead;
    }

}
