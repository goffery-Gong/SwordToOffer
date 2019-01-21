package JUCDemo.ConnectionPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 21:14
 * @Description: 利用代理可以在运行时创建一个实现了一组给定接口的新类；
 */
public class ConnectionDiver {

    public static final Connection createConection() {
        //试用指定的ConnectionHandler来生成一个动态代理对象
        return (Connection) Proxy.newProxyInstance(ConnectionDiver.class.getClassLoader(),new Class[]{Connection.class},new ConnectionHandler());
    }

    private static class ConnectionHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.getName().equals("commit"))
                TimeUnit.MILLISECONDS.sleep(100);
            return null;
        }
    }
}
