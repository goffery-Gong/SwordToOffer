package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Exe_3 {
	/**
	 * 使用栈
	 * @param listNode
	 * @return
	 */
	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		Stack<Integer> stack = new Stack<>();
		ArrayList<Integer> list = new ArrayList<>();

		while (listNode != null) {
			stack.push(listNode.val);
			listNode = listNode.next;
		}

		while (!stack.isEmpty()) {
			list.add(stack.pop());
		}
		return list;
	}
	
	/**
	 * 使用递归调用
	 */
	ArrayList<Integer> list = new ArrayList<>();
	
	public ArrayList<Integer> printListFromTailToHead2(ListNode listNode){
		if(listNode!=null){
			this.printListFromTailToHead2(listNode.next);
			list.add(listNode.val);
		}
		return list;
	}
	
	/**
	 * 使用Collections反转方法
	 * @param listNode
	 * @return
	 */
	public ArrayList<Integer> printListFromTailToHead3(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<Integer>(); 
         
        while(listNode != null){
            list.add(listNode.val);
            listNode = listNode.next;
        }
         
        Collections.reverse(list);//使用Collections的reverse方法，直接将list反转
        return list;
    }
}

class ListNode {
	int val;
	ListNode next = null;

	ListNode(int val) {
		this.val = val;
	}
}