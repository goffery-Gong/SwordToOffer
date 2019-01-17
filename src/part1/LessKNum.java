package part1;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 22:11
 * @Description:
 */
public class LessKNum {
    /**
     * 基于快排切分点，O(n)
     *
     * @param input
     * @param k
     * @return
     * @throws Exception
     */
    ArrayList<Integer> lesskNum(int[] input, int k) throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k <= 0 || k > input.length)
            return list;

        int lo = 0;
        int hi = input.length - 1;
        int index = Utils.Partition(input, lo, hi);
        while (index != k - 1) {
            if (index < k - 1) {
                lo = index + 1;
                index = Utils.Partition(input, lo, hi);
            } else {
                hi = index - 1;
                index = Utils.Partition(input, lo, hi);
            }
        }
        for (int i = 0; i < k; i++)
            list.add(input[i]);
        return list;
    }

    /**
     * 最大堆；O(nlogk)；适合处理海量数据
     * 使用优先队列；最大堆保存这k个数，每次只和堆顶比，如果比堆顶小，删除堆顶，新数入堆。
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution1(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int length = input.length;
        if (k > length || k == 0) {
            return result;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Comparator.reverseOrder());//倒序
        for (int i = 0; i < length; i++) {
            if (maxHeap.size() != k) {
                maxHeap.offer(input[i]);
            } else if (maxHeap.peek() > input[i]) {
                Integer temp = maxHeap.poll();
                temp = null;
                maxHeap.offer(input[i]);
            }
        }
        result.addAll(maxHeap);
        return result;
    }

    /**
     * 最大堆，自己构建堆树
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution2(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        //检查输入的特殊情况
        if (input == null || input.length <= 0 || input.length < k) {
            return list;
        }
        //构建最大堆
        for (int len = k / 2 - 1; len >= 0; len--) {
            adjustMaxHeapSort(input, len, k - 1);
        }
        //从第k个元素开始分别与最大堆的最大值做比较，如果比最大值小，则替换并调整堆。
        //最终堆里的就是最小的K个数。
        int tmp;
        for (int i = k; i < input.length; i++) {
            if (input[i] < input[0]) {
                tmp = input[0];
                input[0] = input[i];
                input[i] = tmp;
                adjustMaxHeapSort(input, 0, k - 1);
            }
        }
        for (int j = 0; j < k; j++) {
            list.add(input[j]);
        }
        return list;
    }

    public void adjustMaxHeapSort(int[] input, int pos, int length) {
        int temp;
        int child;
        for (temp = input[pos]; 2 * pos + 1 <= length; pos = child) {
            child = 2 * pos + 1;
            if (child < length && input[child] < input[child + 1]) {
                child++;
            }
            if (input[child] > temp) {
                input[pos] = input[child];
            } else {
                break;
            }
        }
        input[pos] = temp;
    }

    /**
     * 冒泡排序的思想
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        if (k > input.length) {
            return al;
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < input.length - i - 1; j++) {
                if (input[j] < input[j + 1]) {
                    int temp = input[j];
                    input[j] = input[j + 1];
                    input[j + 1] = temp;
                }
            }
            al.add(input[input.length - i - 1]);
        }
        return al;
    }

    public static void main(String[] args) {
        int[] a = {4, 5, 1, 6, 2, 7, 3, 8};
        List list = null;
        try {
            list = new LessKNum().lesskNum(a, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);

    }
}
