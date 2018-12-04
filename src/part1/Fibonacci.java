package part1;

public class Fibonacci {
    public int FibonacciMethod(int n) {
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
}
