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
	 * ִ�г�����ʱ������
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
	 * ִ�ж�����
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
	 * ȡ��ִ�г�����ʱ������
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
	 * ȡ��ִ�ж�����
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
	 * �̳߳ش�����
	 * 
	 * @author superdroid
	 *
	 */
	private static class ThreadPoolProxy {
		private ThreadPoolExecutor threadPoolExecutor;
		private int corePoolSize; // �̳߳��пɹ�����߳���
		private int maximumPoolSize;// �������ӵ��߳���
		private long keepAliveTime;// �̳߳���������ִ��ʱ �ɴ��ʱ��

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize,
				long keepAliveTime) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.keepAliveTime = keepAliveTime;
		}

		/**
		 * �����̳߳�
		 * 
		 * @return
		 */
		public ThreadPoolExecutor createThreadPoolExecutor() {
			/**
			 * 1.corePoolSize - ������������߳��������������߳� 2.maximumPoolSize - �������������߳�����
			 * ���ڳ��������߳������߳�, �����ָ���ĳ�ʱʱ����û��ʹ�õ�, �ͻᱻ���� 3.keepAliveTime -
			 * ���߳������ں���ʱ����Ϊ��ֹǰ����Ŀ����̵߳ȴ���������ʱ��:��ʱʱ�� 4.unit - keepAliveTime
			 * ������ʱ�䵥λ�� 5.workQueue - ִ��ǰ���ڱ�������Ķ��С��˶��н������� execute �����ύ��
			 * Runnable 6.�����̳߳صĹ��� 7.�����쳣��handler���̶�д����
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
