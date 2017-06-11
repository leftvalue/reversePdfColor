package test;

import java.io.File;

import demo.PdfController;

public class Test {
	public static void main(String args[]) throws Exception {
		String path = "/Users/linxi/Movies/展示1.pdf";// 替换此目录为要处理的pdf文件所在目录即可，自动处理指定目录内的所有pdf
		PdfController.reversePdf(new File(path));
		return;
	}
}
