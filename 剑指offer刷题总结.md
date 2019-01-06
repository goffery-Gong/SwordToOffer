#  剑指offer刷题总结

[TOC]

## 算法题需要注意的

- 代码完整性：功能测试（特殊值——突破思维限制）；边界测试（循环、递归的终止条件）；负面测试（错误的输入）
- 特殊值：数值的负数/0；null；

## 设计模式

### 单例模式

- 关键点：自由序列化，线程安全，保证单例
- [参考1](http://wuchong.me/blog/2014/08/28/how-to-correctly-write-singleton-pattern/ )
- [参考2](https://my.oschina.net/lichhao/blog/107766 )

**饿汉式单例**

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

 **懒汉单例类（同步的）**

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

 **双重检验锁**

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

**静态内部类——延长初始化占位(推荐)**

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

**单元素枚举类（推荐）**

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

## 数字

### 数值的整数次方

- 考虑极限情况：底数为0且指数小于0
- 三种错误处理的方法：1. 函数返回值；2. 错误发生时设置一个全局变量；3. 抛异常
- 使用公式/位运算提高效率

```java
package part1;
/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/24 13:57
 * @Description:
 */
public class Power {
    boolean invaliInput=false;

    /**
     * 时间O(logn)
     * @param base
     * @param exp
     * @return
     */
     double power(double base, int exp){
        //考虑底数为0且指数小于0情况，用invalidInput变量来标记错误输入
        if(base==0 && exp<0) {
            invaliInput=true;
            return 0.0;
        }

        int absExp=exp;
        if(exp<0)
            absExp=-exp;

        double result=powerWithUnsignedExp(base, absExp);
        if(exp<0)
            result=1.0/result;
        return result;
    }

    private double powerWithUnsignedExp(double base, int exp){
        if(exp==0) return 1;
        if(exp==1) return base;

        double result=powerWithUnsignedExp(base, exp>>>1);
        result*=result;
        if((exp & 1)==1)
            result*=base;
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(new Power().power(-2,-3));
    }
}
```

## 数组

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

### 矩阵中路径（回溯法）



### 机器人运动范围（回溯法）

```java
package part1;

/**
 * 矩阵中寻找字符串
 */
public class RobotTraceInMatrix {
    /**
     * @param bound k值
     * @param rows  行
     * @param cols  列
     * @return
     */
    public static int countMoving(int bound, int rows, int cols) {
        if (bound < 0 || rows <= 0 || cols <= 0) return 0;
        boolean[] isVisited = new boolean[rows * cols];


        int size = countNum(bound, rows, cols, 0, 0, isVisited);
        return size;
    }

    /**
     * 计算机器人走过的格子数量
     * @return
     */
    private static int countNum(int bound, int rows, int cols, int i, int j, boolean[] isVisited) {
        int count = 0;

        if (count(bound, rows, cols, i, j, isVisited)) {
            isVisited[i * cols + j] = true;
            count = 1
                    + countNum(bound, rows, cols, i - 1, j, isVisited)
                    + countNum(bound, rows, cols, i + 1, j, isVisited)
                    + countNum(bound, rows, cols, i, j - 1, isVisited)
                    + countNum(bound, rows, cols, i, j + 1, isVisited);
        }
        return count;
    }

    /**
     * 判断格子是否能够达到
     * @return
     */
    private static boolean count(int bound, int rows, int cols, int i, int j, boolean[] isVisited) {
        if (i < rows && i >= 0 && j < cols && j >= 0 && !isVisited[i * cols + j]
                && getDigitSum(i)+getDigitSum(j)<=bound)
            return true;
        return false;
    }

    /**
     * 求一个整数的各位数字之和
     * @param num
     * @return
     */
    private static int getDigitSum(int num) {
        int sum=0;
        while(num>0){
            sum+=num%10;
            num=num/10;
        }
        return sum;
    }


    public static void main(String[] args) {
        System.out.println(countMoving(10, 2, 2));

    }
}
```

### 剪绳子（动态规划/贪心算法）

```java
public class MaxValueAfterCutting {
    /**
     * 动态规划
     * 时间复杂度为 O(n^2)，另外，建立了辅助容器，空间复杂度为 O(n)；
     * @param n
     * @return
     */
    public static int maxValueAferCutting(int n){
        //本身n=3和n=2的情况下，不切是最长的，但是要求必须m>1
        if(n<2)
            return 0;
        if(n==2)
            return 1;
        if(n==3)
            return 2;

        int maxLength=0;
        int[] tempLengths=new int[n+1];
        tempLengths[0]=0;
        tempLengths[1]=1;
        tempLengths[2]=2;
        tempLengths[3]=3;

        for (int i = 4; i <= n; i++) {
            for (int j = 1; j <= i/2; j++) {
                int tempLength=tempLengths[j]*tempLengths[i-j];
                if(maxLength<tempLength)
                    maxLength=tempLength;
                tempLengths[i]=maxLength;
            }
        }
        return maxLength;
    }

    /**
     * 贪心算法 O(1)
     * @param n
     * @return
     */
    public static int maxValueAferCutting2(int n){
        if(n<2)
            return 0;
        if(n==2)
            return 1;
        if(n==3)
            return 2;

        int timesOf3=n/3;
        if(n-timesOf3*3==1)
            timesOf3-=1;
        int timesOf2=(n-timesOf3*3)/2;

        return (int) (pow(3,timesOf3)*pow(2,timesOf2));
    }
    public static void main(String[] args) {
        System.out.println(maxValueAferCutting2(8));
    }
}
```

### 面试题21-调整数组顺序使奇数位于偶数前面

```java
package part1;

public class OrderArray {
    /**
     * 常规方法，交换
     * @param array
     */
    public void orderArray(int[] array) {
        if(array==null || array.length==0)
            return;

        int lo = 0;
        int hi = array.length-1;
        while (true){
            while(lo<hi && (array[lo] & 1)!=0) //找到第一个偶数
                lo++;
            while (lo<hi && (array[hi] & 1)!=1)//找到第一个奇数
                hi--;
            if(lo>=hi) break;
            exch(array,lo,hi);
        }
    }

    /**
     * 函数解耦，提高复用,将条件抽取出来单独作为一个函数进行判断
     * @param array
     */
    public void orderArrayBest(int[] array) {
        if(array==null || array.length==0)
            return;

        int lo = 0;
        int hi = array.length-1;
        while (true){
            while(lo<hi && !isEvent(array[lo]))
                lo++;
            while (lo<hi && isEvent(array[hi]))
                hi--;
            if(lo>=hi) break;
            exch(array,lo,hi);
        }
    }

    private boolean isEvent(int i) {
        return (i & 1)==0;
    }

    private void exch(int[] array, int lo, int hi) {
        int temp=array[lo];
        array[lo]=array[hi];
        array[hi]=temp;
    }

    public static void main(String[] args) {
        int[] a={2,2,3,5,6,7};
        new OrderArray().orderArrayBest(a);
        for (int i :
                a) {
            System.out.print(i+" ");
        }
    }
}
```

### 面试题29-顺时针打印矩阵

- 一圈一圈的打印

```java
package part1.Singleton;

public class PrintMatrix {
    public void printMatrix(int[][] matrix) {
        if (matrix == null)
            return;

        int start = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        while (start * 2 < cols && start * 2 < rows) {//循环结束条件
            printMatrixInCircle(matrix, rows, cols, start);
            start++;
        }
    }

    private void printMatrixInCircle(int[][] matrix, int rows, int cols, int start) {
        int endX = cols - 1 - start;
        int endY = rows - 1 - start;

        //从左向右打印
        for (int i = start; i <= endX; i++)
            System.out.println(matrix[start][i]);
        
        //从上到下打印,至少两行
        if (start < endY)
            for (int i = start + 1; i <= endY; i++)
                System.out.println(matrix[i][endX]);
            
        //从右到左打印，至少两行两列
        if (start < endY && start < endX)
            for (int i = endX - 1; i >= start; i--)
                System.out.println(matrix[endY][i]);
            
        //从下到上答应，至少三行两列
        if (endY - start > 1 && start < endX)
            for (int i = endY - 1; i > start; i--)
                System.out.println(matrix[i][start]);
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4, 5}, {5, 6, 7, 8, 9}, {9, 10, 11, 12, 13}, {13, 14, 15, 16, 17}};
        new PrintMatrix().printMatrix(matrix);
    }
}
```



## 字符串

### 面试题20-表示数值的字符串

```java
package part1;

public class IsNumeric {
    private int i;
    private int inx;

    public boolean isNumric(String num){
        if(num==null)
            return false;
        //判断整数部分
        boolean flag=scanInteger(num);

        //判断小数部分
        if(i<num.length() && (num.charAt(i)=='.')){
            i++;
            flag=flag | isUnsignedInteger(num); //注意运算符短路
        }
        //判断指数部分
        if(i<num.length() && (num.charAt(i)=='e' || num.charAt(i)=='E')){
            i++;
            flag=flag & scanInteger(num);
        }

        return flag && i>=num.length();
    }

    //判断 指数位/整数位为可带符号整数
    private boolean scanInteger(String num) {
        if(i<num.length() && (num.charAt(i)=='+' || num.charAt(i)=='-'))
            i++;
        return isUnsignedInteger(num);
    }

    //判断 小数位为无符号整数
    private boolean isUnsignedInteger(String num) {
        int before=i;
        while(i<num.length() && num.charAt(i)>='0' && num.charAt(i)<='9')
            i++;
        return i>before;
    }


    public static void main(String[] args) {
        System.out.println(new IsNumeric().isNumric("3.-12"));
    }
}
```

## 链表与树

- 链表长度为1的CRUD
- 删除/增加位置为第一个或最后一个
- 

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



### 面试题24-反转链表

- 输入null
- 输入一个节点的链表
- 输入多个节点的链表

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

### 面试题22-输出链表倒数第k个节点

- 总结：类似问题可能为求链表中间节点，如果链表为奇数个，则返回中间节点；如果为偶数个，则返回中间两个任意一个。
- 当一个指针便利链表不能解决问题，可以考虑使用两个或者多个指针。让其中一个走的快一些。

```java
package part1;

public class FindKthNode {
    class Node {
        int value;
        Node next;
    }

    /**
     * 需要遍历两次链表的方法
     *
     * @param head
     * @param k
     */
    public void findKthNode(MyLinkedList.Node head, int k) {
        MyLinkedList.Node current = head;
        int length = 0;
        int flag = 1; //事先current已经指向了第一个节点，所以flag从1开始

        while (current != null) {
            length++;
            if (current.next == null)
                break;
            current = current.next;
        }

        if (head == null || k > length || k <= 0)
            return;

        current = head;
        while (flag != (length - k + 1)) {
            flag++;
            if (current.next == null)
                break;
            current = current.next;
        }
        System.out.println(current.value);
    }

    /**
     * 只需要遍历一次的解法
     *
     * @param head
     * @param k
     */
    public void findKthNodeBest(MyLinkedList.Node head, int k) {
        if (head == null || k <= 0)
            return;

        MyLinkedList.Node front = head;
        MyLinkedList.Node behind = null;

        for (int i = 0; i < k - 1; i++) {
            if (front.next != null)    //防止空指针
                front = front.next;
            else
                return;
        }

        behind = head;
        while (front.next != null) {
            front = front.next;
            behind = behind.next;
        }
        System.out.println(behind.value);
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
        new FindKthNode().findKthNodeBest(list.firstNode, 3);
    }
}

```

### 面试题23-链表中环的入口节点

```java
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
```

### 面试题25-合并有序链表

```java
package part1;

public class MergeList {
    class Node {
        int value;
        Node next;
    }

    /**
     * 递归调用，合并有序链表
     * @param head1
     * @param head2
     * @return
     */
    public Node merge(Node head1, Node head2){
        if(head1==null)
            return head2;
        else if(head2==null)
            return head1;

        Node mergeHead;

        if(head1.value < head2.value){
            mergeHead=head1;
            mergeHead.next=merge(head1.next, head2);
        }else {
            mergeHead=head2;
            mergeHead.next=merge(head1, head2.next);
        }
        return mergeHead;
    }
}
```



### 面试题26-树的子结构

- 特别注意每次指针的指向是否为null
- 测试用例：
  - 树A/B中至少有一个为null
  - 树A/B中所有节点都只有左节点或者右子节点
  - 树A/B节点中含有分叉

```java
package part1;

public class HasSubTree {
    class BinaryTreeNode{
        double value; //注意是double类型
        BinaryTreeNode left;
        BinaryTreeNode right;
    }

    public boolean hasSubTree(BinaryTreeNode root1, BinaryTreeNode root2){
        boolean result=false;

        //递归遍历树1，找到与树2根节点相同的节点。
        if(root1!=null && root2!=null){
            //找到了入口，进行下一步判断
            if(equal(root1.value,root2.value))
                result=doTree1HasTree2(root1, root2);
            if(!result)
                result=hasSubTree(root1.left, root2);
            if(!result)
                result=hasSubTree(root1.right,root2);
        }
        return result;
    }

    private boolean doTree1HasTree2(BinaryTreeNode root1, BinaryTreeNode root2) {
        if(root2==null)
            return true;
        if(root1==null)
            return false;

        if(!equal(root1.value,root2.value))
            return false;

        return doTree1HasTree2(root1.left, root2.left)
                && doTree1HasTree2(root1.right,root2.right);
    }

    //double 类型的数值比较不能直接用==
    private boolean equal(double value, double value1) {
        if((value-value1>-0.000001) && (value-value1<0.000001))
            return true;
        else
            return false;
    }
}
```

### 面试题27- 二叉树镜像

```java
public class MirrorTree {

    public void mirrorTree(BinaryTreeNode treeNode){
        if(treeNode!=null)
            return;

        //叶节点就返回
        if(treeNode.left==null && treeNode.right==null)
            return;

        //交换左右节点
        BinaryTreeNode temp=treeNode.left;
        treeNode.left=treeNode.right;
        treeNode.right=temp;

        //分析左节点/右节点之下
        if(treeNode.left!=null)
            mirrorTree(treeNode.left);
        if(treeNode.right!=null)
            mirrorTree(treeNode.right);
    }
}
```

### 面试题28-对称二叉树

```java
package part1;

public class isSymmetrical {
    public boolean isSymmetrical(BinaryTreeNode root){
        return isSymmetrical(root ,root);
    }

    private boolean isSymmetrical(BinaryTreeNode root1, BinaryTreeNode root2) {
        //递归终止条件
        if(root1 == null && root2==null)
            return true;
        if(root1==null || root2==null)
            return false;

        //根节点不相等就返回false
        if(root1.value!=root2.value)
            return false;

        //根节点相同再判断左右节点(前序遍历，对称前序遍历)
        return isSymmetrical(root1.left,root2.right)
                && isSymmetrical(root1.right,root2.left);
    }
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

```java
Fibonacci(4) = Fibonacci(3) + Fibonacci(2);
                    = Fibonacci(2) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
                    = Fibonacci(1) + Fibonacci(0) + Fibonacci(1) + Fibonacci(1) + Fibonacci(0);
```
由于我们的代码并没有记录Fibonacci(1)和Fibonacci(0)的结果，对于程序来说它每次递归都是未知的，因此光是n=4时f(1)就重复计算了3次之多。    

- 最佳解法

  运用了两个公式：

  $\left[
  \begin{matrix}
  f(n)& f(n-1) \\
  f(n-1) & f(n-2) 
  \end{matrix}
  \right]=\left[
  \begin{matrix}
  1& 1 \\
  1 & 0 
  \end{matrix}
  \right]^{n-1}$所以求得左上角第一个就行

  $ a^n=a^{n/2}*a^{n/2} $ 当n为偶数；

  $ a^n=a^{\frac{n-1}2}*a^{\frac{n-1}2}*a $ 当n为奇数

  ```java
  public static int finbonaciBestMethod(int n){
     if(n<0) return 0;
     if(n==1 || n==2) return 1;
     int[][] base={{1,1},{1,0}};
     int[][] res= matrixCaculate(base,n-1);
     return res[0][0];
  }
  
  //计算矩阵的p次幂，返回结果矩阵
  private static int[][] matrixCaculate(int[][] matrix, int p) {
     if(p==0) return null;
     if(p==1) return matrix;
     int[][] res=matrixCaculate(matrix,p>>1);//时间复杂度为O(logn)的计算p次幂的算法
     res=mutiMatrix(res,res);
     if((p & 1)==1)  //返回1表示奇数，0表示偶数。
        res=mutiMatrix(res,matrix);
     return res;
  }
  
  //计算两个矩阵相乘得到一个新矩阵
  private static int[][] mutiMatrix(int[][] m1, int[][] m2) {
     int [][] res=new int[m1.length][m2[0].length];
     for(int i=0;i<m1.length;i++){     //i应为m1的行
        for(int j=0;j<m2[0].length;j++){//j应为m2的列
           for(int k=0;k<m2.length;k++){
              res[i][j]+=m1[i][k]*m2[k][j];
           }
        }
     }
     return res;
  }
  ```

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



## 位运算

总结

- n & (n-1) ：相当于将n的二进制表示中最右边的1置为0
- (n & 1) ==1成立，则n为奇数

### 表格行数转化（99页）

```java
public class Excel2003 {
    /**
     * 输入字符串，输出行数
     * @param str
     * @return
     */
    public static int FindCol(String str) {
        int sum = 0;
        int exp = 0;
        char[] chars = str.toCharArray();
        for (int i = str.length() - 1; i >= 0; i--) {
            sum += (chars[i] - 'A' + 1) * Math.pow(26, exp);
            exp++;
        }
        return sum;
    }

    /**
     * 输入行数，输出代表的字符串
     * @param num
     * @return
     */
    public static String FindNum(int num){
        int count=Count(num);
        char[] result=new char[count];

        for (int i = count-1; i >= 0; i--) {
            int temp=num%26;
            result[i]=(char)('A'+temp-1);
            num/=26;
        }
        return new String(result); //将char[]转化为string
    }

    /**
     * 统计结果的字符串长度
     * @param num
     * @return
     */
    private static int Count(int num) {
        int count=0;
        while(num!=0){
            num/=26;
            count++;
        }
        return count;
    }
}
```

### 有符号整数二进制表示中1的个数

有一个 32 位有符号整型，给出一个函数可以统计这个整型二进制表示中 1 的位元个数。负数由补码表示。  

比如 32 位有符号整型 5，二进制表示为 00000000000000000000000000000101，值为 1 的位元有 2 个。对于 

-5，二进制表示为 11111111111111111111111111111011，值为 1 的位元有 31 个。

- 参考 [统计 32 位有符号整型二进制表示中 1 的数目](https://zhuanlan.zhihu.com/p/32899882)

```java
public class NumberOf1 {
    /**
     * 当输入为负数，可能出现死循环（>>>可以避免）
     * 通过右移num实现，循环次数等于二进制位数
     *
     * @param num
     * @return
     */
    public static int numberOf1(int num) {
        int sum = 0;
        int flag = 1;

        while (num != 0) {
            //不能将if 的判断条件写成 bits % 2 == 1 来判断当前位是否为 1，因为负数取余为负
            if ((num & 1) == 1) //按位与：num & 1表示num的最后一位与1进行位运算
                sum++;
            num >>>= 1; //这里使用>>>可以避免陷入死循环，如果是>>就不行。
        }
        return sum;
    }

    /**
     * 通过左移flag(1)实现
     * 循环次数等于二进制位数
     * @param num
     * @return
     */
    public static int numberOfOne2(int num) {
        int sum = 0;
        int flag = 1;

        while (flag != 0) {
            //按位与;-5的补码为11111111111111111111111111111101，和flag进行&运算
            if ((num & flag) == flag)
                sum++;
            flag = flag << 1;
        }
        return sum;
    }

    /**
     * 一个整数-1，然后与原整数进行&操作，会把最右边的1变为0.
     * @param args
     */
    public static int numberOfOne3(int num) {
        //num &(num-1)
        int count = 0;
        while (num != 0) {
            count++;
            num = num & (num - 1);
        }
        return count;
    }
}
```



## 其他

### 面试题19-正则表达式匹配（递归）

- 递归的使用
- 注意特殊情况：`str`用完的情况

```java
package part1;

public class MatchModel {

    public boolean match(String str, String model) {
        boolean flag = true;
        if (str == null || model == null) {
            return false;
        }
        return matchModel(str, 0, model, 0);
    }

    /**
     * 匹配:'*'——前面字符出现任意次数；'.'——任意字符
     * 如果模式匹配字符的下一个字符是‘*’:
        * 如果pttern当前字符和str的当前字符匹配，：有以下三种可能情况
             * （1）pttern当前字符能匹配 str 中的 0 个字符：match(str, pattern+2)
             * （2）pttern当前字符能匹配 str 中的 1 个字符：match(str+1, pattern+2)
             * （3）pttern当前字符能匹配 str 中的 多 个字符：match(str+1, pattern)
        * 如果pttern当前字符和和str的当前字符不匹配
            * pttern当前字符能匹配 str 中的 0 个字符：(str, pattern+2)
     * 如果模式匹配字符的下一个字符不是‘*’，进行逐字符匹配。
        * 对于 ‘.’ 的情况比较简单，’.’ 和一个字符匹配 match(str+1, pattern+1)
     *
     * 另外需要注意的是：空字符串"" 和 ".*" 是匹配的
     *
     * @param str
     * @param model
     * @return
     */
    public boolean matchModel(String str, int i, String model, int j) {

        if (i >= str.length() && j >= model.length()) //都结束
            return true;
        if (i < str.length() && j >= model.length()) //str没结束，model结束了
            return false;

        //下一位是'*'
        if (j + 1 < model.length() && model.charAt(j + 1) == '*') {
            //字符串完了
            if (i >= str.length())
                return matchModel(str, i, model, j + 2);

             //当前位相同，则有三种情况是匹配的
            if (str.charAt(i) == model.charAt(j) || model.charAt(j) == '.') {
                return matchModel(str, i, model, j + 2)
                        || matchModel(str, i + 1, model, j + 2)
                        || matchModel(str, i + 1, model, j);
            } else //当前位不相同
                return matchModel(str, i, model, j + 2);
        }
        //下一位不是"*"
        else if(i>=str.length())
            return false;
        else if (str.charAt(i) == model.charAt(j) || model.charAt(j) == '.')
            return matchModel(str, i + 1, model, j + 1);
        return false;
    }

    public static void main(String[] args) {
        boolean flag = new MatchModel().match("", ".*");
        System.out.println(flag);

        char[] chars = "aa".toCharArray();
//        System.out.println(new MatchModel().matchCore(chars, 0, "a*a".toCharArray(), 0));
    }
}
```



