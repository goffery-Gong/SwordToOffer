package part1;

import java.util.Stack;

public class IsPopOrder {
    /**
     * 书上方法
     * @param pushA
     * @param popA
     * @return
     */
    public boolean isPopOrder(int[] pushA, int[] popA) {
        if (pushA == null || popA == null)
            return false;

        boolean isPos = false;
        int j = 0;
        int i;
        Stack<Integer> stack = new Stack<>();

        for (i = 0; i < popA.length; i++) {
            //栈顶元素与popA序列不同，则执行入栈;直到相同
            while (stack.empty() || stack.peek() != popA[i]) {
                if (j >= pushA.length)
                    break;
                stack.push(pushA[j]);
                j++;
            }
            //如果pushA都入栈完毕，栈顶都不同，跳出循环
            if (stack.peek() != popA[i])
                break;

            stack.pop();
        }

        if(i==popA.length && stack.empty())
            isPos=true;

        return isPos;
    }

    /**
     * 论坛方法，better
     * @param pushA
     * @param popA
     * @return
     */
    public boolean isPopOrder2(int[] pushA, int[] popA){
        if (pushA == null || popA == null)
            return false;

        //标识popA的位置
        int j = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);

            ////如果栈不为空，且栈顶元素等于弹出序列
            while (!stack.empty() && stack.peek() == popA[j]) {
                stack.pop();
                j++;
            }
        }
        return stack.empty();
    }
    public boolean IsPopOrder2(int [] pushA,int [] popA) {
        Stack<Integer> stack=new Stack<>();
        int j=0;
        int i;
      for(i=0;i<popA.length;i++){
          while(stack.isEmpty() ||(j<pushA.length && stack.peek()!=popA[i])){
              stack.push(pushA[j]);
              j++;
          }
          if(j>=pushA.length && stack.peek()!=popA[i])
              break;
          stack.pop();
      }

       if(i==popA.length)
           return true;
        return false;
    }

    public static void main(String[] args) {
        int[] a={1,2,3,4,5};
        int[] b={4,5,3,2,1};
        System.out.println(new IsPopOrder().IsPopOrder2(a,b));
    }
}
