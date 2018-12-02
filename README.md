#  剑指offer刷题总结
### 1
### 2 
### 3 
### 4 
### 5 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。  
	import java.util.Stack;
	
	public class Solution {
	    Stack<Integer> stack1 = new Stack<Integer>();
	    Stack<Integer> stack2 = new Stack<Integer>();
	    
	    public void push(int node) {
	        stack1.push(node);
	    }
	    
	    public int pop() {
	        if(stack1.empty()&&stack2.empty()){
	            throw new RuntimeException("Queue is empty!");
	        }
	        if(stack2.isEmpty()){//注意这里
	            while(!stack1.isEmpty())
	                stack2.push(stack1.pop());
	        }
	        return stack2.pop();
	    }
	}

### 6 旋转数组的最小数字
    import java.util.ArrayList;
    public class Solution {
       public int minNumberInRotateArray(int[] array) {
            if (array.length == 0)
                return 0;
            if (array.length == 1)
                return array[0];
    
            int i = 0;
            int j = array.length - 1;
            int mid = i;	//ps:特殊期情况，当旋转数组就是数组本身，那么需要返回第一个数，所以要将mid初始化为i。
            while (array[i] >= array[j]) {
                mid = (i + j) / 2;
                
                if (j - i == 1) {
                    mid=j;
                    break;
                }
                
                //如果i,mid,j的元素相同，则使用顺序查找(例如10111)
                if(array[i]==array[j] && array[mid]==array[i])
                    return MinInOrder(array,i,j);
                
                if (array[mid] >= array[i])
                    i = mid;
                else if (array[mid] <= array[j])
                    j = mid;
            }
            return array[mid];
        }
    
        private int MinInOrder(int[] array, int i, int j) {
            int reslut=array[i];
            for(int k=i+1;k<=j;++k){
                if(reslut>array[k])
                    reslut=array[k];
            }
            return reslut;
        }
    }

### 7 斐波那契数列
- 循环方式


    public int Fibonacci(int n) {
        int a = 1, b = 1;
        int sum = 0;
        if (n == 0)
            return 0;
        else if (n == 1 || n == 2)
            return 1;
        else {
            for (int i = 3; i <= n; i++) {
                sum = a + b;
                a = b;
                b = sum;
            }
            return sum;
        }
    }

- 递归方法  
n=4，看看程序怎么跑的：


    Fibonacci(4) = Fibonacci(3) + Fibonacci(2);
                        = Fibonacci(2) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
                        = Fibonacci(1) + Fibonacci(0) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
由于我们的代码并没有记录Fibonacci(1)和Fibonacci(0)的结果，对于程序来说它每次递归都是未知的，因此光是n=4时f(1)就重复计算了3次之多。  
- 动态规划


    class Solution {
        public int Fibonacci(int n) {
            int f = 0, g = 1;
            while(n--) {
                g += f;
                f = g - f;
            }
            return f;
        }
    }; 