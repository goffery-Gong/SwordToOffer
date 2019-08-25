package 笔试.Huawei1;

import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/22 10:14
 * @Description:
 */
public class Huawei2 {
    public static void main(String[] args) {
        //先统计每个字母的个数
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int[] timesOfChar = new int[58];
        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'A';
            timesOfChar[index]++;
        }
        //对大写字母和小写的取小，用数组标志其出现的次数
        int[] couple = new int[26];
        for (int i = 0; i < 26; i++) {
            couple[i] = Math.min(timesOfChar[i], timesOfChar[i + 32]);
        }
        //遍历上面的数组，如果没有则输出
        int index = -1;
        for (int i = 0; i < 26; i++) {
            if (couple[i] != 0) {
                index = i;//找到了第一个出现个数不是0的字母
                break;
            }
        }
        if (index == -1)
            System.out.println("not found");

        //否则，遍历进行输出，直到数组为0
        while (index != -1) {
            couple[index]--;
            StringBuilder sb = new StringBuilder("" + (char) (index + 'A') + "" + (char) (index + 'a'));
            //从当前的字母开始往后遍历寻找蛇形字符串
            while (++index < couple.length && couple[index] != 0) {
                sb.append("" + (char) (index + 'A') + "" + (char) (index + 'a'));
                couple[index]--;
            }
            System.out.println(sb.toString());
            index = -1;
            for (int i = 0; i < 26; i++) {
                if (couple[i] != 0) {
                    index = i;//找到了第一个出现个数不是0的字母
                    break;
                }
            }
        }
    }
}
