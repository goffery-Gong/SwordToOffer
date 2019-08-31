package job.otherAlgrithm;

/**
 * @author LingLong.gzw
 * @create 2019-08-31
 */
public class Singleton {
    private static class SingleInstanceHolder{
        private static final Singleton INSTANCE=new Singleton();
    }
    public Singleton(){}

    public static Singleton getInstance(){
        return SingleInstanceHolder.INSTANCE;
    }
}
