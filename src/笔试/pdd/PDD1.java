package 笔试.pdd;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/15 21:02
 * @Description:
 */
public class PDD1 {
    public void solution(){
        Scanner sc=new Scanner(System.in);
        int len=sc.nextInt();
        //输入
        int[] a=new int[len];
        int[] b=new int[len];
        for (int i = 0; i < len; i++)
            a[i]=sc.nextInt();
        for (int i = 0; i < len; i++)
            b[i]=sc.nextInt();

        Arrays.sort(a);
        Arrays.sort(b);
        int sum=0;
        for (int i = 0; i < len; i++)
            sum+=a[i]*b[i];
        System.out.println(sum);
    }
}
