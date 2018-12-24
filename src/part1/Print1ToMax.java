package part1;

import java.util.Arrays;

/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/24 16:19
 * @Description:
 */
public class Print1ToMax {
    public void printToMax(int n) {
        if (n < 0)
            return;
        int[] number = new int[n];
//		初始化
//		for(int i = 0; i < n; i++)
//			number[i] = '0';
        Arrays.fill(number, '0');
        while (!increment(number)) {
            print(number);
        }
        return;
    }

    //使用数组实现对数进行+1操作
    public static boolean increment(int[] number){
        if(number.length<1)
            throw new RuntimeException("invalid lenth of array");
        //最高位产生进位标志，则数组中的数为最大的n位整数
        boolean isOverFlow=false;
        //进位位
        int carry=0;
        //没有产生进位的+1，循环只运行1次，产生一个进位，循环多运行一次
        for(int i=number.length-1;i>=0;i--){
            int sum=number[i]+carry;
            if(i==number.length-1)
                sum++;//最低位+1
            if(sum>=10){
                //最高位产生进位
                if(i==0)
                    isOverFlow=true;
                    //普通位产生进位
                else{
                    carry=1;
                    number[i]=0;
                    sum=0;
                }
            }else{
                //普通位+1的结果保存到数组中，+1后程序退出循环
                number[i]=sum;
                break;
            }
        }
        return isOverFlow;
    }

    //打印数组中表示的数，如果数组中表示的数字位数小于n，则不打印前面的0
    public static void print(int[] number){
        boolean isBeginning=true;
        for(int i=0;i<number.length;i++){
            if(isBeginning&&number[i]!=0)
                isBeginning=false;
            if(!isBeginning)
                System.out.print(number[i]);
        }
    }

    public static void main(String[] args) {
        new Print1ToMax().printToMax(2);
    }

}
