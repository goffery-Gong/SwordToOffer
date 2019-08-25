package LeetCode.Knapsack;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/5/14 15:26
 * @Description: 01背包问题：有一个容量为 W 的背包，要用这个背包装下物品的价值最大，这些物品有两个属性：体积 w 和价值 v
 */
public class MaxValueInPackage {
    /**
     * 经典动态规划，二维数组
     *
     * @param W 背包最大容纳重量
     * @param N 物品个数
     * @param w 重量数组
     * @param v 价值数组
     * @return 最大价值
     */
    public int maxValue(int W, int N, int[] w, int[] v) {
        int[][] dp = new int[N + 1][W + 1];//dp[i][j]表示包容量为j时，前i个物品能够组合出的最大价值
        for (int i = 1; i <= N; i++) {
            int weight = w[i - 1];
            int val = v[i - 1];
            for (int j = 1; j <= W; j++) {
                if (weight > j)
                    dp[i][j] = dp[i - 1][j];
                else
                    dp[i][j] = Math.max(dp[i - 1][j - weight] + val, dp[i - 1][j]);//前这个物品可以装进去时，考虑：装这个物品价值大还是不装这个物品价值大？
            }
        }
        return dp[N][W];
    }

    /**
     * 改进动态规划，一维数组
     *
     * @param W 背包最大容纳重量
     * @param N 物品个数
     * @param w 重量数组
     * @param v 价值数组
     * @return 最大价值
     */
    public int maxValue2(int W, int N, int[] w, int[] v) {
        int[] dp = new int[W + 1];
        //第0行初始值都是0，默认初始化了
        for (int i = 1; i <= N; i++) {
            /*for (int j = W; j >=1 ; j--) {
                if(w[i-1]>j)
                    dp[j]=dp[j];
                else
                    dp[j]=Math.max(dp[j-w[i-1]]+v[i-1],dp[j]);
            }*/
            //以上可以简化为
            for (int j = W; j >= w[i - 1]; j--)
                dp[j] = Math.max(dp[j - w[i - 1]] + v[i - 1], dp[j]);
        }
        return dp[W];
    }


    public int findTargetSumWays(int[] nums, int S) {
        int len=nums.length;
        int sum=0;
        for(int i=0; i<len; i++)
            sum+=nums[i];
        int s1=(S+sum)>>1;//正数的和
        // int s2=(sum-S)>>1;//负数的和

        int[] dp=new int[s1+1];//前i个数的和为j的组合数
        dp[0]=1;
        for(int i=0; i<len; i++){
            for(int j=s1; j>=nums[i]; j--)
                dp[j]=dp[j]+dp[j-nums[i]];
        }
        return dp[s1];
    }

    public static void main(String[] args) {
        int[] w = {2, 3, 4, 5, 9};
        int[] v = {3, 4, 5, 8, 10};
        int[] maxtrix = {1, 1, 1, 1,1 };
//        System.out.println(new MaxValueInPackage().maxValue(20, 5, w, v));
//        System.out.println(new MaxValueInPackage().maxValue2(20, 5, w, v));
        System.out.println(new MaxValueInPackage().findTargetSumWays(maxtrix,3));
    }

}
