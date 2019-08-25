package part2;

import java.util.LinkedList;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/18 15:47
 * @Description:
 */
public class Interview1 {
    public static void Print(int num){
        new Thread(()->{
            for (int i = num; i < 100; i+=2) {
                System.out.println(i);
            }
        }).start();
    }
    public static int maxSum(int[] array){
        int result=Integer.MIN_VALUE;
        int temp=0;
        for (int i = 0; i < array.length; i++) {
            if(temp<0)
                temp=array[i];
            else
                temp+=array[i];
            if(temp>result)
                result=temp;
        }
        return result;
    }

    public static void main(String[] args) {
//        Print(0);
//        Print(1);
        int[] a={-3,-5,-2,-4};
        System.out.println(maxSum(a));
        LinkedList<Object> list = new LinkedList<>();
        list.offer(null);
    }
}
