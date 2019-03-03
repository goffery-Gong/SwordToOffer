package part1;

public class CircleList {
    /**
     * 获取入口节点
     * @param head
     * @return
     */
    public MyLinkedList.Node entryNodeOfLoop(MyLinkedList.Node head){
        //获取loop的节点个数loopLength.
        //由于相遇点一定在loop内，所以从相遇点前进能够再次回到相遇点。
        MyLinkedList.Node meetingNode=MeetingNode(head);
        if(meetingNode==null) {
            System.out.println("没有环");
            return null;
        }

        MyLinkedList.Node node1=meetingNode;
        int loopLength=1;
        while(node1.next!=meetingNode){
            node1=node1.next;
            loopLength++;
        }

        //两个指针，node1先走loopLength步；
        node1=head;
        for (int i = 0; i <loopLength ; i++)
            node1=node1.next;

        //然后两个指针以相同速度一起走，相遇位置即为入口点（node1事前走了一个环的距离）
        MyLinkedList.Node node2=head;
        while(node1 !=node2){
            node1=node1.next;
            node2=node2.next;
        }
        return node1;
    }

    //判断是否有环，如果有则返回两个指针的相遇节点
    //两个指针，fast如果能够追上slow，说明有环
    private MyLinkedList.Node MeetingNode(MyLinkedList.Node head) {
        if(head==null)
            return null;

        MyLinkedList.Node slow=head;
        MyLinkedList.Node fast=slow.next;

        while(fast!=null && slow!=null){
            if(fast==slow)
                return fast;

            slow=slow.next;

            fast=fast.next;
            if(fast!=null)
                fast=fast.next;
        }
        return null;
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
        new CircleList().entryNodeOfLoop(list.firstNode);
    }
}
