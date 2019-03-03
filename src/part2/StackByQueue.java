package part2;


import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/2/21 13:20
 * @Description:
 */
public class StackByQueue<E> {
    private Queue<E> queue1=new LinkedList<>();
    private Queue<E> queue2=new LinkedList<>();

    public void push(E e){
        if(!queue1.isEmpty())
            queue1.offer(e);
        else
            queue2.offer(e);
    }

    public E pop() throws Exception {
        if(queue1.isEmpty() && queue2.isEmpty())
            throw new Exception("stack 为空");

        if(queue1.isEmpty()){
            while(queue2.size()>1)
                queue1.offer(queue2.poll());
            return queue2.poll();
        }else{
            while(queue1.size()>1)
                queue2.offer(queue1.poll());
            return queue1.poll();
        }
    }
}
