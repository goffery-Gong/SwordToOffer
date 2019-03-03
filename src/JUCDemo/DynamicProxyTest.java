package JUCDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/28 21:28
 * @Description:
 */
public class DynamicProxyTest {
    interface Ihellp{
        void sayHello();
    }

    static class Hello implements Ihellp{

        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

    static class DynamicProxy implements InvocationHandler{
        Object originalObj;

        Object bind(Object originalObj){
            this.originalObj=originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(),originalObj.getClass().getInterfaces(),this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originalObj,args);
        }
    }

    public static void main(String[] args) {
        Ihellp hello= (Ihellp) new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}
