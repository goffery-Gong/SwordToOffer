package part2;

import part1.Utils;

import java.util.*;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/3/9 22:39
 * @Description:
 */
public class Tencent3 {
    public void DethLock(Object o1, Object o2){
        Thread t1=new Thread(()->{
            synchronized (o1){
                System.out.println(Thread.currentThread().getName()+" has o1");
                synchronized (o2){
                    System.out.println(Thread.currentThread().getName()+" has o2");
                }
            }
        },"thread1");
        Thread t2=new Thread(()->{
            synchronized (o2){
                System.out.println(Thread.currentThread().getName()+" has o2");
                synchronized (o1){
                    System.out.println(Thread.currentThread().getName()+" has o1");
                }
            }
        },"thread2");
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        /*Object o1=new Object();
        Object o2=new Object();
        new Tencent3().DethLock(o1,o2);*/
        String s="123";
        System.out.println(s.substring(1,1));
        Scanner sc=new Scanner(System.in);
        String ss=sc.next();
        System.out.println(ss);
//        String s2=sc.nextLine();
//        System.out.println(s2);
    }
}
