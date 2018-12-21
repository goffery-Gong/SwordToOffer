#  剑指offer刷题总结

[TOC]

### 单例模式

- 关键点：自由序列化，线程安全，保证单例
- [参考1](http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/ )
- [参考2](https://my.oschina.net/lichhao/blog/107766 )

#### 饿汉式单例

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

#### 懒汉单例类（同步的）

- 实例第一次被引用时候创建，不随 类的加载而初始化创建(懒加载)
- 线程安全
- 占用内存（每个线程调用getInstance方法都要进行同步，线程同步加锁消耗内存；而实际上只有第一次进行调用时候进行初始化需要同步，之后因为判断为null 是不需要初始化的，也就不需要进行同步了）

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

#### 双重检验锁

- 改进上面的同步方法。

- `instance = new Singleton()`这句，并非是一个原子操作（详见参考1），sychronize关键字只能保证**原子性和可见性**，不能保证**有序性**，所以操作为非原子操作。
- 对于一个 volatile 变量的写操作都先行发生于后面对这个变量的读操作（这里的“后面”是时间上的先后顺序）。

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



#### **静态内部类——延长初始化占位**(推荐)

```java
public class Singleton {  
    private static class SingletonHolder {  
        private static final Singleton INSTANCE = new Singleton();  
    }  
    private Singleton (){}  
    public static final Singleton getInstance() {  
        return SingletonHolder.INSTANCE; 
    }  
}
```

#### 单元素枚举类（推荐）

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



###  数组中重复数字

```java
package part1;

import java.util.Arrays;
import java.util.HashMap;

public class FindDoubleNum {
    //排序后遍历。时间：O(nlogn)【排序】,空间O（1）
    public static int findDoubleNum(int[] a){
        if(a==null){
            return -1;
        }
        Arrays.sort(a);
        for(int i=0;i<a.length-2; i++){
            if(a[i]==a[i+1])
                return a[i];
        }
        return -1;
    }

    //利用哈希表查询时间为O(1)
    //时间：O(n),空间O(n)
    public static int findDoubleNumByHash(int[] a){
        if(a==null)
            return -1;

        HashMap<Integer,Integer> hashMap=new HashMap();
        for (int num : a) {
            if(hashMap.containsKey(num))
                return num;
            hashMap.put(num,0);
        }
        return -1;
    }

    //利用遍历一次数组作交换实现。时间：O(n),空间O(1)
    //两层循环，但是每个数字最多两次交换就能移动到正确位置（因为交换是以a[a[i]]进行的），所以时间复杂度为n
    public static boolean findDoubleBest(int[]a ,int[] result){
        if(a==null)
            return false;

        for(int k: a)
            if(k<0)
                return false;

        for(int i=0; i<a.length; i++){
            while (i!=a[i]) {
                if (a[i] == a[a[i]]) {
                    result[0] = a[i];
                    return true;
                }
                else
                    exchange(a, i, a[i]);
            }
        }
        return false;
    }

    private static void exchange(int[] a, int i, int j) {
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    //时间：O(nlogn),空间O(1)
    //不改变数组，类二分法;不能保证找出所有重复数组
    public static int findDoubleBinary(int[] a){
        if(a==null)
            return -1;

        int lo=1;
        int hi=a.length-1;
        while (lo<=hi){
            int mid=(lo+hi)/2;
            int count=countNum(a,lo,mid);
            if(lo==hi) {
                if(count>1)
                    return lo;
                else    //需要吗
                    break;
            }
            if(count > (mid-lo+1) )
                hi=mid;
            else
                lo=mid+1;
        }
        return -1;
    }
    
    private static int countNum(int[] a, int lo, int hi) {
        int count=0;
        for(int k:a)
            if(k>=lo && k<=hi)
                count++;
        return count;
    }
    
    public static void main(String[] args) {
        int[] a={2,3,1,0,2,5,3};
        int[] b=new int[1];
        //System.out.println(findDoubleNum(a));
        System.out.println(findDoubleNumByHash(a));
        System.out.println(findDoubleBest(a,b));
    }
}

```

总结：

- 考虑算法实现的条件：
  - 功能要求（找出任意重复数字`findDoubleBinary`/找出所有重复数组`其余所有`）
  - 性能要求（时间优先/空间优先）
  - 数组要求：不改变数组：`hash`

## 链表

### 反向输出链表

```java
public void PrintReverseList(ListNode head){
    if(head!=null){
        if(head.next!=null)//这里必须要判断，不然会出现最后一个节点NPE
            PrintReverseList(head.next);
        System.out.println(head.value+" ");
    }
}
```



### 反转链表

```java
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
```



## 栈和队列

- 用链表实现栈/队列（见算法）

###  用两个栈来实现一个队列

```java
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
```

### 两个队列实现一个栈

- **想要使`for each`实现遍历的Iterator没有想好**
- 关键在于两个队列始终保持一个为空

```java
/**
 * 两个队列实现一个栈
 *  add        增加一个元索                 如果队列已满，则抛出一个IIIegaISlabEepeplian异常
　　remove     移除并返回队列头部的元素        如果队列为空，则抛出一个NoSuchElementException异常
　　element    返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
　　offer       添加一个元素并返回true       如果队列已满，则返回false
　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
　　peek       返回队列头部的元素             如果队列为空，则返回null
　　put         添加一个元素                      如果队列满，则阻塞
　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 * @param <E>
 */
public class StackByQueue<E> implements Iterable{
    int N;
    private Queue<E> queue1 = new LinkedList<>();
    private Queue<E> queue2 = new LinkedList<>();

    public void push(E item) {
        if(!queue1.isEmpty())
            queue1.offer(item);
        else
            queue2.offer(item);
        N++;
    }

    public E pop(){
        if(queue1.isEmpty() && queue2.isEmpty())
            try {
                throw new Exception("stack is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(queue1.isEmpty()){
            while(queue2.size()>1)
                queue1.offer(queue2.poll());
            N--;
            return queue2.poll();
        }else{
            while(queue1.size()>1)
                queue2.offer(queue1.poll());
            N--;
            return queue1.poll();
        }
    }

    public static void main(String[] args) {
        StackByQueue queue=new StackByQueue<Integer>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.pop());
        System.out.println(queue.N);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int i=1;
            @Override
            public boolean hasNext() {
                return i<N;
            }

            @Override
            public Object next() {
//                return get(i);
            }
        };
    }
}
```

###  旋转数组的最小数字

```java
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
```

###  斐波那契数列

- 循环方式


```java
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
```

- 递归方法  
  n=4，看看程序怎么跑的：


    Fibonacci(4) = Fibonacci(3) + Fibonacci(2);
                        = Fibonacci(2) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
                        = Fibonacci(1) + Fibonacci(0) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
由于我们的代码并没有记录Fibonacci(1)和Fibonacci(0)的结果，对于程序来说它每次递归都是未知的，因此光是n=4时f(1)就重复计算了3次之多。    
- 动态规划


```java
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
```

###  变态跳台阶

因为n级台阶，第一步有n种跳法：跳1级、跳2级、到跳n级  
跳1级，剩下n-1级，则剩下跳法是f(n-1)  
跳2级，剩下n-2级，则剩下跳法是f(n-2)  
以此类推，到跳n级，剩下n-n=0；剩下跳法f(0)=0  
所以f(n)=f(n-1)+f(n-2)+...+f(1)+f(0)  
因为f(n-1)=f(n-2)+f(n-3)+...+f(1)+f(0)  
所以f(n)=2*f(n-1)递推出，f(n)=2^(n-1)

```java
public int JumpFloorII(int target) {
        if(target<=0) return 0;
        if(target==1) return 1;
        else 
            return 2*JumpFloorII(target-1);
}
```

11111