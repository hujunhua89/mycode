package com.example.base.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

/****
 * 图片压缩工具类
* <p>Title: ImgCompressUtil</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2016-5-31
 */
public class ImgCompressUtil {
	
	/***
	 * 
	 * @param infile 输入文件
	 * @param w 宽
	 * @param h 高
	 * @param outfile 输出文件
	 * @return boolean 压缩是否成功
	 * @author Junhua Hu
	 * @date 2016-5-31
	 */
	public synchronized static boolean compress(File infile,int w, int h,File outfile){
		FileOutputStream out = null;
		try {
			if(!infile.exists()){
				return false;
			}
			Image img = ImageIO.read(infile);      // 构造Image对象  
			int width = img.getWidth(null);    // 得到源图宽  
			int height = img.getHeight(null);  // 得到源图长
			if(w==0||h==0){
				w = width;
				h = height;
			}
			if (width / height > w / h) {  
			   h = (int) (height * w / width); 
		    } else {  
		       w = (int) (width * h / height);
		    }
			  // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
		     BufferedImage image = new BufferedImage(w, h,BufferedImage.SCALE_SMOOTH );   
		     image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
		     out = new FileOutputStream(outfile); // 输出到文件流  
		     // 可以正常实现bmp、png、gif转jpg  
		     ImageIO.write(image, "jpeg", out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			IOUtils.closeQuietly(out);
		}
	}
	/***
	 * 
	 * @param in 输入流
	 * @param w
	 * @param h
	 * @param outfile
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2016-5-31
	 */
	public synchronized static boolean compress(InputStream in,int w, int h,File outfile){
		FileOutputStream out = null;
		try {
			Image img = ImageIO.read(in);      // 构造Image对象  
			int width = img.getWidth(null);    // 得到源图宽  
			int height = img.getHeight(null);  // 得到源图长
			if(w==0||h==0){
				w = width;
				h = height;
			}
			if (width / height > w / h) {  
			   h = (int) (height * w / width); 
		    } else {  
		       w = (int) (width * h / height);
		    }
			  // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
		     BufferedImage image = new BufferedImage(w, h,BufferedImage.SCALE_SMOOTH );   
		     image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
		     out = new FileOutputStream(outfile); // 输出到文件流  
		     // 可以正常实现bmp、png、gif转jpg 
		     ImageIO.write(image, "JPEG", out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}	
	}
	/***
	 * 
	 * @param infilePath 输入文件路径
	 * @param w
	 * @param h
	 * @param outfilePath 输出文件路径
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2016-5-31
	 */
	public synchronized static boolean compress(String infilePath,int w, int h,String outfilePath){
		FileOutputStream out = null;
		try {
			File infile = new File(infilePath);
			if(!infile.exists()){
				return false;
			}
			Image img = ImageIO.read(infile);    // 构造Image对象  
			int width = img.getWidth(null);    // 得到源图宽  
			int height = img.getHeight(null);  // 得到源图长
			if(w==0||h==0){
				w = width;
				h = height;
			}
			if (width / height > w / h) {  
			   h = (int) (height * w / width); 
		    } else {  
		       w = (int) (width * h / height);
		    }
			  // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
		     BufferedImage image = new BufferedImage(w, h,BufferedImage.SCALE_SMOOTH );   
		     image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图 
		     File outfile = new File(outfilePath);
		     out = new FileOutputStream(outfile); // 输出到文件流  
		     // 可以正常实现bmp、png、gif转jpg  
		     ImageIO.write(image, "JPEG", out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			IOUtils.closeQuietly(out);
		}	
	}
	
	
	/**
     * 截取图片
     * @param srcImageFile  原图片地址
     * @param x    截取时的x坐标
     * @param y    截取时的y坐标
     * @param desWidth   截取的宽度
     * @param desHeight   截取的高度
     */
    public synchronized static void imgCut(InputStream in, int x, int y, int desWidth,
                              int desHeight,String outfileName) {
        try {
            Image img;
            ImageFilter cropFilter;
            BufferedImage bi = ImageIO.read(in);
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth < desWidth && srcHeight >= desHeight) {
            	desWidth = srcWidth;
            }
            if (srcHeight < desHeight) {
            	desHeight = srcHeight;
            }
            Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_SMOOTH);
            cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(desWidth, desHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            //输出文件
            ImageIO.write(tag, "JPEG", new File(outfileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 截取图片
     * @param srcImageFile  原图片地址
     * @param x    截取时的x坐标
     * @param y    截取时的y坐标
     * @param desWidth   截取的宽度
     * @param desHeight   截取的高度
     */
    public synchronized static void imgCut(String inFileName, int x, int y, int desWidth,
                              int desHeight,String outfileName) {
        try {
            Image img;
            ImageFilter cropFilter;
            BufferedImage bi = ImageIO.read(new File(inFileName));
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if (srcWidth >= desWidth && srcHeight >= desHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_SMOOTH);
                cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(desWidth, desHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();
                //输出文件
                ImageIO.write(tag, "JPEG", new File(outfileName));
            }else{
            	ImgCompressUtil.compress(inFileName,0,0,outfileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
