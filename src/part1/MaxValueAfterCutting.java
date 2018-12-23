package part1;

import static java.lang.Math.pow;

public class MaxValueAfterCutting {
    /**
     * 动态规划
     * 时间复杂度为 O(n^2)，另外，建立了辅助容器，空间复杂度为 O(n)；
     * @param n
     * @return
     */
    public static int maxValueAferCutting(int n){
        //本身n=3和n=2的情况下，不切是最长的，但是要求必须m>1
        if(n<2)
            return 0;
        if(n==2)
            return 1;
        if(n==3)
            return 2;

        int maxLength=0;
        int[] tempLengths=new int[n+1];
        tempLengths[0]=0;
        tempLengths[1]=1;
        tempLengths[2]=2;
        tempLengths[3]=3;

        for (int i = 4; i <= n; i++) {
            for (int j = 1; j <= i/2; j++) {
                int tempLength=tempLengths[j]*tempLengths[i-j];
                if(maxLength<tempLength)
                    maxLength=tempLength;
                tempLengths[i]=maxLength;
            }
        }
        return maxLength;
    }

    /**
     * 贪心算法 O(1)
     * @param n
     * @return
     */
    public static int maxValueAferCutting2(int n){
        if(n<2)
            return 0;
        if(n==2)
            return 1;
        if(n==3)
            return 2;

        int timesOf3=n/3;
        if(n-timesOf3*3==1)
            timesOf3-=1;
        int timesOf2=(n-timesOf3*3)/2;

        return (int) (pow(3,timesOf3)*pow(2,timesOf2));
    }
    public static void main(String[] args) {
        System.out.println(maxValueAferCutting2(8));
    }
}
