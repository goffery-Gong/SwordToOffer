package part1;

import java.util.Iterator;

public class LinkedList<E> implements Iterable{

    private static class Node<E>{
        E value;
        Node<E> next;
    }

    Node<E> firstNode;
    Node<E> lastNode;
    int N;

    public void addFirst(E e){
        Node<E> temp=firstNode;
        firstNode=new Node<>();
        firstNode.value=e;
        if(temp==null)
            lastNode=firstNode;
        else
            firstNode.next=temp;
        N++;
    }

    public void addLast(E e){
        Node<E> temp=lastNode;
        lastNode=new Node<>();
        lastNode.value=e;
        if(temp==null)
            lastNode=firstNode;
        else
            temp.next=lastNode;
        N++;
    }

    /**
     * 移除index位置的节点
     * 注意：此方法最好使用双向链表实现，因为中间存在遍历链表
     * @param index
     * @return
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        if(!checkElementIndex(index))
            throw new Exception();

        Node<E> current=firstNode;
        if(index==1) {
            firstNode = firstNode.next;
        }
        else{
            for (int i = 1; i < index-1; i++)
                current=current.next;
            current.next=current.next.next;
        }
        current.value=null;
        current.next=null;
        N--;
    }

    /**
     * 返回index位置的节点
     * @param index
     * @return
     */
    public Node<E> nodeIndex(int index){
        Node<E> node=firstNode;
        for (int i = 0; i < index; i++) {
            node=node.next;
        }
        return node;
    }

    public E getFirst() throws Exception {
        if(firstNode!=null)
            return firstNode.value;
        else
            throw  new Exception();
    }

    public E get(int index) throws Exception {
        if(!checkElementIndex(index))
            throw new Exception();

        Node<E> current=firstNode;
        for (int i = 1; i < index; i++)
            current=current.next;
        return current.value;
    }

    private boolean checkElementIndex(int index) {
        return index>0 && index<=N;
    }

    public int size(){
        return N;
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            private Node<E> currentNode=firstNode;

            @Override
            public boolean hasNext() {
                return currentNode!=null;
            }

            @Override
            public E next() {
               E e=currentNode.value;
               currentNode=currentNode.next;
               return e;
            }
        };
    }

    public static void main(String[] args) {
//        Node<Integer> newNode=new Node<>();
//        Node<Integer> first=newNode;
//        System.out.println(first==newNode);
        LinkedList<Integer> list=new LinkedList<>();
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(4);
        try {
            list.remove(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("第2个为："+list.get(2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Object i : list) {
            System.out.print(i+" ");
        }
    }

}
