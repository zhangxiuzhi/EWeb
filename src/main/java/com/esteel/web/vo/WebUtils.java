package com.esteel.web.vo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * web工具类
 * 
 * @author chenshouye
 *
 */
public class WebUtils {
	/**
	 * 图片裁剪
	 * 
	 * @param out
	 *            二进制图片流
	 * @param imgType
	 *            图片类型
	 * @param x
	 *            图片起点x轴
	 * @param y
	 *            图片起点y轴
	 * @param width
	 *            图片裁剪宽度
	 * @param height
	 *            图片裁剪高度
	 * @return byte[] 返回结果
	 * @throws IOException
	 */
	public static byte[] imgCut(ByteArrayOutputStream out, String imgType, int x, int y, int width, int height) throws IOException {
		ByteArrayOutputStream outimg = new ByteArrayOutputStream();
		InputStream is = new ByteArrayInputStream(out.toByteArray());
		BufferedImage bi = ImageIO.read(is);
		int srcWidth = bi.getHeight(); // 源图宽度
		int srcHeight = bi.getWidth(); // 源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			// 四个参数分别为图像起点坐标和宽高
			// 即: CropImageFilter(int x,int y,int width,int height)
			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
			g.dispose();
			// 输出为文件
			// ImageIO.write(tag, "JPEG", new File("E:\\img\\img.jpeg"));
			ImageIO.write(tag, "JPEG", outimg);
			// 关闭流
			is.close();
			outimg.close();
			return outimg.toByteArray();
		}else {
			return null;
		}
	}
}
