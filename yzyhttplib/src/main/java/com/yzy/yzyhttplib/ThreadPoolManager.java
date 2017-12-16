package com.yzy.yzyhttplib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yzy on 2017/12/16.
 */

public class ThreadPoolManager {
    private static final ThreadPoolManager ourInstance = new ThreadPoolManager();
    public static ThreadPoolManager getInstance() {
        return ourInstance;
    }
    private ThreadPoolExecutor mExecutor;
    private ArrayBlockingQueue mWaitQueue = new ArrayBlockingQueue(10);//等待队列
    private LinkedBlockingQueue<Future<?>> mRequestQueue = new LinkedBlockingQueue<>(10);//请求队列
    private int coreSize = 4;
    private int maxPoolSize = 10;
    private long maxLiveTime = 10;

    /**
     * 最大请求数为10，最大等待数10，那么就是可以同时提交20个请求，当第21个请求来了之后就会被拒绝
     */
    private ThreadPoolManager() {
        mExecutor = new ThreadPoolExecutor(coreSize,maxPoolSize,maxLiveTime, TimeUnit.SECONDS,mWaitQueue,mRejectedHandler);
        mExecutor.execute(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                FutureTask<?> vFutureTask = null;
                try {
                    //阻塞式操作
                    vFutureTask = (FutureTask<?>) mRequestQueue.take();
                } catch (InterruptedException pE) {
                    pE.printStackTrace();
                }
                mExecutor.execute(vFutureTask);
            }

        }
    };

    /**
     * 拒绝策略，被拒绝后就再往请求队列中添加
     */
    private RejectedExecutionHandler mRejectedHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable pRunnable, ThreadPoolExecutor pThreadPoolExecutor) {
            try {
                //拒绝之后丢到拒绝队列中
                mRequestQueue.put(new FutureTask<>(pRunnable,null));
            } catch (InterruptedException pE) {
                pE.printStackTrace();
            }
        }
    };

    /***************************************暴露给外部调用的api**********************************************/
    public <T> void excute(FutureTask<T> pTask) {
        try {
            if(pTask!=null){
                mRequestQueue.put(pTask);
            }
        } catch (InterruptedException pE) {
            pE.printStackTrace();
        }

    }
}
