package demo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import java.util.TreeMap;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

@SuppressWarnings("unused")
public class Imgs2Pdf {

	public static void createPdf(int count, String path, String name) throws Exception {
		if (!path.endsWith("/")) {
			path += "/";
		}
		File file = new File(path);
		if (!file.canWrite()) {
			System.out.println(path + "没有写pdf权限");
		}
		FileInputStream fileInputStream = new FileInputStream(path + "0.bmp");// 因为一个pdf转的所有img尺寸相同，所以用第一个img来确定最终pdf的大小
		BufferedImage sourceImg = ImageIO.read(fileInputStream);
		int w = sourceImg.getWidth();
		int h = sourceImg.getHeight();
		Document doc = new Document(new Rectangle(w, h));
		System.out.println("设置pdf大小" + w + "x" + h);
		fileInputStream.close();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(path + name + ".pdf"));
			doc.open();
			for (int i = 0; i < count; i++) {
				Image tempImage = Image.getInstance(path + i + ".bmp");
				doc.add(tempImage);
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}