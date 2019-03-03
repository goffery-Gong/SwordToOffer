#  剑指offer刷题总结

[TOC]

## 算法题需要注意的

- 代码完整性：
  - 功能测试（特殊值——突破思维限制）；
  - 边界测试（循环、递归的终止条件）；
  - 特殊值测试（数值的负数/0；null；）

## 设计模式

### 面试题2-单例模式

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
	
    /**
	 * 私有的默认构造子
	 */
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
    INSTANCE
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

### 面试题44 数字序列中某一位的数字

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/21 09:56
 * @Description:
 */
public class DigitAtIndex {
    int digitAtIndex(int n) {
        int temp = 1;//一个数字的位数
        int num = 0; //数字
        int tempNum;
        int result = 0;

        while (temp < n) {
            tempNum = num;
            while (num != 0) {
                temp++;
                num = num / 10;
            }
            num = tempNum;
            if (temp < n)
                num++;
        }
        //数字位一定在num中
        return num;
    }

    /**
     * 方法2
     * @param n
     * @return
     */
    int digitAtIndex2(int n) {
        if (n < 0)
            return -1;
        int digit = 1;
        while (true) {
            int numbers = countOfInteger(digit);
            if (n < numbers * digit)
                return digitAtIndex(n, digit);
            n -= numbers * digit;
            digit++;
        }
    }

    //digit位的数字数量（如2位数有90个）
    private int countOfInteger(int digit) {
        if (digit == 1)
            return 10;
        return (int) (Math.pow(10, digit) - Math.pow(10, digit - 1));
    }

    //结果
    private int digitAtIndex(int n, int digit) {
        int num = n / digit + beginNum(digit);
        int indexFromRight = digit - n % digit;
        for (int i = 1; i < indexFromRight; i++)
            num /= 10;
        return num % 10;
    }

    private int beginNum(int digit) {
        if (digit == 1)
            return 0;
        return (int) Math.pow(10, digit - 1);
    }

    public static void main(String[] args) {
        DigitAtIndex digitAtIndex = new DigitAtIndex();
        System.out.println(digitAtIndex.countOfInteger(3));
        System.out.println(digitAtIndex.digitAtIndex(1001));
        System.out.println(digitAtIndex.digitAtIndex2(15));
    }
}
```

### 面试题49 丑数

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/17 19:51
 * @Description:
 */
public class IsUgly {
    boolean isUgly(int number) {
        while (number % 2 == 0)
            number /= 2;
        while (number % 3 == 0)
            number /= 3;
        while (number % 5 == 0)
            number /= 5;

        return number == 1;
    }

    //方法1，遍历
    int getUglyNumber1(int index) {
        if (index <= 0)
            return 0;

        int number = 0;
        int uglyFound = 0;
        while (uglyFound < index) {
            number++;
            if (isUgly(number))
                uglyFound++;
        }
        return number;
    }

    //方法2，空间换时间
    int getUglyNumber2(int index) {
        if (index <=0)
            return 0;

        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;
        int t2 = 0;
        int t3 = 0;
        int t5 = 0;

        int nextIndex = 1;

        while (nextIndex < index) {
            uglyNumbers[nextIndex] = Min(uglyNumbers[t2] * 2, uglyNumbers[t3] * 3, uglyNumbers[t5] * 5);
            while (uglyNumbers[t2] * 2 <= uglyNumbers[nextIndex])
                t2++;
            while (uglyNumbers[t3] * 3 <= uglyNumbers[nextIndex])
                t3++;
            while (uglyNumbers[t5] * 5 <= uglyNumbers[nextIndex])
                t5++;
            nextIndex++;
        }
        return uglyNumbers[nextIndex-1];
    }

    private int Min(int num1, int num2, int num3) {
        int min=(num1<num2)?num1:num2;
        return (min<num3)?min:num3;
    }

    public static void main(String[] args) {
        System.out.println( new IsUgly().getUglyNumber2(10));
    }
}
```



## 数组

- 数组注意输入：判断null与数组长度为0，a.length==0

### 面试题4 二维数组中的查找

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/19 10:34
 * @Description:
 */
public class FindInMatrix {
    static boolean findInArray(int[][] array, int target) {
        if (array == null || array.length == 0)
            return false;

        int row = 0;
        int col = array[0].length - 1;
        while (row < array.length && col >= 0) {
            if (array[row][col] == target)
                return true;
            else if (target < array[row][col])
                col--;
            else
                row++;
        }
        return false;
    }
}
```

###  面试题3 - 数组中重复数字

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

```java
package part1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 矩阵中寻找字符串
 */
public class FindStringInMatrix {
    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null) return false;

        boolean[] isVisited = new boolean[rows * cols];
        //寻找第一个符合条件的字符作为入口
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ( hasPathElement(matrix, rows, cols, str, i, j, 0, isVisited))
                    return true;
            }
        }
        return false;
    }

    private static boolean hasPathElement(char[] matrix, int rows, int cols, char[] str,
                                          int i, int j, int strIndex, boolean[] isVisited) {
        int index = i * cols + j; //二维数组元素下标，转化为一维数组的下标
        if (i < 0 || i >=rows || j < 0 || j >= cols || matrix[index] != str[strIndex] || isVisited[index])
            return false;
        if(strIndex==str.length-1)
            return true;

        isVisited[index]=true;//先将访问的矩阵元素状态置为“true”
        if(hasPathElement(matrix, rows, cols, str, i-1, j, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i+1, j, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i, j-1, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i, j+1, strIndex+1, isVisited))
            return true;

        //如果没找到合适的，则将此时访问的矩阵元素状态恢复为“false”
        isVisited[index]=false;
        return false;
    }

    public static void main(String[] args) {
        char[] chars="ABCB".toCharArray();
        char[] matrix="ABCESFCSADEE".toCharArray();
        System.out.println(hasPath(matrix,3,4,chars));

    }
}
```

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
            maxLength=0;
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

### 面试题46- 把数字翻译成字符串（动态规划）

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 10:00
 * @Description: https://www.jianshu.com/p/80e1841909b7
 */
public class GetTranslationCount {
    static int getTranslationCount(int number) {
        if (number < 0)
            return 0;
        if (number == 1)
            return 1;

        return getTanslationCount(Integer.toString(number));
    }

    private static int getTanslationCount(String number) {
        int f1 = 0;
        int f2 = 1;
        int g;
        int temp;
        //动态规划，从右到左计算。
        //  f(r-2) = f(r-1)+g(r-2,r-1)*f(r);
        //  如果r-2，r-1能够翻译成字符，则g(r-2,r-1)=1，否则为0
        for (int i = number.length() - 2; i >= 0; i--) {
            int num = Integer.parseInt(number.charAt(i) + "" + number.charAt(i + 1));
            if (num < 26 && num >= 10)
                g = 1;
            else
                g = 0;

            temp = f2;
            f2 = f2 + g * f1;
            f1 = temp;
        }
        return f2;
    }

    public static void main(String[] args) {
        System.out.println(getTranslationCount(-10));  //0
        System.out.println(getTranslationCount(1234));  //3
        System.out.println(getTranslationCount(12258)); //5
    }
}
```

