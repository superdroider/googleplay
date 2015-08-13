package com.superdroid.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadManager {
	private static ThreadPoolExecutor longPoolExecutor;
	private static ThreadPoolExecutor shortPoolExecutor;
	private static Object longThreadLock = new Object();
	private static Object shortThreadLock = new Object();

	/**
	 * 执行长（耗时）任务
	 * 
	 * @param runnable
	 */
	public static void longTaskExecute(Runnable runnable) {
		synchronized (longThreadLock) {
			if (longPoolExecutor == null) {
				ThreadPoolProxy proxy = new ThreadPoolProxy(3, 5, 100);
				longPoolExecutor = proxy.createThreadPoolExecutor();
			}
		}
		longPoolExecutor.execute(runnable);
	}

	/**
	 * 执行短任务
	 * 
	 * @param runnable
	 */
	public static void shortTaskExecute(Runnable runnable) {
		synchronized (shortThreadLock) {
			if (shortPoolExecutor == null) {
				ThreadPoolProxy proxy = new ThreadPoolProxy(3, 5, 100);
				shortPoolExecutor = proxy.createThreadPoolExecutor();
			}
		}
		shortPoolExecutor.execute(runnable);
	}

	/**
	 * 取消执行长（耗时）任务
	 * 
	 * @param runnable
	 */
	public static void cancleLongTask(Runnable runnable) {
		if (longPoolExecutor == null || longPoolExecutor.isShutdown()
				|| longPoolExecutor.isTerminated()) {
			return;
		} else {
			longPoolExecutor.getQueue().remove(runnable);
		}
	}

	/**
	 * 取消执行短任务
	 * 
	 * @param runnable
	 */
	public static void cancleShortTask(Runnable runnable) {
		if (shortPoolExecutor == null || shortPoolExecutor.isShutdown()
				|| shortPoolExecutor.isTerminated()) {
			return;
		} else {
			shortPoolExecutor.getQueue().remove(runnable);
		}
	}

	/**
	 * 线程池代理类
	 * 
	 * @author superdroid
	 *
	 */
	private static class ThreadPoolProxy {
		private ThreadPoolExecutor threadPoolExecutor;
		private int corePoolSize; // 线程池中可管理的线程数
		private int maximumPoolSize;// 额外可添加的线程数
		private long keepAliveTime;// 线程池中无任务执行时 可存活时间

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize,
				long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		/**
		 * 创建线程池
		 * 
		 * @return
		 */
		public ThreadPoolExecutor createThreadPoolExecutor() {
			/**
			 * 1.corePoolSize - 池中所保存的线程数，包括空闲线程 2.maximumPoolSize - 池中允许的最大线程数。
			 * 对于超过核心线程数的线程, 如果在指定的超时时间内没有使用到, 就会被销毁 3.keepAliveTime -
			 * 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间:超时时间 4.unit - keepAliveTime
			 * 参数的时间单位。 5.workQueue - 执行前用于保持任务的队列。此队列仅保持由 execute 方法提交的
			 * Runnable 6.创建线程池的工厂 7.处理异常的handler（固定写法）
			 */
			if (threadPoolExecutor == null) {
				threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
						maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>(10),
						Executors.defaultThreadFactory());
			}
			return threadPoolExecutor;
		}
	}

}
