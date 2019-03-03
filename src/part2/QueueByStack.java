package part2;

import java.util.Stack;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 13:16
 * @Description:
 */
public class QueueByStack {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    void push(int node) {
        stack1.push(node);
    }

    int pop() {
        if (stack2.isEmpty())
            while (!stack1.isEmpty())
                stack2.push(stack1.pop());

        return stack2.pop();
    }
}