![offer9](http://blog.xbblfz.site/img/offer9.png)

### 面试题47-礼物的最大价值（动态规划）

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 19:30
 * @Description:
 */
public class GetMaxValue {
    /**
     * 常规递归方法
     * @param values
     * @param rows
     * @param cols
     * @return
     */
    int getMaxValue(int[] values, int rows, int cols) {
        if (values == null || rows<=0 ||cols<=0)
            return 0;
        return getMaxValue(values, rows, cols, 0, 0,0);
    }

    private int getMaxValue(int[] values, int rows, int cols, int i, int j,int count) {
        int index = i * cols + j;
        if (i < 0 || i >= rows || j < 0 || j >= cols )
            return count;

        //f(i,j)=max{f(i+1,j),f(i,j+1)}+gift(i,j)
        int value1=getMaxValue(values, rows, cols, i + 1, j, count);
        int value2=getMaxValue(values, rows, cols, i, j + 1, count);
        count = values[index] + Math.max(value1,value2);

        return count;
    }

    /**
     * 动态规划1
     f(i,j)=max{f(i-1,j),f(i,j-1)}+gift(i,j)
     * @param values
     * @param rows
     * @param cols
     * @return
     */
    int getMaxValueDG(int[] values,int rows, int cols){
        if (values == null || rows<=0 ||cols<=0)
            return 0;

        int[][] maxValues=new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left=0;
                int up=0;
                if(i>0)
                    left=maxValues[i-1][j];
                if(j>0)
                    up=maxValues[i][j-1];

                //maxValues[i][j]元素表示到达坐标为（i,j）的格子时，value最大值
                maxValues[i][j]=Math.max(left,up)+values[i*cols+j];
            }
        }
        return maxValues[rows-1][cols-1];
    }

    /**
     * 优化
     * 直接使用一维数组保存，长度为列数n。当计算dp[i][j]时，
     * 数组前j个数字分别是dp[i][0], dp[i][1],...,dp[i][j-1],
     * 数组从下标为j的数字开始到最后一个数字分别是dp[i-1][j],
     * dp[i-1][j+1],...,dp[i-1][n-1]。
     */
    int getMaxValueDG2(int[] values,int rows, int cols){
        if (values == null || rows<=0 ||cols<=0)
            return 0;

        int[] maxValues=new int[cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left=0;
                int up=0;
                if(i>0)
                    left=maxValues[j];
                if(j>0)
                    up=maxValues[j-1];

                //maxValues[i][j]元素表示到达坐标为（i,j）的格子时，value最大值
                maxValues[j]=Math.max(left,up)+values[i*cols+j];
            }
        }
        return maxValues[cols-1];
    }
    public static void main(String[] args) {
        int[] values = {1, 10, 3, 8, 12, 2, 9, 6, 5, 7, 4, 11, 3, 7, 16, 5};
        System.out.println(new GetMaxValue().getMaxValueDG(values, 4, 4));
    }
}

```

### 面试题48 最长不含重复字符的子字符串

$$
f(i):第i个字符为结尾的不包含重复字符的字符串最长长度\\
\begin{cases}  

             第i个字符之前没出现过:f(i)=f(i-1)+1 ，往后添加1 \\
             第i个字符之前出现过：
             \begin{cases}
             	d(第i个字符和之前出现的位置的距离)<=f(i-1)：f(i)=d \\
             	d(第i个字符和之前出现的位置的距离)>f(i-1): f(i)=f(i-1)+1 
             \end{cases}
            
\end{cases}
$$

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 22:06
 * @Description:
 */
public class LongestSubstringWithOutDup {
    //
    int longestSubstringWithOutDup(String str) {
        if (str == null)
            return 0;

        int tempLength = 0;
        int maxLength = 0;
        int[] position = new int[26];
        for (int i = 0; i < position.length; i++)
            position[i] = -1;

        for (int i = 0; i < str.length(); i++) {
            int preAppearIndext = position[str.charAt(i) - 'a'];
            int d = i - preAppearIndext;
            if (preAppearIndext < 0 || d > tempLength)//情况1,3
                tempLength++;
            else
                tempLength=d;  //情况2

            if(tempLength>maxLength)
                maxLength=tempLength;
            position[str.charAt(i) - 'a']=i; //记录字符出现位置为i
        }
//        if(tempLength>maxLength)
//            maxLength=tempLength; ????这里为何要做判断
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubstringWithOutDup().longestSubstringWithOutDup("arabcacf"));
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

### 面试题39 数组中出现此处超过一半的数字

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 20:58
 * @Description:
 */
public class MoreThanHalf {
    /**
     * 通过特点：数量超过一半的数字，一定在数组的中间位置
     * O(n)
     * @param array
     * @return
     */
    int moreThanHalf(int[] array) {
        if (array == null || array.length == 0)
            return 0;

        int mid = array.length >> 1;
        int index = Partition(array, 0, array.length - 1);
        int lo = 0;
        int hi = array.length - 1;
        while (index != mid) {
            if (index < mid) {
                lo = index + 1;
                index = Partition(array, lo, hi);
            } else {
                hi = index - 1;
                index = Partition(array, lo, hi);
            }
        }
        //判断该数字的数量是否大于数组长度的一半
        int result=array[index];
        if (isMoreThanHalf(array, result))
            return 0;
        return result;
    }

    /**
     * 特点：最后一次吧次数times设置为1的数字就是返回值
     * o(n)
     *
     * @param array
     * @return
     */
    int moreThanHalf2(int[] array){
        if (array == null || array.length == 0)
            return 0;

        int result=array[0];
        int times=0;
        for (int anArray : array) {
            if (times == 0) {
                result = anArray;
                times = 1;
            } else if (anArray == result)
                times++;
            else
                times--;
        }
        //判断该数字的数量是否大于数组长度的一半
        if (isMoreThanHalf(array, result))
            return 0;
        return result;
    }
    private boolean isMoreThanHalf(int[] array, int result) {
        int times = 0;
        for (int anArray : array)
            if (anArray == result)
                times++;
        return times * 2 < array.length;
    }

    private int Partition(int[] array, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (array[++i] < array[lo])
                if (i == hi)
                    break;
            while (array[lo] < array[--j])
                if (j == lo)
                    break;
            if (i >= j)
                break;
            exch(array, i, j);
        }
        exch(array, lo, j);
        return j;
    }

    private void exch(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 3};
        System.out.println(new MoreThanHalf().moreThanHalf2(a));
    }
}
```

