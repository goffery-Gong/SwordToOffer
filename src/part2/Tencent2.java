package part2;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/9 22:28
 * @Description: i*(-1)^i
 */
public class Tencent2 {
    public int sum( int l, int r) {
        int sum = 0;
        for (int i = Math.abs(l); i <= Math.abs(r); i++) {
            if ((i & 1) == 0)
                sum += i;
            else
                sum+=-i;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new Tencent2().sum(2,3));
    }
}
