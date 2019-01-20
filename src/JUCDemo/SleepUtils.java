package JUCDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/18 15:16
 * @Description:
 */
public class SleepUtils {
    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