### 面试题40 最小k个数

- 研究一下书中的partition函数（`Utils.partition()`）
- 最大堆 {o(1)时间获取到k个数字中的最大值，o(nlogk)时间完成插入，删除}；

```java
package part1;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 22:11
 * @Description:
 */
public class LessKNum {
    /**
     * 基于快排切分点，O(n)
     *
     * @param input
     * @param k
     * @return
     * @throws Exception
     */
    ArrayList<Integer> lesskNum(int[] input, int k) throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length)
            return list;

        int lo = 0;
        int hi = input.length - 1;
        int index = Utils.Partition(input, lo, hi);
        while (index != k - 1) {
            if (index < k - 1) {
                lo = index + 1;
                index = Utils.Partition(input, lo, hi);
            } else {
                hi = index - 1;
                index = Utils.Partition(input, lo, hi);
            }
        }
        for (int i = 0; i < k; i++)
            list.add(input[i]);
        return list;
    }

    /**
     * 最大堆；O(nlogk) {o(1)时间获取到k个数字中的最大值，o(logk)时间完成插入，删除}；适合处理海量数据
     * 使用优先队列；最大堆保存这k个数，每次只和堆顶比，如果比堆顶小，删除堆顶，新数入堆。
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution1(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int length = input.length;
        if (k > length || k == 0) {
            return result;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Comparator.reverseOrder());//倒序
        for (int i = 0; i < length; i++) {
            if (maxHeap.size() != k) {
                maxHeap.offer(input[i]);
            } else if (maxHeap.peek() > input[i]) {
                Integer temp = maxHeap.poll();
                temp = null;
                maxHeap.offer(input[i]);
            }
        }
        result.addAll(maxHeap);
        return result;
    }

    /**
     * 最大堆，自己构建堆树
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution2(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        //检查输入的特殊情况
        if (input == null || input.length <= 0 || input.length < k) {
            return list;
        }
        //构建最大堆
        for (int len = k / 2 - 1; len >= 0; len--) {
            adjustMaxHeapSort(input, len, k - 1);
        }
        //从第k个元素开始分别与最大堆的最大值做比较，如果比最大值小，则替换并调整堆。
        //最终堆里的就是最小的K个数。
        int tmp;
        for (int i = k; i < input.length; i++) {
            if (input[i] < input[0]) {
                tmp = input[0];
                input[0] = input[i];
                input[i] = tmp;
                adjustMaxHeapSort(input, 0, k - 1);
            }
        }
        for (int j = 0; j < k; j++) {
            list.add(input[j]);
        }
        return list;
    }

    public void adjustMaxHeapSort(int[] input, int pos, int length) {
        int temp;
        int child;
        for (temp = input[pos]; 2 * pos + 1 <= length; pos = child) {
            child = 2 * pos + 1;
            if (child < length && input[child] < input[child + 1]) {
                child++;
            }
            if (input[child] > temp) {
                input[pos] = input[child];
            } else {
                break;
            }
        }
        input[pos] = temp;
    }

    /**
     * 冒泡排序的思想
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        if (k > input.length) {
            return al;
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if (input[j] < input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
            al.add(input[input.length - i - 1]);
        }
        return al;
    }

    public static void main(String[] args) {
        int[] a = {4, 5, 1, 6, 2, 7, 3, 8};
        List list = null;
        try {
            list = new LessKNum().lesskNum(a, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);

    }
}
```

### 面试题42 最大子序列和问题

