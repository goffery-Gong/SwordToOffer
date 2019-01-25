package JUCDemo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/25 15:40
 * @Description:
 */
public class FairAndUnfairTest {
    private ReentrantLock2 fair = new ReentrantLock2(true);
    private ReentrantLock2 unfair = new ReentrantLock2(false);

    @Test
    public void fair() {
        fairAndUnfairTest(fair);
    }

    @Test
    public void unfair() {
        fairAndUnfairTest(unfair);
    }

    //测试函数：启动5个线程来打印加锁线程和等待线程；展现fair锁的FIFO性质
    private void fairAndUnfairTest(ReentrantLock2 fair) {
        for (int i = 0; i < 5; i++) {
            Job job = new Job(fair);
            job.setName(String.valueOf(i));
            job.start();
        }
    }

    //锁类
    private class ReentrantLock2 extends ReentrantLock {
        ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> list = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(list);
            return list;
        }

    }

    //线程类
    private class Job extends Thread {
        private ReentrantLock2 lock;

        Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Lock By[" + Thread.currentThread() + "], Waiting by " + lock.getQueuedThreads());

                System.out.println("Lock By[" + Thread.currentThread() + "], Waiting by " + lock.getQueuedThreads());
            } finally {
                lock.unlock();
            }
        }

        @Override
        public String toString() {
            return super.getName();
        }
    }
}
