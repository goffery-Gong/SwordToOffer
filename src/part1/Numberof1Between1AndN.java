package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/15 11:01
 * @Description:
 */
public class Numberof1Between1AndN {
    /**
     * 链接：https://www.nowcoder.com/questionTerminal/bd7f978302044eee894445e244c7eee6
     *
     * Go through the digit positions by using position multiplier m with values 1, 10, 100, 1000, etc.
     *
     * For each position, split the decimal representation into two parts,
     * for example split n=3141592 into a=31415 and b=92 when we're at m=100 for analyzing the hundreds-digit.
     * And then we know that the hundreds-digit of n is 1 for prefixes "" to "3141", i.e., 3142 times. Each of those times is a streak, though.
     * Because it's the hundreds-digit, each streak is 100 long. So (a / 10 + 1) * 100 times, the hundreds-digit is 1.
     *
     * Consider the thousands-digit, i.e., when m=1000. Then a=3141 and b=592.
     * The thousands-digit is 1 for prefixes "" to "314", so 315 times. And each time is a streak of 1000 numbers.
     * However, since the thousands-digit is a 1, the very last streak isn't 1000 numbers but only 593 numbers,
     * for the suffixes "000" to "592". So (a / 10 * 1000) + (b + 1) times, the thousands-digit is 1.
     *
     * The case distincton between the current digit/position being 0, 1 and >=2 can easily be done in one expression.
     * With (a + 8) / 10 you get the number of full streaks, and a % 10 == 1 tells you whether to add a partial streak.
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        int ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            int a = (int) (n / m), b = (int) (n % m);
            ones += (a + 8) / 10 * m + ((a % 10 == 1)? 1:0 )* (b + 1);
        }
        return ones;
    }
}