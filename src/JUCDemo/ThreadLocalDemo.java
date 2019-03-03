package JUCDemo;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/24 16:12
 * @Description:
 */
public class ThreadLocalDemo {
    private  static A a=new A();
    private static ThreadLocal<Integer> threadLocal= ThreadLocal.withInitial(() -> 0);
    private static ThreadLocal<A> threadLocal2= ThreadLocal.withInitial(() -> a);

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads=new Thread[3];
        for (int i = 0; i < 3; i++) {
            threads[i]=new Thread(()->{
                for (int j = 0; j < 3; j++) {
                    threadLocal.set(threadLocal.get()+1);
                    System.out.println(Thread.currentThread().getName()+":" +threadLocal.get());
                }
            });
        }

        for (Thread t: threads) {
            t.start();
        }

        for (Thread t: threads) {
            t.sleep(200);
        }
        System.out.println("--------------------------------------");

        for (int i = 0; i < 3; i++) {
            threads[i]=new Thread(()->{
                for (int j = 0; j < 3; j++) {
                    threadLocal2.get().setNumber(threadLocal2.get().getNumber()+5);
                    System.out.println(Thread.currentThread().getName()+":" +threadLocal2.get().getNumber());
                }
            },"thread2-"+i);
        }

        for (Thread t: threads) {
            t.start();
        }

        /*String s1=new StringBuilder("110").toString().intern();
        String s2="110";
        System.out.println(s1==s2);*/
    }
}
class A{
    private int number=0;
    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number=number;
    }
}