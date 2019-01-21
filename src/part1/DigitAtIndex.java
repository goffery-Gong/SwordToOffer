package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/21 09:56
 * @Description:
 */
public class DigitAtIndex {
    int digitAtIndex(int n) {
        int temp = 1;//一个数字的位数
        int num = 0; //数字
        int tempNum;
        int result = 0;

        while (temp < n) {
            tempNum = num;
            while (num != 0) {
                temp++;
                num = num / 10;
            }
            num = tempNum;
            if (temp < n)
                num++;
        }
        //数字位一定在num中
        return num;
    }

    /**
     * 方法2
     * @param n
     * @return
     */
    int digitAtIndex2(int n) {
        if (n < 0)
            return -1;
        int digit = 1;
        while (true) {
            int numbers = countOfInteger(digit);
            if (n < numbers * digit)
                return digitAtIndex(n, digit);
            n -= numbers * digit;
            digit++;
        }
    }

    //digit位的数字数量（如2位数有90个）
    private int countOfInteger(int digit) {
        if (digit == 1)
            return 10;
        return (int) (Math.pow(10, digit) - Math.pow(10, digit - 1));
    }

    //结果
    private int digitAtIndex(int n, int digit) {
        int num = n / digit + beginNum(digit);
        int indexFromRight = digit - n % digit;
        for (int i = 1; i < indexFromRight; i++)
            num /= 10;
        return num % 10;
    }

    private int beginNum(int digit) {
        if (digit == 1)
            return 0;
        return (int) Math.pow(10, digit - 1);
    }

    public static void main(String[] args) {
        DigitAtIndex digitAtIndex = new DigitAtIndex();
        System.out.println(digitAtIndex.countOfInteger(3));
        System.out.println(digitAtIndex.digitAtIndex(1001));
        System.out.println(digitAtIndex.digitAtIndex2(15));
    }
}
