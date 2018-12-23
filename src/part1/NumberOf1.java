package part1;

public class NumberOf1 {
    /**
     * 当输入为负数，可能出现死循环（>>>可以避免）
     * 通过右移num实现
     *
     * @param num
     * @return
     */
    public static int numberOf1(int num) {
        int sum = 0;
        int flag = 1;

        while (num != 0) {
            //不能将if 的判断条件写成 bits % 2 == 1 来判断当前位是否为 1，因为负数取余为负
            if ((num & 1) == 1) //按位与：num & 1表示num的最后一位与1进行位运算
                sum++;
            num >>>= 1; //这里使用>>>可以避免陷入死循环，如果是>>就不行。
        }
        return sum;
    }

    /**
     * 通过左移flag(1)实现
     *
     * @param num
     * @return
     */
    public static int numberOfOne2(int num) {
        int sum = 0;
        int flag = 1;

        while (flag != 0) {
            //按位与;-5的补码为11111111111111111111111111111101，和flag进行&
            if ((num & flag) == flag)
                sum++;
            flag = flag << 1;
        }
        return sum;
    }

    public static int numberOfOne3(int num) {
        //num &(num-1)
        int count = 0;
        while (num != 0) {
            count++;
            num = num & (num - 1);
        }
        return count;
    }

    /**
     * 一个整数-1，然后与原整数进行&操作，会把最右边的1变为0.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(numberOfOne3(-3));
        System.out.println(Integer.toBinaryString(-3));
        System.out.println("1<<1: " + Integer.toBinaryString(1 << 1));
        System.out.println(-5 & (1 << 1));
    }
}
