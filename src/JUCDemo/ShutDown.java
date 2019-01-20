package JUCDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/18 16:22
 * @Description:
 */
public class ShutDown {
    public static void main(String[] args) {
        Thread countThread = new Thread(new Runner(), "countThread");
        countThread.start();
        SleepUtils.second(1);
        countThread.interrupt();

        Runner two=new Runner();
        countThread=new Thread(two, "two");
        countThread.start();
        SleepUtils.second(1);
        two.cancle();
    }

    private static class Runner implements Runnable {
        private volatile boolean on = true;
        private int i;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted())
                i++;
            System.out.println(i);
        }

        public void cancle(){
            on=false;
        }
    }
}
