package auto;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class AutoAction {
	public static void toggleAuto(Robot robot) throws InterruptedException {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_H);
		Thread.sleep(50);
		robot.keyRelease(KeyEvent.VK_H);
		robot.keyRelease(KeyEvent.VK_ALT);
	}

	public static void toggleInventory(Robot robot) throws InterruptedException {
		robot.keyPress(KeyEvent.VK_ALT);
		robot.keyPress(KeyEvent.VK_E);
		Thread.sleep(50);
		robot.keyRelease(KeyEvent.VK_E);
		robot.keyRelease(KeyEvent.VK_ALT);
	}

	public static void click(Robot robot, int windowCoorsX, int windowCoorsY, AutoStep step) throws InterruptedException {
		int[] coors = getCoor(windowCoorsX, windowCoorsY, step);
		Thread.sleep(100);
		robot.mouseMove(coors[0], coors[1]);
		Thread.sleep(100);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(100);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(100);
	}

	public static void type(Robot robot, String characters) throws InterruptedException {
		StringSelection selection = new StringSelection(characters);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		Thread.sleep(50);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(50);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public static boolean compareImage(File fileA, BufferedImage biB, boolean isNumber) {
		try {
			BufferedImage biA = ImageIO.read(fileA);			
			int totalMatching = 0;
			if (biA.getWidth() == biB.getWidth() && biA.getHeight() == biB.getHeight()) {
				for (int x = 1; x < biA.getWidth(); x++) {
					for (int y = 0; y < biA.getHeight(); y++) {
						if (biA.getRGB(x, y) == biB.getRGB(x, y)) {
							totalMatching++;
						}
					}
				}
				if(isNumber) {
					if(totalMatching/((biA.getWidth()-1) * (biA.getHeight()))*100 <= 85) {
						totalMatching = 0;
						for (int x = 0; x < biA.getWidth()-1; x++) {
							for (int y = 0; y < biA.getHeight(); y++) {
								if (biA.getRGB(x, y) == biB.getRGB(x, y)) {
									totalMatching++;
								}
							}
						}
					}
				}
				return totalMatching/((biA.getWidth()-1) * (biA.getHeight()))*100 > 85;				
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static BufferedImage drawBlackAndWhite(BufferedImage img, String path, String name, boolean isCreateFile) {
		BufferedImage blackAndWhiteImg = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D graphic = blackAndWhiteImg.createGraphics();
		graphic.drawImage(img, 0, 0, Color.WHITE, null);
		graphic.dispose();
		if(isCreateFile) {
			File f = new File(path + name + ".png");
			try {
				ImageIO.write(blackAndWhiteImg, "png", f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}		
		return blackAndWhiteImg;
	}	
	public static BufferedImage capture(Robot robot, int[] coors, String path, String name, boolean isCreateFile) {
		Rectangle rect = new Rectangle(coors[0], coors[1], coors[2], coors[3]);
		BufferedImage img = robot.createScreenCapture(rect);
		return drawBlackAndWhite(img, path, name, isCreateFile);
	}

	public static int[] convertToIntArr(String str, int windowX, int windowY) {
		String[] arr = str.split(",");
		int[] result = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			if(i == 0) {
				result[i] = Integer.parseInt(arr[i]) + windowX;
			} else if (i == 1) {
				result[i] = Integer.parseInt(arr[i]) + windowY;
			} else {
				result[i] = Integer.parseInt(arr[i]);
			}
		}
		return result;
	}

	public static boolean captureAndCompare(Robot robot, int[] coors, File fileA) {
		return compareImage(fileA, capture(robot, coors, "", "", false), false);
	}
	
	public static int[] getCoor(int windowCoorsX, int windowCoorsY, AutoStep step) {
		int[] result = { windowCoorsX + step.getX(), windowCoorsY + step.getY()};
		return result;
	}
	
	//0 : 608 204 4 8
	public static int getValue(Robot robot, List<File> numberList, int[] coors) {	
		BufferedImage img = capture(robot, coors, "", "", false);	
		int result = -1, index = 0;
		int[] originalCoors = coors;
		for(File file : numberList) {			
			if(compareImage(file, img, true)) {
				result = index;
				break;
			}
			index++;
		}
		for(int i = 0; i < 2; i++) {
			if(result < 0) {
				coors[0] -= 1;
				img = capture(robot, coors, "", "", false);	
				index = 0;
				for(File file : numberList) {			
					if(compareImage(file, img, true)) {
						result = index;
						break;
					}
					index++;
				}
			} else {
				break;
			}
		}
		for(int i = 0; i < 2; i++) {
			if(result > 0) {
				originalCoors[0] += 1;
				img = capture(robot, originalCoors, "", "", false);	
				index = 0;
				for(File file : numberList) {			
					if(compareImage(file, img, true)) {
						result = index;
						break;
					}
					index++;
				}
			} else {
				break;
			}
		}
		if(result < 0) result = 0;
		return result;
	}
	public static int getItemAmount(Robot robot, int windowX, int windowY, int col, int row, List<File> numberList) throws IOException {
		int[] coors = { windowX + 608 + col*36, windowY + 204 + row*36, 4, 8 };
		int last = getValue(robot, numberList, coors);
		int[] coorsMiddle = { coors[0] - 7 , coors[1], 4, 8 };
		int middle = getValue(robot, numberList, coorsMiddle);	
		int[] coorFirst = { coorsMiddle[0] - 7 , coors[1], 4, 8 };
		int first = getValue(robot, numberList, coorFirst);
		return first * 100 + middle * 10 + last;
	}
}
