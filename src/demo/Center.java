package demo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Center {
	/**
	 * 控制台调用，第一个参数为待处理文件夹路径
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
		String path = args[0];
		File file = new File(path);
		System.out.println("PDF 反色处理小程序\nPowered by dick.");
		if (file.isDirectory()) {
			System.out.println("正在处理指定目录 " + LocalDateTime.now().format(formatter));
			PdfController.handlePath(file);
		} else if (file.isFile() && path.endsWith(".pdf")) {
			System.out.println("正在处理指定文件 " + LocalDateTime.now().format(formatter));
			PdfController.reversePdf(file);
		}
		System.out.println("Reverse completed  " + LocalDateTime.now().format(formatter));
		return;
	}
}
