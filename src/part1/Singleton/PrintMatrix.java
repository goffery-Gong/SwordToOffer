package part1.Singleton;

public class PrintMatrix {
    public void printMatrix(int[][] matrix) {
        if (matrix == null)
            return;

        int start = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        while (start * 2 < cols && start * 2 < rows) {//循环结束条件
            printMatrixInCircle(matrix, rows, cols, start);
            start++;
        }
    }

    private void printMatrixInCircle(int[][] matrix, int rows, int cols, int start) {
        int endX = cols - 1 - start;
        int endY = rows - 1 - start;

        //从左向右打印
        for (int i = start; i <= endX; i++)
            System.out.println(matrix[start][i]);

        //从上到下打印,至少两行
        if (start < endY)
            for (int i = start + 1; i <= endY; i++)
                System.out.println(matrix[i][endX]);

        //从右到左打印，至少两行两列
        if (start < endY && start < endX)
            for (int i = endX - 1; i >= start; i--)
                System.out.println(matrix[endY][i]);

        //从下到上答应，至少三行两列
        if (endY - start > 1 && start < endX)
            for (int i = endY - 1; i > start; i--)
                System.out.println(matrix[i][start]);
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4, 5}, {5, 6, 7, 8, 9}, {9, 10, 11, 12, 13}, {13, 14, 15, 16, 17}};
        new PrintMatrix().printMatrix(matrix);
    }
}
