package part1;

public class OrderArray {
    /**
     * 常规方法，交换
     * @param array
     */
    public void orderArray(int[] array) {
        if(array==null || array.length==0)
            return;

        int lo = 0;
        int hi = array.length-1;
        while (true){
            while(lo<hi && (array[lo] & 1)!=0) //找到第一个偶数
                lo++;
            while (lo<hi && (array[hi] & 1)!=1)//找到第一个奇数
                hi--;
            if(lo>=hi) break;
            exch(array,lo,hi);
        }
    }

    /**
     * 函数解耦，提高复用,将条件抽取出来单独作为一个函数进行判断
     * @param array
     */
    public void orderArrayBest(int[] array) {
        if(array==null || array.length==0)
            return;

        int lo = 0;
        int hi = array.length-1;
        while (true){
            while(lo<hi && !isEvent(array[lo]))
                lo++;
            while (lo<hi && isEvent(array[hi]))
                hi--;
            if(lo>=hi) break;
            exch(array,lo,hi);
        }
    }

    private boolean isEvent(int i) {
        return (i & 1)==0;
    }

    private void exch(int[] array, int lo, int hi) {
        int temp=array[lo];
        array[lo]=array[hi];
        array[hi]=temp;
    }

    public static void main(String[] args) {
        int[] a={2,2,3,5,6,7};
        new OrderArray().orderArrayBest(a);
        for (int i :
                a) {
            System.out.print(i+" ");
        }
    }
}
