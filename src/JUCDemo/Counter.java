package JUCDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/13 15:11
 * @Description: 循环cas实现原子操作
 */
public class Counter {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> list = new ArrayList<>(600);
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            list.add(t);
        }

        for (Thread t : list)
            t.start();

        for (Thread t : list) {
            try {
                t.join();//等待这个线程死亡
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicInteger);
    }

    /**
     * 使用cas实现线程安全计数器
     */
    private void safeCount() {
        while (true) {
            int i = atomicInteger.get();//获取预期值
            boolean success = atomicInteger.compareAndSet(i, ++i);//（expect，update）如果当前值==expect，则将该值设定为update
            if (success)
                break;

        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }
}
