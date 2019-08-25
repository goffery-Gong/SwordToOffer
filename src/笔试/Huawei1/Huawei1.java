package 笔试.Huawei1;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/22 09:31
 * @Description:
 */
public class Huawei1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        if(s.length()==0)
            System.out.println("input is null");
        LinkedList<Integer> num = new LinkedList<>();
        LinkedList<Character> symbol = new LinkedList<>();

        int strLength = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '+' && c != '-') {
                strLength++;
                if (i+1 == s.length()|| s.charAt(i + 1) == '+' || s.charAt(i + 1) == '-') {
                    String substr=s.substring(i - strLength + 1, i+1);
                    num.offer(Integer.valueOf(substr));
                    strLength = 0;
                }
            } else
                symbol.offer(c);
        }
        int result =num.poll();
        while(!num.isEmpty()){
            if(symbol.peek()=='+'){
                result+=num.poll();
                symbol.poll();
            }
            else{
                result-=num.poll();
                symbol.poll();
            }
        }
        System.out.println(result);
    }
}
