package part1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 *  add        增加一个元索                 如果队列已满，则抛出一个IIIegaISlabEepeplian异常
　　remove     移除并返回队列头部的元素        如果队列为空，则抛出一个NoSuchElementException异常
　　element    返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
　　offer       添加一个元素并返回true       如果队列已满，则返回false
　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
　　peek       返回队列头部的元素             如果队列为空，则返回null
　　put         添加一个元素                      如果队列满，则阻塞
　　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 * @param <E>
 */
public class StackByQueue<E> implements Iterable{
    int N;
    private Queue<E> queue1 = new LinkedList<>();
    private Queue<E> queue2 = new LinkedList<>();

    public void push(E item) {
        if(!queue1.isEmpty())
            queue1.offer(item);
        else
            queue2.offer(item);
        N++;
    }

    public E pop(){
        if(queue1.isEmpty() && queue2.isEmpty())
            try {
                throw new Exception("stack is empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(queue1.isEmpty()){
            while(queue2.size()>1)
                queue1.offer(queue2.poll());
            N--;
            return queue2.poll();
        }else{
            while(queue1.size()>1)
                queue2.offer(queue1.poll());
            N--;
            return queue1.poll();
        }
    }

    public static void main(String[] args) {
        StackByQueue queue=new StackByQueue<Integer>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        System.out.println(queue.pop());
        System.out.println(queue.N);
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int i=1;
            @Override
            public boolean hasNext() {
                return i<N;
            }

            @Override
            public Object next() {
//                return get(i);
                return null;
            }
        };
    }
}
