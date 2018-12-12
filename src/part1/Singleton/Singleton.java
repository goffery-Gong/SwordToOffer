package part1.Singleton;

public class Singleton {

    private static Singleton instance;
    private Singleton(){}

    /**
     * 线程不安全的解法
     * @return
     */
    public static Singleton getSingleton(){
        if(instance==null){
            instance= new Singleton();
        }
        return instance;
    }

    public static synchronized Singleton getSingleton2(){
        if (instance==null){
            instance=new Singleton();
        }
        return instance;
    }

    public static void main(String[] args) {
        int i=0;
        while (i<100){
            new Thread(()-> System.out.println(getSingleton2())
            ).start();
            i++;
        }
    }
}
