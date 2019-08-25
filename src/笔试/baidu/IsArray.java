package 笔试.baidu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/4/1 14:39
 * @Description:
 */
public class IsArray {
    private static int flag = -1;
    static List<String[]> list = new ArrayList<>();

    public static int isArray(String arr[]) {
        if (arr == null || arr.length == 0)
            return -1;
        List<String[]> stringArrays = allArray(arr, 0);
        for (String[] array : stringArrays) {
            if(flag==1)
                break;
            isRealArray(array);
        }
        return flag;
    }

    //将数组全排列
    private static List<String[]> allArray(String[] arr, int index) {
        if (index >= arr.length)
            return list;
        for (int j = index; j < arr.length; j++) {
            if (!list.contains(arr))
                list.add(arr);
            if (j != index)
                allArray(exch(arr, j, index), index + 1);
        }
        return list;
    }

    private static String[] exch(String[] arr, int j, int index) {
        String[] newArray=new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArray[i]=arr[i];
        }
        String temp = newArray[j];
        newArray[j] = newArray[index];
        newArray[index] = temp;
        return newArray;
    }

    //将每个数组进行判断，是否有满足条件的
    private static void isRealArray(String[] arr) {
        int firstWordLength;
        char lastWord;
        char firstWord;
        int i;
        for ( i= 0; i < arr.length - 1; i++) {
            firstWordLength = arr[i].length();
            lastWord = arr[i].charAt(firstWordLength - 1);
            firstWord = arr[i + 1].charAt(0);
            if (lastWord != firstWord){
                i--;
                break;
            }
        }
        if (i ==arr.length-1)
            flag=1;
    }

    public static void main(String[] args) {
        String[] arr={"kdw","cdf","abc"};
        System.out.println(new IsArray().isArray(arr));
    }

}
