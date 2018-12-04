package part1;

public class FindMin {
    /**
     *
     * @param a
     * @return
     */
    public int FindmMinNum(int[] a){
		int i=0;
		int j=a.length-1;
		int mid=i;

		while(a[i] >=a [j]){
			if(j-i==1){
				mid=j;
				break;
			}
			mid=(i+j)/2;

			if(a[i] == a[j] && a[i]==a[mid])
				return MinInOrder(a, i, j);
			if(a[mid]>=a[i])
				i=mid;
			else if(a[mid] <=a[j])
				j=mid;
		}
		return a[mid];
	}

	private int MinInOrder(int[] a, int i, int j){
		int result=a[i];
		for(int k=i+1; k<=j; k++){
			if(a[k]<result)
				result=a[k];
		}
		return  result;
	}


	public static void main(String[] args) {
		int[] a={3,4,5,1,2};
//		System.out.println( new FindMin().FindmMinNum(a));
//		System.out.println(new FindMin().Fibonacci(3));
	}
}
