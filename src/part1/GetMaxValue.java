package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 19:30
 * @Description:
 */
public class GetMaxValue {
    /**
     * 常规递归方法
     * @param values
     * @param rows
     * @param cols
     * @return
     */
    int getMaxValue(int[] values, int rows, int cols) {
        if (values == null || rows<=0 ||cols<=0)
            return 0;
        return getMaxValue(values, rows, cols, 0, 0,0);
    }

    private int getMaxValue(int[] values, int rows, int cols, int i, int j,int count) {
        int index = i * cols + j;
        if (i < 0 || i >= rows || j < 0 || j >= cols )
            return count;

        //f(i,j)=max{f(i+1,j),f(i,j+1)}+gift(i,j)
        int value1=getMaxValue(values, rows, cols, i + 1, j, count);
        int value2=getMaxValue(values, rows, cols, i, j + 1, count);
        count = values[index] + Math.max(value1,value2);

        return count;
    }

    /**
     * 动态规划1
     * f(i,j)=max{f(i-1,j),f(i,j-1)}+gift(i,j)
     * @param values
     * @param rows
     * @param cols
     * @return
     */
    int getMaxValueDG(int[] values,int rows, int cols){
        if (values == null || rows<=0 ||cols<=0)
            return 0;

        int[][] maxValues=new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left=0;
                int up=0;
                if(i>0)
                    left=maxValues[i-1][j];
                if(j>0)
                    up=maxValues[i][j-1];

                //maxValues[i][j]元素表示到达坐标为（i,j）的格子时，value最大值
                maxValues[i][j]=Math.max(left,up)+values[i*cols+j];
            }
        }
        return maxValues[rows-1][cols-1];
    }

    /**
     * 优化
     * 直接使用一维数组保存，长度为列数n。当计算dp[i][j]时，
     * 数组前j个数字分别是dp[i][0], dp[i][1],...,dp[i][j-1],
     * 数组从下标为j的数字开始到最后一个数字分别是dp[i-1][j],
     * dp[i-1][j+1],...,dp[i-1][n-1]。
     */
    int getMaxValueDG2(int[] values,int rows, int cols){
        if (values == null || rows<=0 ||cols<=0)
            return 0;

        int[] maxValues=new int[cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left=0;
                int up=0;
                if(i>0)
                    left=maxValues[j];
                if(j>0)
                    up=maxValues[j-1];

                //maxValues[i][j]元素表示到达坐标为（i,j）的格子时，value最大值
                maxValues[j]=Math.max(left,up)+values[i*cols+j];
            }
        }
        return maxValues[cols-1];
    }
    public static void main(String[] args) {
        int[] values = {1, 10, 3, 8, 12, 2, 9, 6, 5, 7, 4, 11, 3, 7, 16, 5};
        System.out.println(new GetMaxValue().getMaxValueDG(values, 4, 4));
    }
}
