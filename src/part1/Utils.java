package part1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 22:52
 * @Description:
 */
public class Utils {


    //复杂度O(N)
    static int Partition(int[] a, int lo, int hi) throws Exception {
        if (a == null || a.length == 0 || hi <= 0 || hi >= a.length)
            throw new Exception("参数有误！");

        //找到一个位置，然后将此位置的数换到最后
        int index = RandomInRange(lo, hi);
        exch(a, index, hi);

        //small是指向比最后一个数小的位置，每找到一个小的就往后移动一位
        int small = lo - 1;
        //遍历前hi-1个数，找到比最后一个数小的，交换到small位置
        for (index = lo; index < hi; index++) {
            if (a[index] < a[hi]) {
                ++small;
                if (small != index)
                    exch(a, index, small);
            }
        }
        ++small;
        exch(a, small, hi);

        return small;
    }

    static int RandomInRange(int lo, int hi) {
        Random random = new Random();
        int s = random.nextInt(hi) % (hi - lo + 1) + lo;
        return s;
    }

    static void exch(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static int myPartition(int[] array, int lo, int hi) {
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

    public static void main(String[] args) {
        Integer[] a = {1, 2, -1, 0};
        for (int i : a)
            System.out.print(i + " ");
        System.out.println();
//        System.out.println(Utils.myPartition(a,a.length-1,a.length-1));
//        System.out.println(Utils.RandomInRange(10, 20));
        Arrays.sort(a);
        for (int i : a)
            System.out.print(i + " ");
        System.out.println();

        //倒序1
        Arrays.sort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1); // (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });

        //倒序2
        Arrays.sort(a, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        //倒序3
        Arrays.sort(a, (o1, o2) -> o2 - o1);
        //倒序4
        Arrays.sort(a, (o1, o2) -> o2.compareTo(o1));
        //倒序5
        Arrays.sort(a, Comparator.reverseOrder());

        for (int i : a)
            System.out.print(i + " ");
    }

    /**
     * 判断输入数组是否为空或者长度为0
     * @param array
     * @return
     */
    public static boolean isNullorZero(int[] array) {
        return array==null|| array.length==0 ;
    }
}
