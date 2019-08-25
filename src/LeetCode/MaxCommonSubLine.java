package LeetCode;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/5/14 10:11
 * @Description: 最长公共子序列
 */
public class MaxCommonSubLine {
    public int maxCommonSubLine(int[] s1, int[] s2){
        int len1=s1.length;
        int len2=s2.length;
        int[][] dp=new int[len1+1][len2+1];// S1 的前 i 个字符与 S2 的前 j 个字符公共子序列长度
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j < len2; j++) {
                if(s1[i-1]==s2[j-1])
                    dp[i][j]=dp[i-1][j-1]+1;
                else
                    dp[i][j]=Math.max(dp[i-1] [j], dp[i] [j-1]);
            }
        }
        return dp[len1][len2];
    }
}
