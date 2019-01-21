package part1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/21 15:56
 * @Description:
 */
public class PrintMinNumber {
    List<String> list = new ArrayList<>();

    /**
     * 常规方法，全排列然后找最小
     *
     * @param numbers
     * @return
     */
    String printMinNumber(int[] numbers) {
        if (Utils.isNullorZero(numbers))
            return null;
        allNum(numbers, 0);
        return leastInList(list);
    }

    //找出全排列list中的最小数字
    private String leastInList(List<String> list) {
        long leastNum = Long.valueOf(list.get(0));//可能会超出大小
        long num;
        for (String str : list) {
            num = Long.valueOf(str);
            if (num < leastNum)
                leastNum = num;
        }
        return String.valueOf(leastNum);
    }

    //数组中元素的全排列
    private void allNum(int[] array, int i) {
        if (i >= array.length)
            return;

        for (int j = i; j < array.length; j++) {
            if (!list.contains(toString(array)))
                list.add(toString(array));
            int[] newArray = exchAndBuild(array, i, j);
            allNum(newArray, i + 1);
        }
    }

    private String toString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i : array) {
            sb.append(i);
        }
        return sb.toString();
    }

    private int[] exchAndBuild(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return Arrays.copyOf(array, array.length);
    }


    /**
     * 自定义排序大小
     * 定义一个比较大小的函数，比较两个字符串s1, s2大小的时候，先将它们拼接起来，比较s1+s2,和s2+s1那个大，
     * 如果s1+s2大，那说明s2应该放前面，所以按这个规则，s2就应该排在s1前面
     * 比如 "3" < "31"但是 "331" > "313"，所以要将二者拼接起来进行比较
     * @param
     */
    String printMinNumber2(int[] numbers) {
        String str = "";
        if (Utils.isNullorZero(numbers)) {
            return str;
        }

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                long a = Long.valueOf(numbers[i] + "" + numbers[j]);
                long b = Long.valueOf(numbers[j] + "" + numbers[i]);
                if (a > b) {
                    int temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        for (int j = 0; j < numbers.length; j++)
            str += String.valueOf(numbers[j]);

        return str;
    }

    /**
     * 自定义排序2，通过使用string来避免大数问题
     * @param numbers
     * @return
     */
    String printMinNumber3(int[] numbers){
        StringBuilder sb=new StringBuilder();
         if (Utils.isNullorZero(numbers)) {
            return sb.toString();
        }

        int length=numbers.length;
        String[] strs=new String[length];
        //转为字符串数组
        for (int i = 0; i < length; i++)
            strs[i]=String.valueOf(numbers[i]);

        //将strs数组按照定义的comparator从小到大排序
        Arrays.sort(strs,(String s1,String s2)->{
            String c1=s1+s2;
            String c2=s2+s1;
            return c1.compareTo(c2);
        });

        for (int i = 0; i < length; i++)
            sb.append(strs[i]);

        return sb.toString();
    }

    public static void main(String[] args) {
        int[] a = {3334, 3, 3333332};
        int[] b = {3, 2, 1};
        String list = new PrintMinNumber().printMinNumber3(b);
        System.out.println(list);
//        System.out.println(Integer.valueOf(b[0]+""+b[1]));
    }
}
