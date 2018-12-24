package part1;
/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/24 13:57
 * @Description:
 */
public class Power {
    public static double Power(double base, int exp){
        if(exp==0) return 1;
        if(exp==1) return base;

        double result=Power(base, exp>>>1);
        result*=result;
        if((exp & 1)==1)
            result*=base;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Power(2,-2));
        /*double startTime=System.currentTimeMillis();
        for (int i = 0; i <10000; i++) {
            Power(2,-2);
        }
        double endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime);*/
    }
}
