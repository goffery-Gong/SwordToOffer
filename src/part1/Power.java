package part1;
/**
 * @Auther: gongzhiwei6
 * @Date: 2018/12/24 13:57
 * @Description:
 */
public class Power {
    boolean invaliInput=false;

    /**
     * 时间O(logn)
     * @param base
     * @param exp
     * @return
     */
     double power(double base, int exp){
        //考虑底数为0且指数小于0情况，用invalidInput变量来标记错误输入
        if(base==0 && exp<0) {
            invaliInput=true;
            return 0.0;
        }

        int absExp=exp;
        if(exp<0)
            absExp=-exp;

        double result=powerWithUnsignedExp(base, absExp);
        if(exp<0)
            result=1.0/result;
        return result;
    }

    private double powerWithUnsignedExp(double base, int exp){
        if(exp==0) return 1;
        if(exp==1) return base;

        double result=powerWithUnsignedExp(base, exp>>>1);
        result*=result;
        if((exp & 1)==1)
            result*=base;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new Power().power(-2,-3));
    }
}
