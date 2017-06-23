package demo;

import java.io.*;

import model.Unit;

/**
 * 
 * @author linxi 2017-06-10 Sat.
 */
public class PdfController {

	/**
	 * 递归处理指定目录下的所有pdf文件
	 * 
	 * @param file
	 */
	public static void handlePath(File file) {
		System.out.println(file.getAbsolutePath());
		if (file.isDirectory() && (!file.getName().startsWith("."))) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isDirectory()) {
					handlePath(file2);
				} else {
					if (file2.getName().endsWith("pdf") && (!file2.getName().startsWith("."))) {
						try {
							reversePdf(file2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			System.out.println("丢弃隐藏文件夹" + file.getName());
		}
	}

	/**
	 * 处理一个pdf为反色
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void reversePdf(File file) throws Exception {
		System.out.println(file.getAbsolutePath());
		String name = file.getName().replaceAll(".pdf$", "");
		String tempPath = file.getParentFile().getAbsolutePath() + "/" + name + "/";
		File tempDir = new File(tempPath);
		if (!tempDir.exists()) {
			tempDir.mkdir();
			System.out.println("创建临时图片储存目录 : " + tempPath);
		}
		int count = Pdf2Imgs.pdf2Pic(file.getAbsolutePath(), tempPath);
		Unit.UnitMaker(count, tempPath, name);
	}
}