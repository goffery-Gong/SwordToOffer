package part1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/16 16:32
 * @Description:
 */
public class CharConbine {
    private Set<String> resultSet;
    private int index;
    private StringBuilder sb=new StringBuilder();

    List<String> combine(char[] chars) {
        if (chars == null || chars.length == 0)
            return new ArrayList<>();

        resultSet = new TreeSet<>();
        for (int i = 1; i <= chars.length; i++)
            combine(chars, i);

        return new ArrayList<>(resultSet);
    }

    private void combine(char[] chars, int length) {
         if (length == 0) {
            resultSet.add(sb.toString());
            return;
        }
        if(chars.length-index<length)
            return;

        //选择第一个，从剩下chars中选择length-1个
        sb.append(chars[index]);
        index++;
        combine(chars,length-1);
        sb.deleteCharAt(sb.length()-1);

        //不选择第一个，从剩下chars中length个
        combine(chars,length);
        --index;
    }

    public static void main(String[] args) {
        System.out.println(new CharConbine().combine("abc".toCharArray()));
    }
}
