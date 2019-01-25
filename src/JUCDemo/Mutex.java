package JUCDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Auther: Goffery Gong
 * @Date: 2019/1/22 09:55
 * @Description:
 */
public class Mutex implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {//独占方法获取锁,状态为0时获取锁
            if(compareAndSetState(0,1)){//如果CAS设置同步状态为1成功，代表获取了同步状态
                setExclusiveOwnerThread(Thread.currentThread());//设置当前拥有独占访问权限的线程
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if(getState()==0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);//null参数表示没有线程拥有访问权限
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {//是否处于占用状态（1为占用）
            return getState()==1;
        }

        //返回一个condition，每个condition都包含了一个condition队列
        Condition newCondition(){return new ConditionObject();}
    }

    private final Sync sync=new Sync();
    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
