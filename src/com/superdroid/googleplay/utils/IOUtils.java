package com.superdroid.googleplay.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/**
	 * 关闭IO流
	 * 
	 * @param closeable
	 *            待关闭IO对象
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
