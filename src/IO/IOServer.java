package IO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/17 17:16
 * @Description:
 */
public class IOServer {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8000);
        ExecutorService pool = Executors.newFixedThreadPool(2);
        while (true) {
            // (1) 阻塞方法获取新的连接
            Socket socket = serverSocket.accept();
            // (2) 每一个新的连接都创建一个线程，负责读取数据
            pool.submit(new SocketHandler(socket));
        }
    }
}

class SocketHandler implements Runnable {
    Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            byte[] data = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int len;
                // (3) 按字节流方式读取数据
                while ((len = inputStream.read(data)) != -1) {
                    System.out.println(new String(data, 0, len));
                }
            }
        } catch (IOException e) {}
    }
}

