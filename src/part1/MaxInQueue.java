package part1;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/8 15:53
 * @Description:
 */
public class MaxInQueue {

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        if (num == null || num.length == 0 || size <= 0 || num.length < size) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> result = new ArrayList<>();
        //双端队列，用来记录每个窗口的最大值下标
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int i = 0; i < num.length; i++) {
            //新增加的值从队尾开始比较，把所有比他小的值丢掉；然后将新值加到队列尾
            while (!qmax.isEmpty() && num[qmax.peekLast()] < num[i])
                qmax.pollLast();

            qmax.addLast(i);
            //判断队首元素是否过期
            if (qmax.peekFirst() == i - size)
                qmax.pollFirst();

            //向result列表中加入元素
            if (i >= size - 1)
                result.add(num[qmax.peekFirst()]);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a={2,3,4,2,6,2,5,1};
        System.out.println(new MaxInQueue().maxInWindows(a,3));
    }
}
