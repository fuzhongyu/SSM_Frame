package com.fzy.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池构造工厂
 * Created by fuzhongyu on 2017/2/10.
 */
public class ExecutorServiceFactory {

    private static ExecutorServiceFactory executorServiceFactory=null;

    private ExecutorService executors;

    private ExecutorServiceFactory(){}

    public static ExecutorServiceFactory getInstance(){
        if(executorServiceFactory==null){
            synchronized (ExecutorServiceFactory.class){
                if(executorServiceFactory==null){
                    executorServiceFactory=new ExecutorServiceFactory();
                }
            }
        }
        return executorServiceFactory;
    }


    /**
     * 获取线程工厂
     * @return
     */
    private ThreadFactory getThreadFactory(){
        return new ThreadFactory() {
            AtomicInteger sn=new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                SecurityManager s=System.getSecurityManager();
                ThreadGroup group=(s!=null)?s.getThreadGroup():Thread.currentThread().getThreadGroup();
                Thread t=new Thread(group,r);
                t.setName("线程任务："+sn.incrementAndGet());
                return t;
            }
        };
    }


    /**
     * Creates a new {@code ThreadPoolExecutor} with the given initial
     * parameters and default thread factory and rejected execution handler.
     * It may be more convenient to use one of the {@link Executors} factory
     * methods instead of this general purpose constructor.
     *
     * @param corePoolSize the number of threads to keep in the pool, even
     *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
     * @param maximumPoolSize the maximum number of threads to allow in the
     *        pool
     * @param keepAliveTime when the number of threads is greater than
     *        the core, this is the maximum time that excess idle threads
     *        will wait for new tasks before terminating.
     * @param unit the time unit for the {@code keepAliveTime} argument
     * @param workQueue the queue to use for holding tasks before they are
     *        executed.  This queue will hold only the {@code Runnable}
     *        tasks submitted by the {@code execute} method.
     * @return
     */
    public ExecutorService createThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        executors=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
        return executors;
    }


    /**
     * 创建一个线程池，它可安排在给定延时后运行命令或者定期执行
     * @return
     */
    public ExecutorService createScheduledThreadPool(){
        int availableProcessors=Runtime.getRuntime().availableProcessors();
        executors= Executors.newScheduledThreadPool(availableProcessors*10,getThreadFactory());
        return executors;
    }

    /**
     * 创建一个使用单个worker线程的executor,以无界队列来运行该线程
     * 注：如果因为在关闭前的执行期间出现失败而终止了此单个线程，那么如果需要，一个新线程将代替他执行后续任务，可保证顺序的执行各个任务，并且在任意给定
     *    的时间不会有多个线程是活动的  newFixedThreadPool(1)不同，可保证无需重新配置此方法返回的执行程序即可使用其他线程
     * @return
     */
    public ExecutorService createSingleThreadExecutor(){
        executors=Executors.newSingleThreadExecutor(getThreadFactory());
        return executors;
    }


    /**
     * 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用他们。对于执行很多短期异步任务的程序而言，这些线程
     * execute将重用以前构造的线程（如果线程可用）。如果现有线程没有线程可用的，则创建一个新线程并添加到池中。终止并从缓存中移除
     * 构造方法具有类似属性但细节不同（例如超时参数）的线程池
     * @return
     */
    public ExecutorService createCachedThreadPool(){
        executors=Executors.newCachedThreadPool(getThreadFactory());
        return executors;
    }


    /**
     * 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。在任意点，在大多数 nThreads
     * 线程会处于处理任务的活动状态。如果在所有线程处于活动状态时提交附加任务
     * ，则在有可用线程之前，附加任务将在队列中等待。如果在关闭前的执行期间由于失败而导致任何线程终止
     * ，那么一个新线程将代替它执行后续的任务（如果需要）。在某个线程被显式地关闭之前，池中的线程将一直存在。
     *
     * @return
     */

    public ExecutorService createFixedThreadPool(int count){
        executors=Executors.newFixedThreadPool(count,getThreadFactory());
        return executors;
    }


}
