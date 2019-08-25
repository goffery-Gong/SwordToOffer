package LeetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/5/5 15:35
 * @Description:
 */
public class Test64 {
    private int sum = 0;

    /**
     * 回溯法 时间复杂度为指数级别
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        minPathSum(grid, 0, 0, 0);
        return sum;
    }

    // 回溯法要注意，由于在一个边到顶以后还要回溯另一个边
    // 所以数组的临界条件不能越界, 也就是如下 w==width-1 && h==height-1
    // 相应的，递归的临界条件也应该使得下一层不能越界
    private void minPathSum(int[][] grid, int row, int col, int temp) {
        temp += grid[row][col];
        if (row == grid.length - 1 && col == grid[0].length - 1)
            sum = sum == 0 ? temp : (temp < sum ? temp : sum);

        if (row < grid.length - 1)// 递归下一层不能越界
            minPathSum(grid, row + 1, col, temp);
        if (col < grid[0].length - 1)// 递归下一层不能越界
            minPathSum(grid, row, col + 1, temp);
//        int[][] pairs=new int[5][6];
//        Arrays.sort(pairs, (o1, o2) -> o1[0]-o2[0]);
    }

    public int minPathSum2(int[][] grid) {
        return minPathSum(grid, grid.length - 1, grid[0].length - 1);
    }

    private int minPathSum(int[][] grid, int row, int col) {
        if (row == 0 && col == 0)
            return grid[0][0];
        return grid[row][col] + Math.min(minPathSum(grid, row, col - 1), minPathSum(grid, row - 1, col));

    }

    public int lengthOfLIS(int[] nums) {
        /**
         dp[i]: 所有长度为i+1的递增子序列中, 最小的那个序列尾数.
         由定义知dp数组必然是一个递增数组, 可以用 maxL 来表示最长递增子序列的长度.
         对数组进行迭代, 依次判断每个数num将其插入dp数组相应的位置:
         1. num > dp[maxL], 表示num比所有已知递增序列的尾数都大, 将num添加入dp
         数组尾部, 并将最长递增序列长度maxL加1
         2. dp[i-1] < num <= dp[i], 只更新相应的dp[i]
         **/
        int maxL = 0;
        int[] dp = new int[nums.length];
        for (int num : nums) {
            // 二分法查找, 也可以调用库函数如binary_search
            int lo = 0, hi = maxL;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (dp[mid] < num)
                    lo = mid + 1;
                else
                    hi = mid;
            }
            dp[lo] = num;
            if (lo == maxL)
                maxL++;
        }
        return maxL;
    }

    public int maxSubLineSum(int[] nums) {
        int sum = 0;
        int[] dp = new int[nums.length + 1];//dp[i]表示以num[i]结尾的最大子序列和
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = dp[i - 1] + nums[i];
            if (dp[i] > sum)
                sum = dp[i];
        }
        return sum;
    }

    public int findNumberOfLIS(int[] nums) {
        int len = nums.length;
        int max = 0;
        int res = 0;

        int[] dp = new int[len];//表示到第i个的最长递增子序列长度；
        int[] dpN = new int[len];

        Arrays.fill(dp, 1);
        Arrays.fill(dpN, 1);

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[i] < dp[j] + 1){
                        dp[i] = dp[j]+1;
                        dpN[i] = dpN[j];
                    }
                    else if (dp[i] == dp[j] + 1)
                        dpN[i] += dpN[j];//如果+1等于当前LIS 则说明找到了新组合
                }
            }
            max = Math.max(max, dp[i]);
        }
        for (int i = 0; i < len; i++)
            if (dp[i] == max)
                res += dpN[i];

        return res;
    }

    public boolean isPalindrome(ListNode head) {
        ListNode rehead=reverseList(head);
        ListNode p1=head;
        ListNode p2=rehead;
        while(p1!=null && p2!=null){
            if(p1.val==p2.val){
                p1=p1.next;
                p2=p2.next;
            }else
                break;
        }
        return p1 == null;
    }

    public ListNode reverseList(ListNode head){
        if(head==null || head.next==null)
            return head;
        ListNode next=head.next;
        ListNode newList=reverseList(head.next);
        next.next=head;
        head.next=null;
        return newList;
    }

    public static void main(String[] args) {
        ListNode head =new ListNode(0);
        ListNode cur=head;
        int flag=3;
        while(flag!=0){
            cur.next=new ListNode(1);
            cur=cur.next;
            flag--;
        }
    }

    static class ListNode{
        int val;
        ListNode next;

        ListNode(int val){
            super();
            this.val=val;
        }
    }
}