动态规划：
$$
f(i)=\begin{cases}  
\left\{  
             pData[i] \\
             f(i-1)+ pData[i] 
\right.  
\end{cases}
$$


```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 16:37
 * @Description:
 */
public class MaxSubArray {
    /**
     * o(n^2)
     *
     * @param array
     * @return
     */
    int maxSubArray(int[] array) {
        if (array == null && array.length == 0)
            return 0;
        int tempSum;
        int maxSum = 0;

        for (int i = 0; i < array.length; i++) {
            tempSum = 0;
            for (int j = i; j < array.length; j++) {
                tempSum += array[j];
                if (tempSum > maxSum)
                    maxSum = tempSum;
            }
        }
        return maxSum;
    }

    int maxSubArray_dp(int[] array) {
        if (array == null && array.length == 0)
            return 0;

        int tempSum = array[0];
        int maxSum = array[0]; //防止为负

        for (int i = 1; i < array.length; i++) {
            tempSum = (tempSum < 0) ? array[i] : tempSum + array[i];
            maxSum = (tempSum > maxSum) ? tempSum : maxSum;
        }
        /*int tempSum = 0;
        int maxSum = 0x80000000; //32位数最小值，负2的32次方

        for (int i = 0; i < array.length; i++) {
            tempSum = (tempSum < 0) ? array[i] : tempSum + array[i];
            maxSum = (tempSum > maxSum) ? tempSum : maxSum;
        }*/
        return maxSum;
    }
}
```

### 面试题45-把数组排成最小的数

```java
package part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/21 15:56
 * @Description:
 */
public class PrintMinNumber {
    List<String> list = new ArrayList<>();

    /**
     * 常规方法，全排列然后找最小
     *
     * @param numbers
     * @return
     */
    String printMinNumber(int[] numbers) {
        if (Utils.isNullorZero(numbers))
            return null;
        allNum(numbers, 0);
        return leastInList(list);
    }

    //找出全排列list中的最小数字
    private String leastInList(List<String> list) {
        long leastNum = Long.valueOf(list.get(0));//可能会超出大小
        long num;
        for (String str : list) {
            num = Long.valueOf(str);
            if (num < leastNum)
                leastNum = num;
        }
        return String.valueOf(leastNum);
    }

    //数组中元素的全排列
    private void allNum(int[] array, int i) {
        if (i >= array.length)
            return;

        for (int j = i; j < array.length; j++) {
            if (!list.contains(toString(array)))
                list.add(toString(array));
            int[] newArray = exchAndBuild(array, i, j);
            allNum(newArray, i + 1);
        }
    }

    private String toString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }

    private int[] exchAndBuild(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return Arrays.copyOf(array, array.length);
    }


    /**
     * 自定义排序大小
     * 定义一个比较大小的函数，比较两个字符串s1, s2大小的时候，先将它们拼接起来，比较s1+s2,和s2+s1那个大，
     * 如果s1+s2大，那说明s2应该放前面，所以按这个规则，s2就应该排在s1前面
     * 比如 "3" < "31"但是 "331" > "313"，所以要将二者拼接起来进行比较
     * @param
     */
    String printMinNumber2(int[] numbers) {
        String str = "";
        if (Utils.isNullorZero(numbers)) {
            return str;
        }

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                long a = Long.valueOf(numbers[i] + "" + numbers[j]);
                long b = Long.valueOf(numbers[j] + "" + numbers[i]);
                if (a > b) {
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        for (int j = 0; j < numbers.length; j++)
            str += String.valueOf(numbers[j]);

        return str;
    }

    /**
     * 自定义排序2，通过使用string来避免大数问题
     * @param numbers
     * @return
     */
    String printMinNumber3(int[] numbers){
        StringBuilder sb=new StringBuilder();
         if (Utils.isNullorZero(numbers)) {
            return sb.toString();
        }

        int length=numbers.length;
        String[] strs=new String[length];
        //转为字符串数组
        for (int i = 0; i < length; i++)
            strs[i]=String.valueOf(numbers[i]);

        //将strs数组按照定义的comparator从小到大排序
        Arrays.sort(strs,(String s1,String s2)->{
            String c1=s1+s2;
            String c2=s2+s1;
            return c1.compareTo(c2);
        });

        for (int i = 0; i < length; i++)
            sb.append(strs[i]);

        return sb.toString();
    }

    public static void main(String[] args) {
        int[] a = {3334, 3, 3333332};
        int[] b = {3, 2, 1};
        String list = new PrintMinNumber().printMinNumber3(b);
        System.out.println(list);
//        System.out.println(Integer.valueOf(b[0]+""+b[1]));
    }
}
```

### 面试题51数组中的逆序对

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/1 15:00
 * @Description:
 */
public class InversePairs {
    /**
     * 数组中的逆序对
     * @param array
     * @return
     */
    public int inversePairs(int[] array) {
        if (array == null)
            return 0;

        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i]=array[i];
        }
        return inversePairsCore(array, copy, 0, array.length - 1);

    }

    /**
     *
     * @param array
     * @param copy 辅助数组
     * @param lo
     * @param hi
     * @return 测试用例输出结果比较大，对每次返回的count mod(1000000007)求余
     */
    private int inversePairsCore(int[] array, int[] copy, int lo, int hi) {
        if (lo == hi)
            return 0;

        int mid = (lo + hi) >>> 1;
        int leftCount=inversePairsCore(copy,array,lo,mid)%1000000007;
        int rightCount=inversePairsCore(copy,array,mid+1,hi)%1000000007;

        int i = mid;
        int j = hi;
        int copyIndex = hi; //辅助数组的index
        int count = 0;
        while (i >= lo && j >= mid+1) {
            if (array[i] > array[j]) {
                count += j - mid;
                copy[copyIndex--] = array[i--];
                //防止count太大而越界
                if(count>=1000000007)
                    count%=1000000007;
            } else
                copy[copyIndex--] = array[j--];
        }

        //上面循环是while(i >= low && j > mid) {} 当有一个指针不满足条件时剩余的数字直接进行拷贝到拷贝数组当中，
        // 因此用了两个for循环 如果满足条件就直接把剩余比它小的数直接进行拷贝
        for (; i >= lo; i--)
            copy[copyIndex--] = array[i];
        for (; j >= mid+1; j--)
            copy[copyIndex--] = array[j];

        return (leftCount+rightCount+count)%1000000007;
    }

    public static void main(String[] args) {
        int[] a={7,5,6,4};
        System.out.println(new InversePairs().inversePairs(a));
    }
}
```

### 面试题53 在排序数组中查找数字

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/3 20:17
 * @Description:
 */
public class FindNumInSortedArray {
    public int GetNumberOfK(int [] array , int k) {
       //通过找到第一次k出现的位置和最后一次出现的位置，得到出现的次数
        if(array==null || array.length==0)
            return 0;
        int first=GetFirst(array, k,0,array.length-1);
        int end=GetLast(array, k,0,array.length-1);
        if(first>-1 && end>-1)
            return end-first+1;
        return 0;
    }

    private int GetFirst(int[] array, int k, int lo, int hi){
        if(lo>hi)   //这里为小于号，保证在等号的时候不会返回-1，而是继续执行逻辑
            return -1;
        int mid=(lo+hi)>>1;
        if(array[mid]>k)
            return GetFirst(array, k, lo, mid-1);
        else if(array[mid]<k)
            return GetFirst(array, k, mid+1, hi);
        else if(mid-1>=0 && array[mid-1]==k)
            return GetFirst(array, k, lo,mid-1);
        else
            return mid;
    }

    private int GetLast(int[] array, int k, int lo, int hi){
        int mid;
        while(lo<=hi){
            mid=(lo+hi)>>1;
            if(array[mid]>k)
                hi=mid-1;
            else if(array[mid]<k)
                lo=mid+1;
            else if(mid+1<array.length && array[mid+1]==k)
                lo=mid+1;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a={1,2,3,3,3,3};
        System.out.println(new FindNumInSortedArray().GetNumberOfK(a,3));
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

### 面试题38-字符串的排列

```java
package part1;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 10:56
 * @Description:
 */
