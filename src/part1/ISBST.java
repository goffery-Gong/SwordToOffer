package part1;

import java.util.Arrays;

/**
 * @Auther: PC
 * @Date: 2019/1/10 22:09
 * @Description:
 */
public class ISBST {
    /**
     * 判断是否为bst的后序遍历序列
     *
     * @param sequence
     * @return
     */
    boolean VerifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length==0)
            return false;

        int root = sequence[sequence.length - 1];
        //查找左子树中小于根节点的值
        int i;
        for (i = 0; i < sequence.length - 1; i++)
            if (sequence[i] > root)
                break;

        //查找右子树中大于根节点的值
        int j = i;
        for (; j < sequence.length - 1; j++)
            if (sequence[j] < root)
                return false;

        boolean left = true;
        boolean right = true;
        if (i > 0)
            left = VerifySquenceOfBST(Arrays.copyOfRange(sequence, 0, i));//包含起点，不包含终点
        if (j < sequence.length - 1)
            right = VerifySquenceOfBST(Arrays.copyOfRange(sequence, i + 1, sequence.length - 1));

        return left && right;
    }

    public static void main(String[] args) {
        int a[] = {2, 3, 4, 5, 6, 7, 8, 9};
        int from = 0;
        int to = 8;
        int original[] = Arrays.copyOfRange(a, from, to);
        for (int c : original) {
            System.err.println(c);
        }
    }
}
