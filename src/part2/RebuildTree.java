package part2;

import part1.BinaryTreeNode;
import part1.Utils;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 10:42
 * @Description:
 */
public class RebuildTree extends BinaryTreeNode {
    BinaryTreeNode rebuild(int[] pre, int[] mid) {
        if (Utils.isNullorZero(pre) || Utils.isNullorZero(mid))
            return null;

        return rebuild(pre, mid, 0, pre.length - 1, 0, mid.length - 1);
    }

    private BinaryTreeNode rebuild(int[] pre, int[] mid, int preStart, int preEnd, int midStart, int midEnd) {
        if(preStart>preEnd || midStart>midEnd)
            return null;

        BinaryTreeNode root=new BinaryTreeNode(pre[preStart]);

        int border = midStart;
        while(border<=midEnd && mid[border]!=pre[preStart])
            border++;

        int leftLength=border-midStart;
        int leftPreEnd=preStart+leftLength;

        root.left=rebuild(pre,mid,preStart+1,leftPreEnd,midStart,border-1);
        root.right=rebuild(pre,mid,leftPreEnd+1,preEnd,border+1,midEnd);
        return root;
    }
}