public class SortString {
    List<String> list = new ArrayList<>();

    /**
     * 递归思路
     *
     * @param str
     * @return
     */
    List<String> sortString(String str) {
        if (str == null || str.length() == 0)
            return list;
        sortString(str, 0);
        Collections.sort(list);
        return list;
    }

    private void sortString(String str, int i) {
        if (i >= str.length())
            return;

        for (int j = i; j < str.length(); j++) {
            if (!list.contains(str))
                list.add(str);

            //交换，构造新的串
            String newStr = exchAndBuild(str, i, j);

            //新的串进行递归
            sortString(newStr, i + 1);
        }
    }

    private String exchAndBuild(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
        return new String(chars);
    }
   /**
     * 回溯法
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        List<String> resultList = new ArrayList<>();
        if (str.length() == 0)
            return (ArrayList) resultList;
        //递归的初始值为（str数组，空的list，初始下标0）
        fun(str.toCharArray(), resultList, 0);
        Collections.sort(resultList);
        return (ArrayList) resultList;
    }

    private void fun(char[] ch, List<String> list, int i) {
        //这是递归的终止条件，就是i下标已经移到char数组的末尾的时候，考虑添加这一组字符串进入结果集中
        if (i == ch.length - 1) {
            //判断一下是否重复
            if (!list.contains(new String(ch))) {
                list.add(new String(ch));
                return;
            }
        } else {
            for (int j = i; j < ch.length; j++) {
                swap(ch, i, j);
                fun(ch, list, i + 1);
                swap(ch, i, j);
            }
        }
    }

    //交换数组的两个下标的元素
    private void swap(char[] str, int i, int j) {
        if (i != j) {
            char t = str[i];
            str[i] = str[j];
            str[j] = t;
        }
    }
    public static void main(String[] args) {
        System.out.println(new SortString().sortString("abc"));
//        System.out.println(new SortString().Permutation("abc"));
    }
}
```

### 面试题38-拓展 

```java
package part1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 16:32
 * @Description:
 */
public class CharConbine {
    private Set<String> resultSet;
    private int index;
    private StringBuilder sb=new StringBuilder();

    List<String> combine(char[] chars) {
        if (chars == null || chars.length == 0)
            return new ArrayList<>();

        resultSet = new TreeSet<>();
        for (int i = 1; i <= chars.length; i++)
            combine(chars, i);

        return new ArrayList<>(resultSet);
    }

    private void combine(char[] chars, int length) {
         if (length == 0) {
            resultSet.add(sb.toString());
            return;
        }
        if(chars.length-index<length)
            return;

        //选择第一个，从剩下chars中选择length-1个
        sb.append(chars[index]);
        index++;
        combine(chars,length-1);
        sb.deleteCharAt(sb.length()-1);

        //不选择第一个，从剩下chars中length个
        combine(chars,length);
        --index;
    }

    public static void main(String[] args) {
        System.out.println(new CharConbine().combine("abc".toCharArray()));
    }
}
```

### 面试题50第一个只出现一次的字符

```java
package part1;

import java.util.HashMap;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/17 21:22
 * @Description:
 */
public class FirstChar {
    //时间效率：O(n),空间效率O(1)
    char firstChar(String str) {
        if (str == null)
            return '\0';
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!map.containsKey(chr))
                map.put(chr, 1);
            else {
                int value = map.get(chr);
                map.put(chr, value + 1);
            }
        }

        for (int i = 0; i < str.length(); i++) {
            if(map.get(str.charAt(i))==1)
                return str.charAt(i);
        }
        return '\0';
    }

    //去掉重复的字符
    String isMoreThanOne(String str){
        HashMap<Character, Boolean> map=new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!map.containsKey(chr))
                map.put(chr, false);
            else {
                map.put(chr, true);
            }
        }

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if(!map.get(str.charAt(i)))
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new FirstChar().firstChar("nanfsfds"));
        System.out.println(new FirstChar().isMoreThanOne("googlee"));
    }
}
```

### 拓展：字符流中第一个只出现一次的字符

```java
public class Solution {
    //Insert one char from stringstream
    private int index;
    private int[] map=new int[256];//将chr字符的ascll码作为key，下标值index作为value
    
    public Solution(){
        for(int i=0; i<256; i++)
            map[i]=-1;
    }
    
    public void Insert(char ch)
    {
        if(map[ch]==-1)
            map[ch]=index;
        else if(map[ch]>=0)
            map[ch]=-2;
        index++;
    }
  //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        int min=Integer.MAX_VALUE;
        char c='#';
        for(int i=0; i<256; i++){
            if(map[i]>=0 && map[i]<min){
                c=(char)i;
                min=map[i];
            }
        }
         return c;
    }
}
```



## 链表与树

- 链表长度为1的CRUD
- 删除/增加位置为第一个或最后一个
- 

### 面试题6 反向输出链表

```java
public void PrintReverseList(ListNode head){
    if(head!=null){
        if(head.next!=null)//这里必须要判断，不然会出现最后一个节点NPE
            PrintReverseList(head.next);
        System.out.println(head.value+" ");
    }
}
```

### 面试题18 删除重复链表节点

```java
package part1;

/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/28 10:11
 * @Description:
 */
public class DeleteRepeatedNode extends MyLinkedList {
//    class Node<E> {
//        E value;
//        Node<E> next;
//    }

    public void deleteRepeatedNode(MyLinkedList list) throws Exception {
        if (list==null)
            throw new Exception("list不能为空");

        Node current = list.firstNode;
        int index = 1;

        //循环遍历链表
        while (current.next != null) {
            if (current.value == current.next.value) {
                // 循环删除当前重复的节点
                // 注意当除服数字在链表尾，current.next会为空
                while (current.next!=null && current.value == current.next.value) {
                    current = current.next;
                    list.remove(index);
                }
                //尽量不要让current为null
                if(current.next!=null)
                    current=current.next;
                list.remove(index);
            } else {
                current = current.next;
                index++;
            }
        }
    }
}
```

### 面试题7 重建二叉树

```java
package part1;

import java.util.Arrays;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/15 09:58
 * @Description:
 */
