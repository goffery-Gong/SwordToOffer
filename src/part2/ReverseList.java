package part2;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 09:41
 * @Description:
 */
public class ReverseList {
    class ListNode {
        int value;
        ListNode next;
    }

    ListNode reverseList(ListNode head){
        if(head==null)
            return null;

        ListNode next; //保存后续节点
        ListNode front=null; //新的头结点
        while(head!=null){
            next=head.next;
            head.next=front;
            front=head;
            head=next;
        }
        return front;
    }
}
