package utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileUtils {
	public static void writeFile(BufferedImage img, String name) {
		File f = new File(name);
		try {
			ImageIO.write(img, "png", f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	public static BufferedImage getImage(String name) throws IOException {
		File img = new File(name);
		return ImageIO.read(img);
	}


}
