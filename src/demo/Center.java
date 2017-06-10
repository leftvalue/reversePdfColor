package demo;

import java.io.File;

public class Center {
	/**
	 * 控制台调用，第一个参数为待处理文件夹路径
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		PdfController.handlePath(new File(args[0]));
		return;
	}
}
