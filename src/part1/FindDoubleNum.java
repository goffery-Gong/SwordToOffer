package part1;

import java.util.Arrays;
import java.util.HashMap;

public class FindDoubleNum {
    //排序后遍历。时间：O(nlogn)【排序】,空间O（1）
    public static int findDoubleNum(int[] a){
        if(a==null){
            return -1;
        }
        Arrays.sort(a);
        for(int i=0;i<a.length-2; i++){
            if(a[i]==a[i+1])
                return a[i];
        }
        return -1;
    }

    //利用哈希表查询时间为O(1)
    //时间：O(n),空间O(n)
    public static int[] findDoubleNumByHash(int[] a){
        int i=0;
        int[] reslut=new int[a.length];

        if(a==null)
            return reslut;

        HashMap<Integer,Integer> hashMap=new HashMap();
        for (int num : a) {
            if(hashMap.containsKey(num)) {
                reslut[i] = num;
                i++;
            }
            hashMap.put(num,0);
        }
        return reslut;
    }

    //利用遍历一次数组作交换实现。时间：O(n),空间O(1)
    // 两层循环，但是每个数字最多两次交换就能移动到正确位置（因为交换是以a[a[i]]进行的），所以时间复杂度为n
    public static boolean findDoubleBest(int[]a ,int[] result){
        if(a==null)
            return false;

        for(int k: a)
            if(k<0)
                return false;

        for(int i=0; i<a.length; i++){
            while (i!=a[i]) {
                if (a[i] == a[a[i]]) {
                    result[0] = a[i];
                    return true;
                }
                else
                    exchange(a, i, a[i]);
            }
        }
        return false;
    }

    private static void exchange(int[] a, int i, int j) {
        int temp=a[i];
        a[i]=a[j];
        a[j]=temp;
    }

    //时间：O(nlogn),空间O(1)
    //不改变数组，类二分法,不能保证找出所有重复数组
    public static int findDoubleBinary(int[] a){
        if(a==null)
            return -1;

        int lo=1;
        int hi=a.length-1;
        while (lo<=hi){
            int mid=(lo+hi)/2;
            int count=countNum(a,lo,mid);
            if(lo==hi) {
                if(count>1)
                    return lo;
                else    //需要吗
                    break;
            }
            if(count > (mid-lo+1) )
                hi=mid;
            else
                lo=mid+1;
        }
        return -1;
    }

    private static int countNum(int[] a, int lo, int hi) {
        int count=0;
        for(int k:a)
            if(k>=lo && k<=hi)
                count++;
        return count;
    }

    public static void main(String[] args) {
        int[] a={2,2,1,5,4,5,3};
        int[] b=new int[1];
//        System.out.println(findDoubleNum(a));
        System.out.println(findDoubleNumByHash(a));
//        System.out.println(findDoubleBest(a,b));
//        System.out.println(findDoubleBinary(a));
    }
}
