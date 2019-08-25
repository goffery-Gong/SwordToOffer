package 笔试.card;

import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/4/9 19:46
 * @Description:
 */
public class Main1 {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        System.out.println(JumpFloorII(n,0));
    }
    public static int JumpFloorII(int target,int sum) {
        if (target <6) return 0;
        if (target==6) return 1;
        for (int i = target-6; i >=0 ; i--) {
            sum+=JumpFloorII(i,sum);
        }
        return sum;
    }
}
