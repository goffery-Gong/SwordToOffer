package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/24 22:06
 * @Description:
 */
public class LongestSubstringWithOutDup {
    //
    int longestSubstringWithOutDup(String str) {
        if (str == null)
            return 0;

        int tempLength = 0;
        int maxLength = 0;
        int[] position = new int[26];
        for (int i = 0; i < position.length; i++)
            position[i] = -1;

        for (int i = 0; i < str.length(); i++) {
            int preAppearIndext = position[str.charAt(i) - 'a'];
            int d = i - preAppearIndext;
            if (preAppearIndext < 0 || d > tempLength)//情况1,3
                tempLength++;
            else
                tempLength=d;  //情况2

            if(tempLength>maxLength)
                maxLength=tempLength;
            position[str.charAt(i) - 'a']=i; //记录字符出现位置为i
        }
//        if(tempLength>maxLength)
//            maxLength=tempLength; ????
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(new LongestSubstringWithOutDup().longestSubstringWithOutDup("arabcacf"));
    }
}
