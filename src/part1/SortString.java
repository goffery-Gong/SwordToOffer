package part1;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 10:56
 * @Description:
 */
public class SortString {
    List<String> list = new ArrayList<>();

    /**
     * 递归思路
     *
     * @param str
     * @return
     */
    List<String> sortString(String str) {
        if (str == null || str.length() == 0)
            return list;
        sortString(str, 0);
        Collections.sort(list);
        return list;
    }

    private void sortString(String str, int i) {
        if (i >= str.length())
            return;

        for (int j = i; j < str.length(); j++) {
            if (!list.contains(str))
                list.add(str);

            /*//交换，构造新的串

            String newStr = exchAndBuild(str, i, j);

            //新的串进行递归
            sortString(newStr, i + 1);*/

            if (i != j)
                sortString(exchAndBuild(str, i, j), i + 1);
        }
    }

    private String exchAndBuild(String str, int i, int j) {
        char[] chars = str.toCharArray();
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
        return new String(chars);
    }

    /**
     * 回溯法
     *
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        List<String> resultList = new ArrayList<>();
        if (str.length() == 0)
            return (ArrayList) resultList;
        //递归的初始值为（str数组，空的list，初始下标0）
        fun(str.toCharArray(), resultList, 0);
        Collections.sort(resultList);
        return (ArrayList) resultList;
    }

    private void fun(char[] ch, List<String> list, int i) {
        //这是递归的终止条件，就是i下标已经移到char数组的末尾的时候，考虑添加这一组字符串进入结果集中
        if (i == ch.length - 1) {
            //判断一下是否重复
            if (!list.contains(new String(ch))) {
                list.add(new String(ch));
                return;
            }
        } else
            for (int j = i; j < ch.length; j++) {
                swap(ch, i, j);
                fun(ch, list, i + 1);
                swap(ch, i, j);
            }
    }

    //交换数组的两个下标的元素
    private void swap(char[] str, int i, int j) {
        if (i != j) {
            char t = str[i];
            str[i] = str[j];
            str[j] = t;
        }
    }

    public static void main(String[] args) {
        System.out.println(new SortString().sortString("abc"));
//        System.out.println(new SortString().Permutation("abc"));
    }
}
