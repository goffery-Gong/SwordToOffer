package part1;

public class Fibonacci {

	/**
	 * 循环 O(n)
	 * @param n
	 * @return
	 */
    public int FibonacciCycleMethod(int n) {
		int a = 1, b = 1;
		int sum = 0;
		if (n == 0)
			return 0;
		else if (n == 1 || n == 2)
			return 1;
		else {
			for (int i = 3; i <= n; i++) {
				sum = a + b;
				a = b;
				b = sum;
			}
			return sum;
		}
	}

	/**
	 * 递归 O(2^n)(https://www.nowcoder.com/questionTerminal/fd57dad14d224881a929d6739741fe50)
	 * @param n
	 * @return
	 */
	public static int FibonaciDiguiMethod(int n){
		if(n <= 0)
			return 0;
		if(n == 1)
			return 1;
		else
			return FibonaciDiguiMethod(n-1)+FibonaciDiguiMethod(n-2);
	}
}
