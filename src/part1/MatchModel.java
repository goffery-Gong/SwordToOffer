package part1;

public class MatchModel {

    public boolean match(String str, String model) {
        boolean flag = true;
        if (str == null || model == null) {
            return false;
        }
        return matchModel(str, 0, model, 0);
    }

    /**
     * 匹配:'*'——前面字符出现任意次数；'.'——任意字符
     * 如果模式匹配字符的下一个字符是‘*’:
        * 如果pttern当前字符和str的当前字符匹配，：有以下三种可能情况
             * （1）pttern当前字符能匹配 str 中的 0 个字符：match(str, pattern+2)
             * （2）pttern当前字符能匹配 str 中的 1 个字符：match(str+1, pattern+2)
             * （3）pttern当前字符能匹配 str 中的 多 个字符：match(str+1, pattern)
        * 如果pttern当前字符和和str的当前字符不匹配
            * pttern当前字符能匹配 str 中的 0 个字符：(str, pattern+2)
     * 如果模式匹配字符的下一个字符不是‘*’，进行逐字符匹配。
        * 对于 ‘.’ 的情况比较简单，’.’ 和一个字符匹配 match(str+1, pattern+1)
     *
     * 另外需要注意的是：空字符串"" 和 ".*" 是匹配的
     *
     * @param str
     * @param model
     * @return
     */
    public boolean matchModel(String str, int i, String model, int j) {

        if (i >= str.length() && j >= model.length()) //都结束
            return true;
        if (i < str.length() && j >= model.length()) //str没结束，model结束了
            return false;

        //下一位是'*'
        if (j + 1 < model.length() && model.charAt(j + 1) == '*') {
            //字符串完了
            if (i >= str.length())
                return matchModel(str, i, model, j + 2);

             //当前位相同，则有三种情况是匹配的
            if (str.charAt(i) == model.charAt(j) || model.charAt(j) == '.') {
                return matchModel(str, i, model, j + 2)
                        || matchModel(str, i + 1, model, j + 2)
                        || matchModel(str, i + 1, model, j);
            } else //当前位不相同
                return matchModel(str, i, model, j + 2);
        }
        //下一位不是"*"
        else if(i>=str.length())
            return false;
        else if (str.charAt(i) == model.charAt(j) || model.charAt(j) == '.')
            return matchModel(str, i + 1, model, j + 1);
        return false;
    }

    public static void main(String[] args) {
        boolean flag = new MatchModel().match("", ".*");
        System.out.println(flag);

        char[] chars = "aa".toCharArray();
//        System.out.println(new MatchModel().matchCore(chars, 0, "a*a".toCharArray(), 0));
    }
}
