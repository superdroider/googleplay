package com.superdroid.googleplay.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/**
	 * �ر�IO��
	 * 
	 * @param closeable
	 *            ���ر�IO����
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
