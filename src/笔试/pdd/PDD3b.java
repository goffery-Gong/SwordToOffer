package 笔试.pdd;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/18 16:43
 * @Description:
 */
public class PDD3b {
    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int d = sc.nextInt();

        Bank[] banks = new Bank[n];
        for (int i = 0; i <n ; i++) {
            banks[i]=new Bank();
            banks[i].dis=sc.nextInt();
            banks[i].money=sc.nextInt();
        }

        Arrays.sort(banks, Comparator.comparingInt(o -> o.dis));
        int max=-1;
        for(int i=n-1;i>=0;i--){
            if(banks[i].money>max){
                max=banks[i].money;
            }
            banks[i].max=max;
        }

        int maxMoney=-1;
        for(int i=0;banks[n-1].dis-banks[i].dis>=d;i++){
            //二分查找找到相距距离大于等于d的最左元素
            int j=binarySearch(banks,new Bank(banks[i].dis+d),i+1,n-1, Comparator.comparingInt(o -> o.dis),false);
            //如果未找到等值元素的情况下进行处理
            if(j<0){
                j=-j-1;
            }
            if(banks[i].money+banks[j].max>maxMoney)
                maxMoney=banks[i].money+banks[j].max;
        }

        System.out.println(maxMoney);
    }

	static class Bank{
	    int money;
	    int dis;
	    int max;
	    Bank(int dis){
	        this.dis=dis;
        }
        Bank(){};
    }

    /**
     * @param arr
     * @param key
     * @param low
     * @param high
     * @param c 需要显式传入比较器
     * @param rightMost 查找相同元素的最右的那个传入true，查找最左的那个传入false
     * @param <T>
     * @return 如果查到就正常返回值，未查到就返回的是-(i+1)，其中i是要查找的元素插入到数组中的位置
     */
    public static <T>int binarySearch(T[] arr, T key, int low, int high, Comparator<T> c, boolean rightMost){

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = c.compare(arr[mid],key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else{
                if (rightMost) {//寻找最右的元素
                    if(mid+1<=high&&c.compare(arr[mid+1],key)==0){
                        low=mid+1;
                        continue;
                    }
                    else
                        return mid;
                }else{//寻找最左的元素
                    if(mid-1>=low&&c.compare(arr[mid+1],key)==0){
                        high=mid-1;
                        continue;
                    }
                    else
                        return mid;
                }
            }
        }
        return -(low + 1);  // key not found.
    }
}
