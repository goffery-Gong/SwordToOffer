package part1;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/3 20:17
 * @Description:
 */
public class FindNumInSortedArray {
    public int GetNumberOfK(int [] array , int k) {
       //通过找到第一次k出现的位置和最后一次出现的位置，得到出现的次数
        if(array==null || array.length==0)
            return 0;
        int first=GetFirst(array, k,0,array.length-1);
        int end=GetLast(array, k,0,array.length-1);
        if(first>-1 && end>-1)
            return end-first+1;
        return 0;
    }

    private int GetFirst(int[] array, int k, int lo, int hi){
        if(lo>hi)   //这里为小于号，保证在等号的时候不会返回-1，而是继续执行逻辑
            return -1;
        int mid=(lo+hi)>>1;
        if(array[mid]>k)
            return GetFirst(array, k, lo, mid-1);
        else if(array[mid]<k)
            return GetFirst(array, k, mid+1, hi);
        else if(mid-1>=0 && array[mid-1]==k)
            return GetFirst(array, k, lo,mid-1);
        else
            return mid;
    }

    private int GetLast(int[] array, int k, int lo, int hi){
        int mid;
        while(lo<=hi){
            mid=(lo+hi)>>1;
            if(array[mid]>k)
                hi=mid-1;
            else if(array[mid]<k)
                lo=mid+1;
            else if(mid+1<array.length && array[mid+1]==k)
                lo=mid+1;
            else
                return mid;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] a={1,2,3,3,3,3};
        System.out.println(new FindNumInSortedArray().GetNumberOfK(a,3));
    }
}