public class RebuildTree {
    /**
     * 重建二叉树
     * @param preOrder
     * @param midOrder
     * @return
     */
    BinaryTreeNode reBuildTree(int[] preOrder, int[] midOrder) {
        if (preOrder == null || midOrder == null)
            return null;
        return reBuildTree(preOrder, 0, preOrder.length - 1, midOrder, 0, midOrder.length - 1);
    }

    private BinaryTreeNode reBuildTree(int[] preOrder, int preStart, int preEnd, int[] midOrder, int midStart, int midEnd) {
        if (preStart > preEnd || midStart > midEnd)
            return null;
        //前序遍历的第一个为根节点，构建根节点
        BinaryTreeNode root = new BinaryTreeNode(preOrder[preStart]);

        //对应到中序遍历，划分左右分支
        int border = midStart;
        while (border <= midEnd && midOrder[border] != preOrder[preStart])
            border++;

        int leftLength = border - midStart;
        int leftPreEnd = preStart + leftLength;//前序遍历序列左分支长度
        root.left = reBuildTree(preOrder, preStart + 1, leftPreEnd, midOrder, midStart, border - 1);
        root.right = reBuildTree(preOrder, leftPreEnd + 1, preEnd, midOrder, border + 1, midEnd);
        return root;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] b = {4, 7, 2, 1, 5, 3, 8, 6};
        BinaryTreeNode root = new RebuildTree().reBuildTree(a, b);
        System.out.println(root);
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

