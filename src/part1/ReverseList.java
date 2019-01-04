package part1;

import java.util.List;
import java.util.Stack;

public class ReverseList {

    /**
     * 循环，空间复杂度为O(1)
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        if(head==null)
            return null;

        //head为当前节点，如果当前节点为空的话，那就什么也不做，直接返回null；
        ListNode pre = null;
        ListNode next ;
        //当前节点是head，pre为当前节点的前一节点，next为当前节点的下一节点
        //需要pre和next的目的是让当前节点从pre->head->next1->next2变成pre<-head next1->next2
        //即pre让节点可以反转所指方向，但反转之后如果不用next节点保存next1节点的话，此单链表就此断开了
        //所以需要用到pre和next两个节点
        //1->2->3->4->5
        //1<-2<-3 4->5
        while(head!=null){
            //做循环，如果当前节点不为空的话，始终执行此循环，此循环的目的就是让当前节点从指向next到指向pre
            //如此就可以做到反转链表的效果
            //先用next保存head的下一个节点的信息，保证单链表不会因为失去head节点的原next节点而就此断裂
            next = head.next;
            //保存完next，就可以让head从指向next变成指向pre了，代码如下
            head.next = pre;
            //head指向pre后，就继续依次反转下一个节点
            //让pre，head，next依次向后移动一个节点，继续下一次的指针反转
            pre = head;
            head = next;
        }
        //如果head为null的时候，pre就为最后一个节点了，但是链表已经反转完毕，pre就是反转后链表的第一个节点
        //直接输出pre就是我们想要得到的反转后的链表
        return pre;
    }

    /**
     * 通过栈，空间复杂度为O(N)
     * @param head
     * @return
     */
    public ListNode ReverseListByStack(ListNode head) {
        if(head==null){
            return null;
        }
        //创建栈
        Stack<ListNode> stack=new Stack<>();

        ListNode currentPoint=head;
        while(currentPoint!=null && currentPoint.next!=null){
            stack.push(currentPoint);
            currentPoint=currentPoint.next;
        }

        head=currentPoint;
        while(!stack.isEmpty()){
            currentPoint.next=stack.pop();
            currentPoint=currentPoint.next;
        }
        //最后一定要置尾节点的next值为null,不然出现死循环
        currentPoint.next=null;
        return head;
    }

    public void PrintReverseList(ListNode head){
        if(head!=null){
            if(head.next!=null)//这里必须要判断，不然会出现最后一个节点NPE
                PrintReverseList(head.next);
            System.out.println(head.value+" ");
        }
    }

    private static class ListNode {
        int value;
        ListNode next = null;

        ListNode(int value) {
            this.value = value;
        }
    }
}
