package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/20 19:23
 * @Description:
 */
public class NumberofOne {
    /**
     * 基本方法
     * @param n
     * @return
     */
    int numberofOne(int n) {
        int num = 0;
        for (int i = 1; i <= n; i++)
            num += numOfOne(i);
        return num;
    }

    private int numOfOne(int i) {
        int tempNum = 0;
        while (i != 0) {
            if (i % 10 == 1)
                tempNum++;
            i = i / 10;
        }

        return tempNum;
    }
}
