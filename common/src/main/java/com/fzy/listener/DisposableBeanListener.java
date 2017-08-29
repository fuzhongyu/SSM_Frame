package com.fzy.listener;

import com.fzy.thread.ExcutorProcessPool;
import com.fzy.utils.SpringContextHolder;
import org.springframework.beans.factory.DisposableBean;

/**
 * 关闭程序时，清理资源
 * Created by fuzhongyu on 2017/8/24.
 */
public class DisposableBeanListener implements DisposableBean {


    /**
     * 实现DisposableBean接口, .
     */
    @Override
    public void destroy() throws Exception {

        //在Context关闭时清理静态变量
        SpringContextHolder.clearHolder();

        //关闭线程池
        ExcutorProcessPool.getInstance().shutdown();

        Thread.sleep(1000);

    }
}
