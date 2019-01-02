package part1;

import java.util.Iterator;

public class MyLinkedList<E> implements Iterable {

    class Node<E> {
        E value;
        Node<E> next;
    }

    Node<E> firstNode;
    Node<E> lastNode;
    int N;

    public void addFirst(E e) {
        Node<E> temp = firstNode;
        firstNode = new Node<>();
        firstNode.value = e;
        if (temp == null)
            lastNode = firstNode;
        else
            firstNode.next = temp;
        N++;
    }

    public void addLast(E e) {
        Node<E> temp = lastNode;
        lastNode = new Node<>();
        lastNode.value = e;
        if (temp == null)
            lastNode = firstNode;
        else
            temp.next = lastNode;
        N++;
    }

    /**
     * �Ƴ�indexλ�õĽڵ�
     * ע�⣺�˷������ʹ��˫������ʵ�֣���Ϊ�м���ڱ�������
     *
     * @param index
     * @return
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        if (!checkElementIndex(index))
            throw new Exception("indexԽ��");

        Node<E> current = firstNode;
        Node temp = null;
        
        if (index == 1) {
            if(N==1){//ֻ��һ���ڵ�
                firstNode=null;
                lastNode=null;
            }
            else { //ɾ��ͷ���
                temp = current;
                firstNode = firstNode.next;
            }
        } else if (index == N) { //ɾ��β�ڵ㣨Ҳ������lastNode��ʵ�֣�
            for (int i = 1; i < index - 1; i++)
                current = current.next;
            temp = current.next;
            current.next = null;
        } else { //ɾ���м�ڵ�
            for (int i = 1; i < index - 1; i++)
                current = current.next;
            temp = current.next;
            current.next = current.next.next;
        }
        if(temp!=null){
            temp.value = null;
            temp.next = null;
        }
        N--;
    }

/*    *//**
     * ɾ��ĳ�ڵ� ʱ�临�Ӷ�ΪO(1)
     * ����Ϊ��ListNode pHead, ListNode pDelNode������index
     * @param index
     * @throws Exception
     *//*
    public void removeCopyWay(int index) throws Exception {
        Node current=firstNode;

        if(index==N)
            remove(index);
        else {
            for (int i = 1; i < index; i++) {
                current=current.next;
            }
            current.value=current.next.value;
            current.next=current.next.next;
        }
        N--;
    }*/

    /**
     * ����indexλ�õĽڵ�
     *
     * @param index
     * @return
     */
    public Node<E> nodeIndex(int index) {
        Node<E> node = firstNode;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public E getFirst() throws Exception {
        if (firstNode != null)
            return firstNode.value;
        else
            throw new Exception();
    }

    public E get(int index) throws Exception {
        if (!checkElementIndex(index))
            throw new Exception();

        Node<E> current = firstNode;
        for (int i = 1; i < index; i++)
            current = current.next;
        return current.value;
    }

    private boolean checkElementIndex(int index) {
        return index > 0 && index <= N;
    }

    public int size() {
        return N;
    }

    @Override
    public Iterator iterator() {
        return new Iterator<E>() {
            private Node<E> currentNode = firstNode;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public E next() {
                E e = currentNode.value;
                currentNode = currentNode.next;
                return e;
            }
        };
    }

    public static void main(String[] args) {
//        Node<Integer> newNode=new Node<>();
//        Node<Integer> first=newNode;
//        System.out.println(first==newNode);
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(4);
        for (Object i : list) {
            System.out.print(i + " ");
        }
//        try {
//            System.out.println("��2��Ϊ��"+list.get(2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        for (Object i : list) {
            System.out.print(i + " ");
        }
    }

}
