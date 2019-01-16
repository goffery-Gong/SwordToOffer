package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 20:58
 * @Description:
 */
public class MoreThanHalf {
    /**
     * 通过特点：数量超过一半的数字，一定在数组的中间位置
     * O(n)
     * @param array
     * @return
     */
    int moreThanHalf(int[] array) {
        if (array == null || array.length == 0)
            return 0;

        int mid = array.length >> 1;
        int index = Partition(array, 0, array.length - 1);
        int lo = 0;
        int hi = array.length - 1;
        while (index != mid) {
            if (index < mid) {
                lo = index + 1;
                index = Partition(array, lo, hi);
            } else {
                hi = index - 1;
                index = Partition(array, lo, hi);
            }
        }
        //判断该数字的数量是否大于数组长度的一半
        int result=array[index];
        if (isMoreThanHalf(array, result))
            return 0;
        return result;
    }

    /**
     * 特点：最后一次吧次数times设置为1的数字就是返回值
     * o(n)
     *
     * @param array
     * @return
     */
    int moreThanHalf2(int[] array){
        if (array == null || array.length == 0)
            return 0;

        int result=array[0];
        int times=0;
        for (int anArray : array) {
            if (times == 0) {
                result = anArray;
                times = 1;
            } else if (anArray == result)
                times++;
            else
                times--;
        }
        //判断该数字的数量是否大于数组长度的一半
        if (isMoreThanHalf(array, result))
            return 0;
        return result;
    }
    private boolean isMoreThanHalf(int[] array, int result) {
        int times = 0;
        for (int anArray : array)
            if (anArray == result)
                times++;
        return times * 2 < array.length;
    }

    private int Partition(int[] array, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (array[++i] < array[lo])
                if (i == hi)
                    break;
            while (array[lo] < array[--j])
                if (j == lo)
                    break;
            if (i >= j)
                break;
            exch(array, i, j);
        }
        exch(array, lo, j);
        return j;
    }

    private void exch(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 3};
        System.out.println(new MoreThanHalf().moreThanHalf2(a));
    }
}
