package part1;

public class Fibonacci {

	/**
	 * 循环 O(n)
	 * @param n
	 * @return
	 */
    public static int FibonacciCycleMethod(int n) {
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
	public static int fibonaciDiguiMethod(int n){
		if(n <= 0)
			return 0;
		if(n == 1)
			return 1;
		else
			return fibonaciDiguiMethod(n-1)+fibonaciDiguiMethod(n-2);
	}

	public static int finbonaciBestMethod(int n){
		if(n<0) return 0;
		if(n==1 || n==2) return 1;
		int[][] base={{1,1},{1,0}};
		return matrixCaculate(base,n-1)[0][0];
	}

	//计算矩阵的p次幂，返回结果矩阵
	private static int[][] matrixCaculate(int[][] matrix, int p) {
		if(p==0) return null;
		if(p==1) return matrix;
		int[][] res=matrixCaculate(matrix,p>>1);//时间复杂度为O(logn)的计算p次幂的算法
		res=mutiMatrix(res,res);
		if((p & 1)==1)  //返回1表示奇数，0表示偶数。
			mutiMatrix(res,matrix);
		return res;
	}

	//计算两个矩阵相乘得到一个新矩阵
	private static int[][] mutiMatrix(int[][] m1, int[][] m2) {
		int [][] res=new int[m1.length][m2[0].length];
		for(int i=0;i<m1.length;i++){		//i应为m1的行
			for(int j=0;j<m2[0].length;j++){//j应为m2的列
				for(int k=0;k<m2.length;k++){
					res[i][j]+=m1[i][k]*m2[k][j];
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(finbonaciBestMethod(4));
		System.out.println(FibonacciCycleMethod(4));
	}
}
