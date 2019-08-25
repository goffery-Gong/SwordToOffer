package part2;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/13 10:50
 * @Description:
 */
public class Partition {
    public ArrayList<Integer> GetLeastNumbers_Solution_MaxHeap(int[] input, int k) {
        //要找到k个数中的最大值，然后进行比较替换
        ArrayList<Integer> result = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k <= 0) {
            return result;
        }
        //优先队列实现了最大堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());//从大到小，最大堆
        for (int i = 0; i < input.length; i++) {
            if (maxHeap.size() == k) {
                if (maxHeap.peek() > input[i]) {
                    Integer temp = maxHeap.poll();
                    temp = null;
                    maxHeap.offer(input[i]);
                }
            } else
                maxHeap.offer(input[i]);
        }
        result.addAll(maxHeap);
        return result;
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k <= 0)
            return list;

        int lo = 0;
        int hi = input.length - 1;
        int m = Partition(input, lo, hi);
        while (m != k - 1) {
            if (m + 1 < k) {
                lo = m + 1;
                m = Partition(input, lo, hi);
            } else {
                hi = m - 1;
                m = Partition(input, lo, hi);
            }
        }
        for (int i = 0; i <= m; i++)
            list.add(input[i]);
        return list;
    }

    private int Partition(int[] input, int lo, int hi) {
        int index = RandomInRange(lo, hi);
        exch(input, index, hi);
        int small = lo - 1;
        for (index = lo; index < hi; index++) {
            if (input[index] < input[hi]) {
                small++;
                if (index != small)
                    exch(input, index, small);
            }
        }
        small++;
        exch(input, small, hi);
        return small;
    }

    private int RandomInRange(int lo, int hi) {
        return new Random().nextInt(hi) % (hi - lo + 1) + lo;
    }

    private void exch(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void main(String[] args) {
        int[] a = {4, 5, 1, 6, 2, 7, 3, 8};
        System.out.println(new Partition().GetLeastNumbers_Solution(a, 2));
        PriorityQueue<Integer> ii = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < 4; i++) {
            ii.offer(i);
        }
        System.out.println(ii);
    }
}
