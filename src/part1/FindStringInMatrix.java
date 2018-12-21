package part1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 矩阵中寻找字符串
 */
public class FindStringInMatrix {
    public static boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null) return false;

        boolean[] isVisited = new boolean[rows * cols];
        //寻找第一个符合条件的字符作为入口
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ( hasPathElement(matrix, rows, cols, str, i, j, 0, isVisited))
                    return true;
            }
        }
        return false;
    }

    private static boolean hasPathElement(char[] matrix, int rows, int cols, char[] str,
                                          int i, int j, int strIndex, boolean[] isVisited) {
        int index = i * cols + j; //二维数组元素下标，转化为一维数组的下标
        if (i < 0 || i >=rows || j < 0 || j >= cols || matrix[index] != str[strIndex] || isVisited[index])
            return false;
        if(strIndex==str.length-1)
            return true;

        isVisited[index]=true;//先将访问的矩阵元素状态置为“true”
        if(hasPathElement(matrix, rows, cols, str, i-1, j, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i+1, j, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i, j-1, strIndex+1, isVisited)
                ||hasPathElement(matrix, rows, cols, str, i, j+1, strIndex+1, isVisited))
            return true;

        //如果没找到合适的，则将此时访问的矩阵元素状态恢复为“false”
        isVisited[index]=false;
        return false;
    }

    public static void main(String[] args) {
        char[] chars="ABCB".toCharArray();
        char[] matrix="ABCESFCSADEE".toCharArray();
        System.out.println(hasPath(matrix,3,4,chars));

    }
}
