package 笔试.pdd;

import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/15 21:12
 * @Description:
 * Given “bcabc”
 * Return “abc”
 *
 * Given “cbacdcbc”
 * Return “acdb”
 *
 * 就是去除重复的元素，但是要求所得到的字符串的字典序最小。最后返回str的第一个字符
 * 思路就是贪心+递归。
 */
public class PDD2 {
    public void solution() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        System.out.println(removeDuplicateLetters(str).charAt(0));

    }

    public String removeDuplicateLetters(String s) {
        if (s == null || s.length() <= 0)
            return "";
        else {
            int[] count = new int[26];
            //遍历，统计每个字符的出现次数
            for (int i = 0; i < s.length(); i++)
                count[s.charAt(i) - 'a']++;
            int pos = 0;
            //for循环的主要功能是找出当前s的最小字符
            // 同时要保证如果最小字符前没有重复的字符，则pos位指向不重复的那个字符，而不是最小的字符
            // （如：bca，中指向的是b，而不是a；bcabc中，指向的是a）
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) < s.charAt(pos))
                    pos = i;
                count[s.charAt(i) - 'a']--;
                if (count[s.charAt(i) - 'a'] == 0)
                    break;
            }
            //递归调用，找出子串中的下一个字符
            String res = s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replace(String.valueOf(s.charAt(pos)), ""));
            return res;
        }
    }
}