    //demo2
    public ListNode FindListNode(ListNode head,int k){
        if(head==null || k <= 0)
            return null;

        ListNode p1=head;
        ListNode p2=head;
        for (int i = 0; i < k-1; i++) 
            if(p2.next!=null)
                p2=p2.next;
            else
                return null;
        
        while (p2.next!=null) {
            p2=p2.next;
            p1=p1.next;
        }
        return p1;
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

        MyLinkedList.Node slow=head.next;
        if(slow==null)
            return null;
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

### 面试题32-层次遍历树

- 层次遍历树相当于广度优先遍历二叉树
- 广度优先遍历有向图或者是树，都用到队列。首先把起始节点放入队列，然后每次从队列头部取出一个节点，遍历其子节点，并依次放入队列。重复这个遍历过程，直到队列中的全部节点被遍历。

```java
import java.util.*;

public class PrintBinaryTree {
    /**
     * 题目1-二叉树的层次遍历（放入list中）
     * 使用队列api
     *
     * @param root
     * @return
     */
    public ArrayList<Integer> printTree(BinaryTreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();//offer、poll方法返回一个特定的值
            list.add(node.value);

            if (node.left != null)
                queue.offer(node.left);
            if (node.right != null)
                queue.offer(node.right);
        }
        return list;
    }

    /**
     * 二叉树的层次遍历——广度优先遍历
     * 通过ArrayList模仿队列
     *
     * @param root
     * @return
     */
    public ArrayList<Integer> PrintFromTopToBottom(BinaryTreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<BinaryTreeNode> queue = new ArrayList<>();
        if (root == null) {
            return list;
        }
        queue.add(root);
        while (queue.size() != 0) {
            BinaryTreeNode temp = queue.remove(0);//返回第一个
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
            list.add(temp.value);//添加到末尾
        }
        return list;
    }

    /**
     * 题目2-按照行打印二叉树
     *
     * @param root
     */
    public void printTreeByRow(BinaryTreeNode root) {
        if (root == null) {
            return;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.offer(root);

        //使用两个变量来保存本行没打印的节点、下一行节点数
        int toBePrint = 1;
        int nextLevel = 0;
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();//offer、poll方法返回一个特定的值
            toBePrint--;
            System.out.print(node.value + " ");

            if (node.left != null) {
                queue.offer(node.left);
                nextLevel++;
            }
            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
            if (toBePrint == 0) {
                System.out.println();
                toBePrint = nextLevel;
                nextLevel = 0;
            }
        }
    }

    /**
     * 题目3-之字形打印二叉树
     *
     * @param root
     */
    public ArrayList<ArrayList<Integer>> printTreeByZRow(BinaryTreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();//结果数组
        if (root == null) {
            return list;
        }
        Stack<BinaryTreeNode> stack1 = new Stack<>();
        Stack<BinaryTreeNode> stack2 = new Stack<>();
        ArrayList<Integer> temp = new ArrayList<>();//内层临时数组
        stack1.push(root);
        int numofLine = 1;

        while (!stack1.empty() || !stack2.empty()) {
            if ((numofLine & 1) == 1) {//当打印奇数层，先保存左子节点到stack2
                while (!stack1.empty()) {
                    BinaryTreeNode node = stack1.pop();
//                    System.out.print(node.value + " ");
                    temp.add(node.value);
                    if (node.left != null)
                        stack2.push(node.left);

                    if (node.right != null)
                        stack2.push(node.right);
                }
                if (!temp.isEmpty()) {
                    list.add(new ArrayList<>(temp));
                    temp.clear();
//                    System.out.println( );
                    numofLine++;
                }
            } else {//当打印偶数层，先保存右子节点到stack1
                while (!stack2.empty()) {
                    BinaryTreeNode node = stack2.pop();
//                    System.out.print(node.value + " ");
                    temp.add(node.value);
                    if (node.right != null) {
                        stack1.push(node.right);
                    }
                    if (node.left != null) {
                        stack1.push(node.left);
                    }
                }
                if (!temp.isEmpty()) {
                    list.add(new ArrayList<>(temp));
                    temp.clear();
//                    System.out.println();
                    numofLine++;
                }
            }
        }
        return list;
    }

    /**
     * 通过ArrayList的双向性，实现z遍历
     *
     *  大家的实现很多都是将每层的数据存进ArrayList中，偶数层时进行reverse操作，
     *  在海量数据时，这样效率太低了。
     *  *
     *  * 下面的实现：不必将每层的数据存进ArrayList中，偶数层时进行reverse操作，按打印顺序存入
     *  * 思路：利用Java中的LinkedList的底层实现是双向链表的特点。
     *  *     1)可用做队列,实现树的层次遍历
     *  *     2)可双向遍历,奇数层时从前向后遍历，偶数层时从后向前遍历
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(BinaryTreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        if (pRoot == null) {
            return ret;
        }
        ArrayList<Integer> list = new ArrayList<>();
        LinkedList<BinaryTreeNode> queue = new LinkedList<>();
        queue.addLast(null);//层分隔符
        queue.addLast(pRoot);
        boolean leftToRight = true;

        while (queue.size() != 1) {
            BinaryTreeNode node = queue.removeFirst();
            if (node == null) {//到达层分隔符
                Iterator<BinaryTreeNode> iter = null;
                if (leftToRight) {
                    iter = queue.iterator();//从前往后遍历
                } else {
                    iter = queue.descendingIterator();//从后往前遍历
                }
                leftToRight = !leftToRight;
                while (iter.hasNext()) {
                    BinaryTreeNode temp = iter.next();
                    list.add(temp.value);
                }
                ret.add(new ArrayList<>(list));
                list.clear();
                queue.addLast(null);//添加层分隔符
                continue;//一定要continue
            }
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }

        return ret;
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);
        System.out.println(new PrintBinaryTree().printTreeByZRow(node));
        /*List a=new PrintBinaryTree().Print(node);
        System.out.println(a);*/
    }
}

```

### 面试题33-二叉搜索树的后序遍历序列

- BST的插入：平均时间O(logn)

```java
package part1;

import java.util.Arrays;

/**
 * @Auther: PC
 * @Date: 2019/1/10 22:09
 * @Description:
 */
public class ISBST {
    /**
     * 判断是否为bst的后序遍历序列
     *
     * @param sequence
     * @return
     */
    boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length==0)
            return false;

        int root = sequence[sequence.length - 1];
        //查找左子树中小于根节点的值
        int i;
        for (i = 0; i < sequence.length - 1; i++)
            if (sequence[i] > root)
                break;

        //查找右子树中大于根节点的值
        int j = i;
        for (; j < sequence.length - 1; j++)
            if (sequence[j] < root)
                return false;

        boolean left = true;
        boolean right = true;
        if (i > 0)
            left = VerifySquenceOfBST(Arrays.copyOfRange(sequence, 0, i));//包含起点，不包含终点
        if (j < sequence.length - 1)
            right = VerifySquenceOfBST(Arrays.copyOfRange(sequence, i + 1, sequence.length - 1));

        return left && right;
    }

    public static void main(String[] args) {
        int a[] = {2, 3, 4, 5, 6, 7, 8, 9};
        int from = 0;
        int to = 8;
        int original[] = Arrays.copyOfRange(a, from, to);
        for (int c : original) {
            System.err.println(c);
        }
    }
}
```

### 面试题34- 二叉树中和为某一值的路径

```java
package part1;

import java.util.Stack;

/**
 * @Auther: PC
 * @Date: 2019/1/11 09:49
 * @Description:
 */
public class FindPath {
    public void findPath(BinaryTreeNode root, int num) {
        if (root == null)
            return;

        Stack<Integer> stack = new Stack<>();
        int curSum = 0;
        //前序遍历根节点，通过栈保存节点；如果节点和为num，则输出
        findPath(root, num, stack, curSum);
    }

    private void findPath(BinaryTreeNode root, int num, Stack<Integer> stack, int curSum) {
        boolean isLeaf = root.left == null && root.right == null;
        stack.push(root.value);
        curSum += root.value;

        //当访问到叶节点，并且num符合
        if (isLeaf && curSum == num)
            for (Integer i : stack)
                System.out.println(i);

        if (root.left != null)
            findPath(root.left, num, stack, curSum);
        if (root.right != null)
            findPath(root.right, num, stack, curSum);

        //再返回父节点前，在路径上删除当前节点
        //两种情况会访问到这：叶节点；某节点的子路径都访问完了，没有符合条件的路径，要返回上一层节点
        stack.pop();
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);

        new FindPath().findPath(node, 10);
    }
}
```



### 面试题35- 复杂链表的复制

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/11 11:12
 * @Description:
 */
public class CloneLinkedList {
    RandomListNode clone(RandomListNode pHead) {
        cloneNodes(pHead);
        setRandomNode(pHead);
        return cutList(pHead);
    }

    //第一步，将节点复制到原节点之后
    private void cloneNodes(RandomListNode phead) {
        RandomListNode cur = phead;
        while (cur != null) {
            RandomListNode temp = new RandomListNode();
            temp.label = cur.label;
            temp.next = cur.next;
            temp.random = null;

            cur.next = temp;
            cur = temp.next;
        }
    }

    //第二步，设定clone出的节点的random指向
    private void setRandomNode(RandomListNode phead) {
        RandomListNode cur = phead;
        while (cur != null) {
            RandomListNode temp = cur.next;
            if (cur.random != null)
                temp.random = cur.random.next;
            cur = temp.next;
        }
    }

    //第三步，将长链表拆成两个子链表，偶数位形成的链表为clone链表
    private RandomListNode cutList(RandomListNode phead) {
        RandomListNode listNode = phead;
        RandomListNode cloneNode=null;
        RandomListNode newHead=null;

        //给cloneNode赋第一个值，并将listNode向后移动
        if (listNode != null) {
            cloneNode = listNode.next; //cloneNode的第一个节点为长链的第二个节点
            newHead = cloneNode;
            listNode.next = cloneNode.next;//listNode的下一个指向cloneNode的下一个节点
            listNode = listNode.next;     //将listNode指向新的位置
        }

        //开始循环
        while (listNode != null) {
            cloneNode.next = listNode.next;
            cloneNode = cloneNode.next;
            listNode.next = cloneNode.next;
            listNode = listNode.next;
        }
        return newHead;
    }

    private class RandomListNode {
        int label;
        RandomListNode next;
        RandomListNode random;
    }
}
```

### 面试题36- 二叉搜索树与双向链表

```java
package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/11 15:17
 * @Description:
 */
public class BSTtoDeList {
    TreeNode head;
    TreeNode lastNodeInList;

    TreeNode doBSTtoDeList(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return null;
        //转换左子树
        if (pRootOfTree.left != null)
            doBSTtoDeList(pRootOfTree.left);

        if (lastNodeInList == null) {
            lastNodeInList = pRootOfTree;
            head = pRootOfTree;
        } else {
            lastNodeInList.right = pRootOfTree;
            pRootOfTree.left = lastNodeInList;
            lastNodeInList = pRootOfTree;
        }

        if (pRootOfTree.right != null)
            doBSTtoDeList(pRootOfTree.right);

        return head;
    }


    /**
     * 右，中，左中序遍历，直接返回倒序排列的链表
     * @param pRootOfTree
     * @return
     */
    public TreeNode doBSTtoDeListBetter(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return pRootOfTree;

        doBSTtoDeListBetter(pRootOfTree.right);
        if (head == null) {
            head = pRootOfTree;
        } else {
            head.left = pRootOfTree;
            pRootOfTree.right = head;
            head = pRootOfTree;
        }
        doBSTtoDeListBetter(pRootOfTree.left);

        return head;
    }
    
    /**
     * 非递归，使用栈实现中序遍历的非递归算法，便可以找出节点的先后关系，依次连接即可
     * @param root
     * @return
     */
    public TreeNode Convert(TreeNode root) {
        if(root==null)
            return null;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode p = root;
        TreeNode pre = null;// 用于保存中序遍历序列的上一节点
        boolean isFirst = true;
        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p = p.left;
            }
            p = stack.pop();
            if(isFirst){
                root = p;// 将中序遍历序列中的第一个节点记为root
                pre = root;
                isFirst = false;
            }else{
                pre.right = p;
                p.left = pre;
                pre = p;
            }
            p = p.right;
        }
        return root;
    }
}

