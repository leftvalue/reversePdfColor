package demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BmpController {
	/**
	 * 反转指定目录下0.bmp到{}total-1}.bmp图片们的颜色 ， 同时删除temp原色图片
	 * 
	 * @param total
	 * @param path
	 * @throws IOException
	 */
	public static void reverseColorOfAllImg(int total, String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			System.out.println("指定目录不存在");
			return;
		} else {
			if (!file.canWrite()) {
				System.out.println("指定目录不可写");
				return;
			} else {
				for (int i = 0; i < total; i++) {
					System.out.println("正在转色第" + i + "张图片");
					reverseBmpColor(path + "/" + i + "_temp.bmp", path + "/" + i + ".bmp");
					new File(path + "/" + i + "_temp.bmp").delete();// 删除原图
				}
			}
		}
	}

	/**
	 * 反转一张bmp的颜色
	 * 
	 * @param from
	 * @param to
	 * @throws IOException
	 */
	public static void reverseBmpColor(String from, String to) throws IOException {
		File f = new File(from);
		File fa = new File(to);
		FileOutputStream sa = new FileOutputStream(fa);
		byte[] b = Files.readAllBytes(Paths.get(from));
		for (int i = 55; i < f.length(); i++) {// bmp文件头大小为54
			// b[i] = (byte) ((byte) 0xff - b[i]);
			b[i] = (byte) (~b[i]);
		}
		sa.write(b);
		sa.close();
	}

}
