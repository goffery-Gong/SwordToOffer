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

    public int rob(int[] nums) {
//        dp[i]=max(dp[i-2]+nums[i],dp[i-1])
         int pre1=0;
         int pre2=0;
         for(int i=0;i<nums.length;i++){
             int cur=Math.max(pre1,pre2+nums[i]);
             pre2=pre1;
             pre1=cur;
         }
         return pre1;
//        return rob(nums,nums.length-1);
    }

    private int rob(int[] nums,int i){
        if(i==-1 || i==-2)
            return 0;
        if(i==0)
            return nums[0];
        return Math.max(nums[i]+rob(nums,i-2),rob(nums,i-1));
    }

    public static void main(String[] args) {
        int[] nums={2,7};
        System.out.println(new Fibonaci().rob(nums));
    }
}
