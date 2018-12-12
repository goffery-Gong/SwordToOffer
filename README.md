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

### 8 变态跳台阶
因为n级台阶，第一步有n种跳法：跳1级、跳2级、到跳n级  
跳1级，剩下n-1级，则剩下跳法是f(n-1)  
跳2级，剩下n-2级，则剩下跳法是f(n-2)  
以此类推，到跳n级，剩下n-n=0；剩下跳法f(0)=0  
所以f(n)=f(n-1)+f(n-2)+...+f(1)+f(0)  
因为f(n-1)=f(n-2)+f(n-3)+...+f(1)+f(0)  
所以f(n)=2*f(n-1)递推出，f(n)=2^(n-1)

    public int JumpFloorII(int target) {
            if(target<=0) return 0;
            if(target==1) return 1;
            else 
                return 2*JumpFloorII(target-1);
    }

### 9 单例模式

- 关键点：自由序列化，线程安全，保证单例

- [参考1](http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/ )

- [参考2](https://my.oschina.net/lichhao/blog/107766 )

- 饿汉式单例：
  - 类加载时候静态变量初始化，实例创建（非懒加载）
  - 线程不安全
  - 构造函数私有，类不能被继承
  - 饿汉式的创建方式在一些场景中将无法使用：譬如 Singleton 实例的创建是依赖参数或者配置文件的，在 getInstance() 之前必须调用某个方法设置参数给它，那样这种单例写法就无法使用了。

```java
public class EagerSingleton {
    //通过静态变量初始化类实例
    private static final EagerSingleton instance=new EagerSingleton();

    private EagerSingleton(){}

    //获取唯一实例的静态工厂方法
    public static EagerSingleton getInstance(){
        return instance;
    }
}
```
- 懒汉单例类（同步的）
  - 实例第一次被引用时候创建，不随 类的加载而初始化创建(懒加载)
  - 线程安全
  - 占用内存（线程同步加锁消耗内存）

```java
public class LazySingleton {

    /**
     * 此时静态变量不能声明为final，因为需要在工厂方法中对它进行实例化
     */
    private static LazySingleton instance;

    /**
     * 私有构造子，确保无法在类外实例化该类
     */
    private LazySingleton() {
    }

    /**
     * synchronized关键字解决多个线程的同步问题
     */
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

}
```

- 双重检验锁
  - `instance = new Singleton()`这句，并非是一个原子操作（详见参考1）

```java
//1.0版本，存在instance = new Singleton()非原子操作问题
public static Singleton getSingleton() {
    if (instance == null) {                         //Single Checked
        synchronized (Singleton.class) {
            if (instance == null) {                 //Double Checked
                instance = new Singleton();
            }
        }
    }
    return instance ;
}
```

```java
//2.0版本 使用volatile避免指令排序优化
public class Singleton {
    private volatile static Singleton instance; //声明成 volatile
    private Singleton (){}
    public static Singleton getSingleton() {
        if (instance == null) {                         
            synchronized (Singleton.class) {
                if (instance == null) {       
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```



- **静态内部类——延长初始化占位**(推荐)

```java

```

- 单元素枚举类（推荐）

  [参考1](https://www.jianshu.com/p/c836a7576118)

  [参考2](https://www.cnblogs.com/cielosun/p/6596475.html )

  ```java
  enum SingletonDemo{
      INSTANCE;
      public void otherMethods(){
          System.out.println("Something");
      }
  }
  ```

- 总结
  - 一般情况下直接使用饿汉式就好了，如果明确要求要懒加载（lazy initialization）会倾向于使用静态内部类，如果涉及到**反序列化创建对象**时会试着使用枚举的方式来实现单例。