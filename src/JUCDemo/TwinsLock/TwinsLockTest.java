package JUCDemo.TwinsLock;

import JUCDemo.SleepUtils;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/22 22:23
 * @Description:
 */
public class TwinsLockTest {
    @Test
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run(){
                while (true){
                    lock.lock();
                    try{
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    }finally {
                        lock.unlock();
                    }
                }
            }
        }
        //启动10个线程
        for (int i=0;i<10;i++){
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        //每隔1秒换行
        for(int i =0 ;i<10;i++){
            SleepUtils.second(1);
            System.out.println();
        }
    }
}
