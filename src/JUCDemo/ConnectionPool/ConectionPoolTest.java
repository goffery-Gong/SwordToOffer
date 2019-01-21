package JUCDemo.ConnectionPool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 21:45
 * @Description:
 */
public class ConectionPoolTest {
    static ConnectionPool pool=new ConnectionPool(10);
    static CountDownLatch start=new CountDownLatch(1);//使用countdownLatch来确保thread能够同时开始执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        int threadCount=15;
        end=new CountDownLatch(threadCount);
        int count=20;
        AtomicInteger got=new AtomicInteger();
        AtomicInteger notGot=new AtomicInteger();

        for (int i = 0; i < threadCount; i++) {
            Thread thread=new Thread(new ConnectionRunner(count,got,notGot),"ConnectionThread");
            thread.start();
        }
        //????
        start.countDown();
        end.await();
        System.out.println("total invoke: " +(threadCount * count));
        System.out.println("got connection "+got);
        System.out.println("notGot connection "+notGot);
    }

    private static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count=count;
            this.got=got;
            this.notGot=notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(count>0){
                try {
                    Connection connection = pool.fetchConnection(100);
                    if(connection!=null) {
                        try {
                            connection.createStatement();//创建一个 Statement对象，用于将SQL语句发送到数据库。
                            connection.commit(); //使自上次提交/回滚以来所做的所有更改都将永久性，并释放此 Connection对象当前持有的任何数据库锁。
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    }else
                        notGot.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    count--;
                }
            }
            end.countDown();
        }
    }
}
