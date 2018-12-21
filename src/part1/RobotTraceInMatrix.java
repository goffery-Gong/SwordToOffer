package part1;

/**
 * 矩阵中寻找字符串
 */
public class RobotTraceInMatrix {
    /**
     *
     * @param bound k值
     * @param rows 行
     * @param cols 列
     * @return
     */
    public static int countMoving(int bound, int rows, int cols) {
        if (bound < 0 || rows<=0 || cols<=0) return 0;
        boolean[] isVisited = new boolean[rows * cols];
        int count=0;

        int size=countNum(bound, rows, cols, 0,0, isVisited,count);
        return size;
    }

    private static int countNum(int bound, int rows, int cols, int i, int j,boolean[] isVisited,int count) {
         if(count(bound,rows,cols,i,j,isVisited)){
            isVisited[i*cols+j]=true;
            count++;
            count=countNum(bound,rows,cols,i-1,j,isVisited,count);
            count=countNum(bound,rows,cols,i+1,j,isVisited,count);
            count=countNum(bound,rows,cols,i,j-1,isVisited,count);
            count=countNum(bound,rows,cols,i,j+1,isVisited,count);
        }
        return count;
    }

    private static boolean count(int bound, int rows, int cols, int i, int j,boolean[] isVisited) {
        if(i>=rows || i<0 || j>=cols || j<0 || isVisited[i*cols+j])
            return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(countMoving(10,3,3));

    }
}