class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}
```

### 面试题37- 序列化和反序列化二叉树

```java
package part1;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/14 22:07
 * @Description:
 */
public class SerilizeTree {

    List<String> list = new ArrayList<>();
    int index = -1;

    /**
     * 序列化二叉树函数
     * null用%替代
     *
     * @param root
     */
    List<String> Serilize(BinaryTreeNode root) {
        if (root == null) {
            list.add("%");
            return list;
        }
        //前序遍历二叉树
        list.add(String.valueOf(root.value));
        Serilize(root.left);
        Serilize(root.right);

        return list;
    }

    /**
     * 反序列化二叉树
     * @param list
     * @return
     */
    BinaryTreeNode Deserilize(List<String> list) {
        BinaryTreeNode root = new BinaryTreeNode();
        return Deserilize(root, list);
    }

    private BinaryTreeNode Deserilize(BinaryTreeNode root, List<String> list) {
        index++;
        if (index >= list.size())
            return root;
        String num = list.get(index);
        if (num != "%") {
            root = new BinaryTreeNode();
            root.value = Integer.valueOf(num);
            root.left = Deserilize(root.left, list);
            root.right = Deserilize(root.right, list);
        }
        return root;
    }

    public static void main(String[] args) {
        BinaryTreeNode node = new BinaryTreeNode();
        node.value = 1;
        node.add(2, 3);
        node.left.add(4, 5);
        node.right.add(6, 7);
        List<String> list = new SerilizeTree().Serilize(node);
        System.out.println(list);
        BinaryTreeNode root = new SerilizeTree().Deserilize(list);
        System.out.println(root);
    }
}
```

### 面试题41-数据流中的中位数

```java
package part1;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 15:49
 * @Description: 通过最大堆，最小堆实现找到数据流的中位数
 */
public class MidNumInFlow {
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    private int count;

    public void Insert(Integer num) {
        //count为奇数，进入最大堆，count变为偶数
        if ((count & 1) == 1) {
            minHeap.offer(num);
            int filterMinNum = minHeap.poll();
            maxHeap.offer(filterMinNum);
        } else {//count为偶数，进入最小堆，count变为奇数
            maxHeap.offer(num);
            int filterMaxNum = maxHeap.poll();
            minHeap.offer(filterMaxNum);
        }
        count++;
    }

    Double GetMedian() {
        if ((count & 1) == 0)
            return (double) ((minHeap.peek() + maxHeap.peek())) / 2;
        else
            return (double) (minHeap.peek());
    }

    public static void main(String[] args) {
        MidNumInFlow midNumInFlow = new MidNumInFlow();
        midNumInFlow.Insert(5);
        System.out.println(midNumInFlow.GetMedian());
        midNumInFlow.Insert(2);
        System.out.println(midNumInFlow.GetMedian());
    }
}
```

### 面试题52 两个链表的第一个公共节点

- 当两个链表有公共节点时，从第一个公共节点开始，之后他们所有的节点都是重合的，不可能再出现分叉；
- 思路1：将两个链表分别放入栈；然后出栈进行对比，找到最后一个相同的节点；
- 思路2：分别求出两个链表的长度；设长度差为m；则快的先走m，然后一起走，直到找到第一个相同的节点；
- 注意：节点相同，即：node1==node2

```java
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        //得到两个链表的长度
        int n=lengthOfList(pHead1);
        int m=lengthOfList(pHead2);
        //长度长的先走，然后短的再一起走，最后找到相同的一个节点
        ListNode longHead;
        ListNode shortHead;
        int lengthDif;
        if(m-n>0){
            longHead=pHead2;
            shortHead=pHead1;
            lengthDif=m-n;
        }else{
            longHead=pHead1;
            shortHead=pHead2;
            lengthDif=n-m;
        }
        for(int i=0; i<lengthDif;i++)
            longHead=longHead.next;
        
        while(longHead!=null && shortHead!=null ){
            if(longHead==shortHead)
                return longHead;
            longHead=longHead.next;
            shortHead=shortHead.next;
        }
        return null;
    }
    
    private int lengthOfList(ListNode pHead){
        int n=0;
        while(pHead!=null){
            n++;
            pHead=pHead.next;
        }
        return n;
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
　　poll        移除并返问队列头部的元素    如果队列为空，则返回null
　　peek       返回队列头部的元素             如果队列为空，则返回null
　　put         添加一个元素                 如果队列满，则阻塞
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

  $$
  \left[
  \begin{matrix}
  f(n)& f(n-1) \\
  f(n-1) & f(n-2) 
  \end{matrix}
  \right]=\left[
  \begin{matrix}
  1& 1 \\
  1 & 0 
  \end{matrix}
  \right]^{n-1}
  $$
  所以求得左上角第一个就行

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

### 面试题30-包含min函数的栈

```java
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
```



### 面试题31-栈的压入弹出序列

```java
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

    public static void main(String[] args) {

    }
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



