package part2;

import part1.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 10:14
 * @Description:
 */
public class PrintReverseList {
    class ListNode {
        int value;
        ListNode next;
    }

    //递归调用
    List<Integer> list=new ArrayList<>();
    List printList(ListNode listNode){
        if(listNode==null)
            return list;

        printList(listNode.next);
        list.add(listNode.value);
        return list;
    }

    //基于栈反向输出
}
