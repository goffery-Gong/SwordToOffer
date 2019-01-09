package part1;

import java.util.Stack;

public class MinInStack {
    Stack<Integer> dataStack=new Stack<>();
    Stack<Integer> minStack=new Stack<>();

    public void push(int node) {
        dataStack.push(node);
        if(minStack.empty() || node<minStack.peek())
            minStack.push(node);
        else
            minStack.push(minStack.peek());
    }

    public void pop() {
        if(!dataStack.empty() && minStack.size()>0){
            dataStack.pop();
            minStack.pop();
        }
    }

    public int min() {
        if(!dataStack.empty() && minStack.size()>0)
            return minStack.peek();
        else return -1;
    }
}
