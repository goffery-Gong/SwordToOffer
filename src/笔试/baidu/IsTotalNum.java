package 笔试.baidu;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/4/1 16:16
 * @Description:
 */
public class IsTotalNum {
    /**
     * 一个数的所有因数
     *
     * @param n
     * @return
     */
    private int isTotalNum(int n) {
        int sum=0;
        for (int i = 1; i < n; i++) {
            if(n%i==0)
                sum+=i;
        }
        return (sum==n)?1:0;
    }

    public static void main(String[] args) {
        System.out.println(new IsTotalNum().isTotalNum(28));
    }
}
