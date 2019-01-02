package part1;

import java.util.Arrays;

/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/24 16:19
 * @Description:
 */
public class Print1ToMax {
    /**
     * 通过用字符数组存储num
     * @param n
     */
    public void printToMax(int n) {
        if (n <= 0) {
            System.out.println("输入的n没有意义");
            return;
        }
        //声明字符数组,用来存放一个大数
        char number[] = new char[n];
        for (int i = 0; i < number.length; ++i) { //放字符0进行初始化
            number[i] = '0';
        }
        while (!incrementNumber(number)) { //如果大数自加，直到自溢退出
            printNumber(number); //打印大数
        }
    }

    //自加
    private boolean incrementNumber(char[] number) {
        boolean isOverflow = false; //判断是否到了最高位要溢出
        int nTakeOver = 0; //判断是否进位
        int nLength = number.length;
        for (int i = nLength - 1; i >= 0; --i) {
            int nSum = number[i] - '0' + nTakeOver; //取到第i位的字符转换为数字 +进位符
            if (i == nLength - 1) { //末尾自加1
                ++nSum;
            }
            if (nSum >= 10) {
                if (i == 0) //如果是最高位>10，那么溢出标志设为true
                    isOverflow = true;
                else {
                    nSum -= 10;
                    nTakeOver = 1;
                    number[i] = (char) ('0' + nSum);
                }
            } else {
                number[i] = (char) (nSum + '0');
                break;
            }
        }
        return isOverflow;
    }

    //打印某个数字
    private void printNumber(char[] number) {
        boolean isBeginning0 = true;
        int nLength = number.length;
        for (int i = 0; i < nLength; ++i) {
            //必须有isBeginning标记，如果没有类似10这种就打印成了1
            if (isBeginning0 && number[i] != '0') {
                isBeginning0 = false;
            }
            if (!isBeginning0) {
                System.out.print(number[i]);
            }
        }
        System.out.println();
    }

    /**
     * 全排列（递归）方法
     * @param n
     */
    public void printNumbers(int n){
        if(n<0)
            return ;
        char[] number=new char[n];
        for (int i = 0; i < 10; i++) {
            number[0]= (char) (i+'0');
            print1ToMax(number,n,0);
        }
    }

    public void print1ToMax(char[] number, int n, int index) {
        if(index==n-1) {
            printNumber(number);
            return;
        }
        for (int i = 0; i < 10; i++) {
            number[index+1]= (char) (i+'0');
            print1ToMax(number, n, index+1);
        }
    }

    /**
     * myMethod
     * @param n
     */
    public static void printNum(int n){
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < Math.pow(10, n); i++) {
            sb.append(i);
        }
        print(sb, n);
    }


    //打印字符串中的每个数字
    private static void print(StringBuffer sb, int n) {
        int j = 0;
        for (int i = 0; i < n; i++) {

            for (int k = j - 1 + 9 * (int) Math.pow(10, i) * (i + 1); j <= k; j += (i + 1)) {

                StringBuilder sbNew = new StringBuilder();

                for (int m = 0; m <= i; m++) {
                    sbNew.append(sb.charAt(j + m));
                }

                System.out.println(sbNew);
            }
        }
    }



    public static void main(String[] args) {
        new Print1ToMax().printNumbers(2);
//        new Print1ToMax().printToMax(2);
    }
}
