package part2;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/9 22:12
 * @Description:
 */
public class Tencent1 {
    public int Coin(int n,int m){
        if(m<=n)
            return 1;
        int result=0;
        while(m!=0){
            result+=m/n;
            m=m%n;
            n--;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Tencent1().Coin(4,10));
    }
}
