package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import dto.Cell;

public class ImageUtils {

	public static String countRGB(Robot robot, int[] coors) {
		Rectangle rect = new Rectangle(coors[0], coors[1], coors[2], coors[3]);
		BufferedImage img = robot.createScreenCapture(rect);
		int sum = 0, red = 0, green = 0, blue = 0;
		for (int w = 0; w < img.getWidth(); w++) {
			for (int h = 0; h < img.getHeight(); h++) {
				Color color = new Color(img.getRGB(w, h));
				red += color.getRed();
				green += color.getGreen();
				blue += color.getBlue();
			}
		}
		sum = red + green + blue;
		double rPc = NumberUtils.round(((double) red / (double) sum) * 100);
		double gPc = NumberUtils.round(((double) green / (double) sum) * 100);
		double bPc = NumberUtils.round(((double) blue / (double) sum) * 100);
		boolean isEmptyLocked = rPc == 34.0 && gPc == 33.0 && bPc == 33.0;
		boolean isEmptyUnlocked = rPc == 38.0 && gPc == 34.0 && bPc == 27.0;
		boolean isScroll = rPc == 39.0 && gPc == 37.0 && bPc == 24.0 || rPc == 31.0 && gPc == 37.0 && bPc == 32.0;
		if (isEmptyLocked || isEmptyUnlocked) {
			return "empty";
		} else if (isScroll) {
			return "scroll";
		} else if (rPc > bPc && rPc > gPc) {
			if (gPc < 30.0) {
				return "weapon";
			} else {
				return "misc";
			}
		} else if (bPc >= rPc && bPc >= gPc) {
			return "armour";
		} else {
			return "trash";
		}
	}
	
	public static JSONArray getInventory(BufferedImage inventory) {
		JSONArray array = new JSONArray();
		for(int row = 0; row < 8; row++) {
			array.put(getRow(inventory, row));
		}	
		return array;
	}
	
	public static JSONArray getRow(BufferedImage inventory, int row) {
		JSONArray array = new JSONArray();
		for(int position = 0; position < 10; position++) {
			array.put(getCell(getSubImage(inventory, position * 36, row * 36, 35, 35), position));
		}		
		return array;
	}
	
	public static JSONObject getCell(BufferedImage item, int position) {
		int sum = 0, red = 0, green = 0, blue = 0;
		for (int w = 0; w < item.getWidth(); w++) {
			for (int h = 0; h < item.getHeight(); h++) {
				Color color = new Color(item.getRGB(w, h));
				red += color.getRed();
				green += color.getGreen();
				blue += color.getBlue();
			}
		}
		sum = red + green + blue;
		double rPc = NumberUtils.round(((double) red / (double) sum) * 100);
		double gPc = NumberUtils.round(((double) green / (double) sum) * 100);
		double bPc = NumberUtils.round(((double) blue / (double) sum) * 100);
		boolean isLocked = rPc == 34.0 && gPc == 33.0 && bPc == 33.0;
		boolean isEmpty = rPc == 38.0 && gPc == 34.0 && bPc == 27.0;
		boolean isScroll = rPc == 39.0 && gPc == 37.0 && bPc == 24.0 //orange 
				|| rPc == 31.0 && gPc == 37.0 && bPc == 32.0; //blue
		String type = "";
		if (isEmpty || isLocked) {
			type = "empty";
		} else if (isScroll) {
			type = "scroll";
		} else if (rPc > bPc && rPc > gPc) {
			if (gPc < 30.0) {
				type = "weapon";
			} else {
				type = "misc";
			}
		} else if (bPc >= rPc && bPc >= gPc) {
			type = "armour";
		} else {
			type = "item";
		}
		Cell cell = new Cell();
		cell.setEmpty(isEmpty);
		cell.setLocked(isLocked);
//		if(!isEmpty && !isLocked) {
//			cell.setBase64(getBase64(item));
//		}
		cell.setPosition(position);
		cell.setType(type);
		JSONObject obj = new JSONObject(cell);
		System.out.println(obj.toString());
		return obj;
	}

	public static String getBase64(BufferedImage img) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
			byte[] byteArr = baos.toByteArray();
			return Base64.getEncoder().encodeToString(byteArr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static BufferedImage getSubImage(BufferedImage img,  Integer[] coors) {
		return getSubImage(img, coors[0], coors[1], coors[2], coors[3]);
	}

	public static BufferedImage getSubImage(BufferedImage img, int x, int y, int w, int h) {
		return img.getSubimage(x, y, w, h);
	}
	
	public static BufferedImage getSubImageBlackAndWhite(BufferedImage img, Integer[] coors) {	
		return getSubImageBlackAndWhite(img, coors[0], coors[1], coors[2], coors[3]);
	}

	public static BufferedImage getSubImageBlackAndWhite(BufferedImage img, int x, int y, int w, int h) {
		BufferedImage sub = getSubImage(img, x, y, w, h);
		BufferedImage blackAndWhiteImg = getBlackAndWhiteImage(sub);
		return blackAndWhiteImg;
	}

	public static BufferedImage getBlackAndWhiteImage(BufferedImage img) {
		BufferedImage blackAndWhiteImg = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D graphic = blackAndWhiteImg.createGraphics();
		graphic.drawImage(img, 0, 0, Color.WHITE, null);
		graphic.dispose();
		return blackAndWhiteImg;
	}

	public static boolean compareImage(File fileA, BufferedImage biB, boolean isNumber) {
		BufferedImage biA;
		try {
			biA = ImageIO.read(fileA);
			return compareImage(biA, biB, isNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean compareImage(BufferedImage biA, BufferedImage biB, boolean isNumber) {
		try {
			int totalMatching = 0;
			if (biA.getWidth() == biB.getWidth() && biA.getHeight() == biB.getHeight()) {
				for (int x = 1; x < biA.getWidth(); x++) {
					for (int y = 0; y < biA.getHeight(); y++) {
						if (biA.getRGB(x, y) == biB.getRGB(x, y)) {
							totalMatching++;
						}
					}
				}
				if (isNumber) {
					if (totalMatching / ((biA.getWidth() - 1) * (biA.getHeight())) * 100 <= 85) {
						totalMatching = 0;
						for (int x = 0; x < biA.getWidth() - 1; x++) {
							for (int y = 0; y < biA.getHeight(); y++) {
								if (biA.getRGB(x, y) == biB.getRGB(x, y)) {
									totalMatching++;
								}
							}
						}
					}
				}
				return totalMatching / ((biA.getWidth() - 1) * (biA.getHeight())) * 100 > 85;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
