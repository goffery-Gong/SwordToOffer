package 笔试.card;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/4/9 19:46
 * @Description:
 */
public class Main2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int w=sc.nextInt();
        int[] array=new int[2*n];
        for (int i = 0; i < 2*n; i++) {
            array[i]=sc.nextInt();
        }
        Arrays.sort(array);
        double min;
        double manMin=array[n];
        double womanMin=array[0];
        if(manMin>2*womanMin)
            min=womanMin;
        else
            min=manMin/2;
        double temp=3*n*min;
        System.out.println(String.format("%.6f",temp<w?temp:w));
    }
}
