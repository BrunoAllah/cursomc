package com.cursomc.business;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cursomc.business.exception.FileException;

@Service
public class ImageBusiness {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if (!extension.equals("png") && !extension.equals("jpg"))
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		
		try {
			BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
			if (extension.equals("png"))
				image = pngToJpg(image);
			return image;
		}catch (IOException e) {
			throw new FileException("Erro ao ler o arquivo");
		}
	}

	public BufferedImage pngToJpg(BufferedImage image) {
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), 
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage image, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	public BufferedImage cropSquare(BufferedImage sourceImage) {
		int min = sourceImage.getHeight() <= sourceImage.getWidth() ? sourceImage.getHeight() : sourceImage.getWidth();
		return Scalr.crop(sourceImage, 
				(sourceImage.getWidth()/2) - (min/2), 
				(sourceImage.getHeight()/2) - (min/2), 
				min,
				min);
	}
	
	public BufferedImage resize(BufferedImage sourceImage, int size) {
		return Scalr.resize(sourceImage, Scalr.Method.ULTRA_QUALITY, size);
	}
}
