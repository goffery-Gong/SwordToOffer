package part2;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/18 13:52
 * @Description:
 */
public class HeapSort {
    public void heapSort(Integer[] array){
        //构建最大堆，然后将排好序的元素再放到array中
        PriorityQueue<Integer> queue=new PriorityQueue<>(Comparator.reverseOrder());
        Collections.addAll(queue, array);
        for (int i = 0; i < array.length; i++)
            array[i]=queue.poll();

        //sink
        int length = array.length;
        while(length>1){
            exch(array,1,length--);
            sink(array,1,length);
        }
    }
     static void exch(Comparable[] array, int i, int j) {
        Comparable temp = array[i-1];
        array[i-1] = array[j-1];
        array[j-1] = temp;
    }
    private static void sink(Comparable[] pq, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j, j+1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i-1].compareTo(pq[j-1]) < 0;
    }
}
