package part1.Singleton;

public class SortByOn {
    private static final int MAXNUM=99;

    /**
     * 将数组排序(数字范围在0-99),时间复杂度O(N),空间O(N)
     * @param a
     * @throws Exception
     */
    public static void sortByON(int[] a) throws Exception {
        if(a==null)
            return ;
        int[] temp=new int[MAXNUM+1];
        //临时数组初始化
        for (int i = 0; i < temp.length; i++) {
            temp[i]=0;
        }

        //统计每个数字的出现频率
        for (int i = 0; i <a.length; i++) {
            int tempNum=a[i];
            if(tempNum>MAXNUM || tempNum<0)
                throw new Exception("越界。。。");
            temp[tempNum]++;
        }

        int index=0;
        for (int i = 0; i < MAXNUM; i++) {
            for (int j = 0; j < temp[i]; j++) {
                a[index]=i;
                index++;
            }
        }
    }

    public static void main(String[] args) {
        int[] a={2,2,2,2,4,4,4,90,4,6};
        try {
            sortByON(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int k :
                a) {
            System.out.println(k);
        }
    }
}
