package part1;

public class IsNumeric {
    private int i;
    private int inx;

    public boolean isNumric(String num){
        if(num==null)
            return false;
        //判断整数部分
        boolean flag=scanInteger(num);

        //判断小数部分
        if(i<num.length() && (num.charAt(i)=='.')){
            i++;
            flag=flag | isUnsignedInteger(num); //注意运算符短路
        }
        //判断指数部分
        if(i<num.length() && (num.charAt(i)=='e' || num.charAt(i)=='E')){
            i++;
            flag=flag & scanInteger(num);
        }

        return flag && i>=num.length();
    }

    //判断 指数位/整数位为可带符号整数
    private boolean scanInteger(String num) {
        if(i<num.length() && (num.charAt(i)=='+' || num.charAt(i)=='-'))
            i++;
        return isUnsignedInteger(num);
    }

    //判断 小数位为无符号整数
    private boolean isUnsignedInteger(String num) {
        int before=i;
        while(i<num.length() && num.charAt(i)>='0' && num.charAt(i)<='9')
            i++;
        return i>before;
    }


    public static void main(String[] args) {
        System.out.println(new IsNumeric().isNumric("3.-12"));
    }
}
