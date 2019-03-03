package part2;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 13:54
 * @Description:
 */
public class Fibonaci {
    //递归方法：大量重复计算 O(2^n)
    public int fibonaci(int n) {
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;

        return fibonaci(n - 1) + fibonaci(n - 2);
    }

    //循环 O(n)
    public int fibonaci2(int n) {
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;

        int resultN=0;
        int temp1 = 0;
        int temp2 = 1;
        for (int i = 2; i <= n; i++) {
            resultN = temp1 + temp2;
            temp1 = temp2;
            temp2 = resultN;
        }
        return resultN;
    }
}
