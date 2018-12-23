package part1;

//本质上是一个进制转换问题
public class Excel2003 {
    /**
     * 输入字符串，输出行数
     * @param str
     * @return
     */
    public static int FindCol(String str) {
        int sum = 0;
        int exp = 0;
        char[] chars = str.toCharArray();
        for (int i = str.length() - 1; i >= 0; i--) {
            sum += (chars[i] - 'A' + 1) * Math.pow(26, exp);
            exp++;
        }
        return sum;
    }

    /**
     * 输入行数，输出代表的字符串
     * @param num
     * @return
     */
    public static String FindNum(int num){
        int count=Count(num);
        char[] result=new char[count];

        for (int i = count-1; i >= 0; i--) {
            int temp=num%26;
            result[i]=(char)('A'+temp-1);
            num/=26;
        }
        return new String(result); //
    }

    /**
     * 统计结果的字符串长度
     * @param num
     * @return
     */
    private static int Count(int num) {
        int count=0;
        while(num!=0){
            num/=26;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        /*Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            System.out.println(Find(str));
        }*/
        System.out.println(FindNum(1128));
    }
}
