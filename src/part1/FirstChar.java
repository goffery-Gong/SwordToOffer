package part1;

import java.util.HashMap;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/17 21:22
 * @Description:
 */
public class FirstChar {
    //时间效率：O(n),空间效率O(1)
    char firstChar(String str) {
        if (str == null)
            return '\0';
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!map.containsKey(chr))
                map.put(chr, 1);
            else {
                int value = map.get(chr);
                map.put(chr, value + 1);
            }
        }

        for (int i = 0; i < str.length(); i++) {
            if(map.get(str.charAt(i))==1)
                return str.charAt(i);
        }
        return '\0';
    }

    //去掉重复的字符
    String isMoreThanOne(String str){
        HashMap<Character, Boolean> map=new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!map.containsKey(chr))
                map.put(chr, false);
            else {
                map.put(chr, true);
            }
        }

        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if(!map.get(str.charAt(i)))
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }


    public int FirstNotRepeatingChar(String str) {
        if (str == null)
            return -1;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (!map.containsKey(chr))
                map.put(chr, 1);
            else {
                int value = map.get(chr);
                map.put(chr, value + 1);
            }
        }

        for (int i = 0; i < str.length(); i++) {
            if(map.get(str.charAt(i))==1)
                return i;
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(new FirstChar().firstChar("nanfsfds"));
        System.out.println(new FirstChar().isMoreThanOne("googlee"));
    }
}
