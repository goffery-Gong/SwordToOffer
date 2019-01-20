package part1;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 15:49
 * @Description: 通过最大堆，最小堆实现找到数据流的中位数
 */
public class MidNumInFlow {
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
    private int count;

    public void Insert(Integer num) {
        //count为奇数，进入最大堆，count变为偶数
        if ((count & 1) == 1) {
            minHeap.offer(num);
            int filterMinNum = minHeap.poll();
            maxHeap.offer(filterMinNum);
        } else {//count为偶数，进入最小堆，count变为奇数
            maxHeap.offer(num);
            int filterMaxNum = maxHeap.poll();
            minHeap.offer(filterMaxNum);
        }
        count++;
    }

    Double GetMedian() {
        if ((count & 1) == 0)
            return (double) ((minHeap.peek() + maxHeap.peek())) / 2;
        else
            return (double) (minHeap.peek());
    }

    public static void main(String[] args) {
        MidNumInFlow midNumInFlow = new MidNumInFlow();
        midNumInFlow.Insert(5);
        System.out.println(midNumInFlow.GetMedian());
        midNumInFlow.Insert(2);
        System.out.println(midNumInFlow.GetMedian());
    }
}
