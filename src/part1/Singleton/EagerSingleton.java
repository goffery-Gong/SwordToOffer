package part1.Singleton;

public class EagerSingleton {
    //通过静态变量初始化类实例
    private static EagerSingleton instance=new EagerSingleton();

    private EagerSingleton(){}

    //获取唯一实例的静态工厂方法
    public static EagerSingleton getInstance(){
        return instance;
    }
}
