package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/19 10:34
 * @Description:
 */
public class FindInMatrix {
    static boolean findInArray(int[][] array, int target) {
        if (array == null || array.length == 0)
            return false;

        int row = 0;
        int col = array[0].length - 1;
        while (row < array.length && col >= 0) {
            if (array[row][col] == target)
                return true;
            else if (target < array[row][col])
                col--;
            else
                row++;
        }
        return false;
    }
}
