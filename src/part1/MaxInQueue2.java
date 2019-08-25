package part1;

import java.util.LinkedList;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/12 15:08
 * @Description:
 */
public class MaxInQueue2 {
    class Data {
        int val;
        int index;

        private Data(int val, int index) {
            this.val =val;
            this.index=index;
        }
    }

    private LinkedList<Data> qmax=new LinkedList<>();
    private LinkedList<Data> dataList=new LinkedList<>();
    private int currentIndex;

    public void pushBack(int value) {
        while (!qmax.isEmpty() && qmax.peekLast().val < value)
            qmax.pollLast();
        Data data = new Data(value, currentIndex);
        qmax.addLast(data);
        dataList.addLast(data);
        currentIndex++;
    }

    public int popFirst() throws Exception {
        if (dataList.isEmpty())
            throw new Exception("dataQueue is empty..");
        if (dataList.peekFirst().index == qmax.peekFirst().index)
            qmax.pollFirst();
        return dataList.pollFirst().val;
    }

    public int max() throws Exception{
        if(qmax.isEmpty())
            throw new Exception("maxQueue is empty...");
        return qmax.peekFirst().val;
    }

    public static void main(String[] args) {
        MaxInQueue2 queue = new MaxInQueue2();
        queue.pushBack(4);
        queue.pushBack(2);
        queue.pushBack(3);
        try {
            System.out.println("current Max is: "+queue.max());
            System.out.println("first in data is: "+queue.popFirst());
            System.out.println("current Max is: "+queue.max());
            System.out.println("first in data is: "+queue.popFirst());
            System.out.println("first in data is: "+queue.popFirst());
            System.out.println("first in data is: "+queue.popFirst());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
