package JUCDemo;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/17 22:18
 * @Description:
 */
public class InstanceFactory {
      final int  aaa ;

      InstanceFactory(){
          aaa=0;
      }

    private static class InstanceHolder {

        public static Instacne instacne = new Instacne();

        public Instacne instace1 = new Instacne();

        public Instacne getInstance1() {
            return instace1;
        }
    }

    public static void main(String[] args) {
        Instacne instacne = new InstanceHolder().instace1;
        Instacne instacne1 = InstanceHolder.instacne;
    }

    /*public static Instacne getInstance(){

        return InstanceHolder.instacne;
    }*/

    private static class Instacne {
        static final int aadd=0;
        public  void aaa() {

        }
    }
}
