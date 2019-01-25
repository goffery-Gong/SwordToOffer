package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 10:00
 * @Description: https://www.jianshu.com/p/80e1841909b7
 */
public class GetTranslationCount {
    static int getTranslationCount(int number) {
        if (number < 0)
            return 0;
        /*if (number == 1)
            return 1;*/

        return getTanslationCount(Integer.toString(number));
    }

    private static int getTanslationCount(String number) {
        int f1 = 0;
        int f2 = 1;
        int g;
        int temp;
        //动态规划，从右到左计算。
        // 转移矩阵: f(r-2) = f(r-1)+g(r-2,r-1)*f(r);
        //  如果r-2，r-1能够翻译成字符，则g(r-2,r-1)=1，否则为0
        for (int i = number.length() - 2; i >= 0; i--) {
            int num = Integer.parseInt(number.charAt(i) + "" + number.charAt(i + 1));
            if (num < 26 && num >= 10)
                g = 1;
            else
                g = 0;

            temp = f2;
            f2 = f2 + g * f1;
            f1 = temp;
        }
        return f2;
    }

    public static void main(String[] args) {
        System.out.println(getTranslationCount(9));  //0
        System.out.println(getTranslationCount(1234));  //3
        System.out.println(getTranslationCount(12258)); //5
    }
}
