package demo;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

public class Pdf2Imgs {
	/**
	 * 
	 * @param pdf
	 *            源pdf文件目录
	 * @param bmppath
	 *            导出bmp文件目录-一定要加/
	 * @return 一共导出了多少页
	 */
	public static int pdf2Pic(String pdf, String bmppath) {
		int i = 0;
		if (!bmppath.endsWith("/")) {
			bmppath += "/";
		}
		Document document = new Document();
		document.setFile(pdf);
		float scale = 2.5f;// 缩放比例
		float rotation = 0f;// 旋转角度

		for (; i < document.getNumberOfPages(); i++) {
			BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
			RenderedImage rendImage = image;
			try {
				String imgName = i + "_temp.bmp";
				System.out.println(imgName);
				File file = new File(bmppath + imgName);
				ImageIO.write(rendImage, "bmp", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			image.flush();
		}
		document.dispose();
		return i;
	}
}
