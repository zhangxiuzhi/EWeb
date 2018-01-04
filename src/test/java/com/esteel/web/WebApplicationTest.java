package com.esteel.web;

import com.esteel.web.service.ContactClient;
import com.taobao.common.tfs.TfsManager;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ESTeel
 * Description:
 * User: zhangxiuzhi
 * Date: 2017-12-01
 * Time: 11:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTest {

    @Autowired
    TfsManager tfsManager;
    @Autowired
	ContactClient contactClient;

    @Test
    public void tfsPutTest(){

        String file = tfsManager.saveFile("e:\\2.jpg", null, "jpg");
        System.out.println(file);
        boolean b1 = tfsManager.fetchFile("T11RZTB5dT1R4bAZ6K", "jpg", "e:\\3.jpg");
        Assert.assertEquals(b1,true);
    }
   /* @Test
    public void cut(String srcImageFile, String result,  
            int x, int y, int width, int height) {  
       logger.info("uploadFileCut:图片裁剪,参数{}",fileId,imgType,x,y,width,height);
		//获取图像流文件
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		boolean fetchFile = tfsManager.fetchFile(fileId, imgType, out);
		if(fetchFile) {
			InputStream is = new ByteArrayInputStream(out.toByteArray());
			BufferedImage bi = ImageIO.read(is);
			int srcWidth = bi.getHeight(); // 源图宽度  
            int srcHeight = bi.getWidth(); // 源图高度  
            if (srcWidth > 0 && srcHeight > 0) {  
                Image image = bi.getScaledInstance(srcWidth, srcHeight,  
                        Image.SCALE_DEFAULT);  
                // 四个参数分别为图像起点坐标和宽高  
                // 即: CropImageFilter(int x,int y,int width,int height)  
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);  
                Image img = Toolkit.getDefaultToolkit().createImage(  
                        new FilteredImageSource(image.getSource(),  
                                cropFilter));  
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图  
                g.dispose();  
                // 输出为文件  
                ImageIO.write(tag, "JPEG",new File("E:\\img\\img.jpeg"));
            }  
		}
    }  */
}
