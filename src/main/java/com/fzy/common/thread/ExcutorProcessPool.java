package com.fzy.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程池（单例）
 * Created by fuzhongyu on 2017/2/10.
 */
public class ExcutorProcessPool{

    private Logger logger= LoggerFactory.getLogger(ExcutorProcessPool.class);

    private final static int CORE_POOL_SIZE=50;
    private final static int MAXIMUM_POOL_SIZE=150;
    private final static int KEEP_ALIVE_TIME=20;

    private static ExcutorProcessPool excutorProcessPool=new ExcutorProcessPool();

    private ExecutorServiceFactory executorServiceFactory= ExecutorServiceFactory.getInstance();

    private ExecutorService executorService;

    private ExcutorProcessPool(){

        executorService=executorServiceFactory.createThreadPool(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());

    }

    public static ExcutorProcessPool getInstance(){return excutorProcessPool;}


    /**
     * 获取当前线程池中运行的线程数
     * @return
     */
    public Integer getThreadCount(){
        return ((ThreadPoolExecutor)executorService).getActiveCount();
    }

    /**
     * 关闭线程池
     */
    public void shutdown(){
        if (logger.isDebugEnabled()){
            logger.debug("---关闭线程池ExcutorProcessPool---");
        }
        executorService.shutdown();
    }

    /**
     * 提交任务到线程池，无返回值
     * @param runnable
     */
    public void excute(Runnable runnable){
        executorService.execute(runnable);
    }

    /**
     * 提交任务到线程池，可以接受返回值
     * @param callable
     * @return
     */
    public Future<?> submit(Callable<?> callable){
        return executorService.submit(callable);
    }

}
