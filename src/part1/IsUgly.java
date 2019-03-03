package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/17 19:51
 * @Description:
 */
public class IsUgly {
    boolean isUgly(int number) {
        while (number % 2 == 0)
            number /= 2;
        while (number % 3 == 0)
            number /= 3;
        while (number % 5 == 0)
            number /= 5;

        return number == 1;
    }

    //方法1，遍历
    int getUglyNumber1(int index) {
        if (index <= 0)
            return 0;

        int number = 0;
        int uglyFound = 0;
        while (uglyFound < index) {
            number++;
            if (isUgly(number))
                uglyFound++;
        }
        return number;
    }

    //方法2，空间换时间
    int getUglyNumber2(int index) {
        if (index <=0)
            return 0;

        int[] uglyNumbers = new int[index];
        uglyNumbers[0] = 1;
        int t2 = 0;
        int t3 = 0;
        int t5 = 0;

        int nextIndex = 1;

        while (nextIndex < index) {
            uglyNumbers[nextIndex] = Min(uglyNumbers[t2] * 2, uglyNumbers[t3] * 3, uglyNumbers[t5] * 5);
            while (uglyNumbers[t2] * 2 <= uglyNumbers[nextIndex])
                t2++;
            while (uglyNumbers[t3] * 3 <= uglyNumbers[nextIndex])
                t3++;
            while (uglyNumbers[t5] * 5 <= uglyNumbers[nextIndex])
                t5++;
            nextIndex++;
        }
        return uglyNumbers[nextIndex-1];
    }

    private int Min(int num1, int num2, int num3) {
        int min=(num1<num2)?num1:num2;
        return (min<num3)?min:num3;
    }

    public static void main(String[] args) {
        System.out.println( new IsUgly().getUglyNumber2(10));
    }
}
