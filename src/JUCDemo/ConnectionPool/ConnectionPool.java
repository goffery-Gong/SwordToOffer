package JUCDemo.ConnectionPool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 21:12
 * @Description:
 */
public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    ConnectionPool(int initialSize) {
        if (initialSize > 0)
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(ConnectionDiver.createConection());
            }
    }

    /**
     * 释放链接
     * @param connection
     */
    void releaseConnection(Connection connection) {
        if (connection != null)
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();//链接释放后进行通知，其他消费者会感知到连接池中已经归还了一个连接
            }
    }

    /**
     * 获取连接
     *
     * @param mills
     * @return mills内不能获取连接，返回null
     */
    Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            //完全超时,直接返回一个连接
            if (mills <= 0) {
                while (pool.isEmpty())
                    pool.wait();
                return pool.removeFirst();
            } else {
                long future = mills + System.currentTimeMillis();
                long remainin = mills;
                //当remaining大于0(没有超时)，并且pool为空（超时等待模式）
                while (pool.isEmpty() && remainin > 0) {
                    pool.wait(remainin);
                    remainin = future - System.currentTimeMillis();
                }
                Connection result = null;//mills内不能获取连接（pool为空），就返回null
                if (!pool.isEmpty())
                    result = pool.removeFirst();
                return result;
            }
        }
    }

}
