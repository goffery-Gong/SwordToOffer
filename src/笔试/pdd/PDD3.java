package 笔试.pdd;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/10 16:41
 * @Description:
 */
public class PDD3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int d = sc.nextInt();
        int[] dis = new int[n];
        int[] money = new int[n];
        for (int i = 0; i < n; i++) {
            dis[i] = sc.nextInt();
            money[i] = sc.nextInt();
        }
        Bank[] array = new Bank[n];
        int result = 0;

        for (int i = 0; i < n; i++)
            array[i] = new Bank(dis[i], money[i], 0);

        //通过dis将银行排序
        Arrays.sort(array, Comparator.comparingInt(o -> o.dis));
        int maxMoney = 0;

        //将每个银行右边（包括自己）能够获取的最大money存入数组
        for (int i = n - 1; i >= 0; i--) {
            if (array[i].money > maxMoney)
                maxMoney = array[i].money;
            //保存每个位置上的最大money
            array[i].rightMax = maxMoney;
        }

        //找到最大的组合
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int diff = array[j].dis - array[i].dis;
                int sum = array[i].money + array[j].rightMax;
                if (diff >= d) {//
                    if (result < sum)
                        result = sum;
                    break;
                }
            }
        }

        System.out.println(result);
    }

    static class Bank {
        int dis;
        int money;
        int rightMax;

        Bank(int dis, int money, int rightMax) {
            this.dis = dis;
            this.money = money;
            this.rightMax = rightMax;
        }
    }

}
