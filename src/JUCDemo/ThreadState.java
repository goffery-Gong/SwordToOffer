package JUCDemo;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/18 15:12
 * @Description:
 */
public class ThreadState {
    public static void main(String[] args) {
        //TimeWaitingThread
        new Thread(() -> {
            while (true)
                SleepUtils.second(100);
        }, "TimeWaitingThread").start();

        //WaitingThread
        new Thread(new Waiting(), "WaitingThread").start();

        //BlockedThread
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }

    private static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true)
                SleepUtils.second(100);
        }
    }

    private static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true)
                    SleepUtils.second(100);
            }
        }
    }
}
