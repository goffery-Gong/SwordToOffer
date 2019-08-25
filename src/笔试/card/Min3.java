package 笔试.card;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/4/9 20:42
 * @Description:
 */
public class Min3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        boolean notTwo = false;
        int count = 0;
        int len = array.length;

        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }
        Arrays.sort(array);

        for (int num : array) {
            if (num % array[0] != 0) {
                notTwo = true;
                break;
            }
        }

        if (notTwo) {
            for (int i = 0; i < array.length; i++) {
                while (array[i] != 1) {
                    array[i] /= 2;
                    count++;
                }
            }
        } else {
            for (int i = 0; i < len; i++) {
                while (array[i] != array[len / 2]) {
                    array[i] /= 2;
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}
