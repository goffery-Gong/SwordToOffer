package IO;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/17 17:17
 * @Description: ，连接上服务端8000端口之后，每隔2秒，我们向服务端写一个带有时间戳的 "hello world"
 */
public class IOClient{
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        Thread.sleep(2000);
                    } catch (Exception e) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
