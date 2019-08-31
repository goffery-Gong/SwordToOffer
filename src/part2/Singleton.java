package part2;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/18 10:07
 * @Description:
 */
public class Singleton {
    //DCL
    private static volatile Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null)
                    instance=new Singleton();
            }
        }
        return instance;
    }

    /**
     * 静态内部类。jvm规定，在主动引用类中的静态字段时，要对类进行初始化（延迟初始化）。
     */
    private static class InstanceHolder{
        private static final Singleton INSTANCE=new Singleton();
    }
    public static Singleton getInstance2(){
        return InstanceHolder.INSTANCE;
    }

    enum Singleton2{
        InSTANCE
    }
}
