package auto;

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

	public static void click(Robot robot, int x, int y, int sleep) throws InterruptedException {
		Thread.sleep(sleep);
		robot.mouseMove(x, y);
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
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(50);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	public static boolean compareImage(File fileA, BufferedImage biB) {
		try {
			BufferedImage biA = ImageIO.read(fileA);
			if (biA.getWidth() == biB.getWidth() && biA.getHeight() == biB.getHeight()) {
				for (int x = 0; x < biA.getWidth(); x++) {
					for (int y = 0; y < biA.getHeight(); y++) {
						if (biA.getRGB(x, y) != biB.getRGB(x, y))
							return false;
					}
				}
			} else {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static BufferedImage drawBlackAndWhite(BufferedImage img, String path, String name) {
		BufferedImage blackAndWhiteImg = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D graphics = blackAndWhiteImg.createGraphics();
		graphics.drawImage(img, 0, 0, null);
		File f = new File(path + name+ ".jpg");
		try {
			ImageIO.write(blackAndWhiteImg, "jpg", f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return blackAndWhiteImg;
	}
	public static BufferedImage capture(Robot robot, int x, int y, int w, int h) {
		Rectangle rect = new Rectangle(x, y, w, h);
		BufferedImage img = robot.createScreenCapture(rect);
		return drawBlackAndWhite(img, "", "test");		
	}

	public static int[] convertToIntArr(String[] str) {
		int[] result = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			result[i] = Integer.parseInt(str[i]);
		}
		return result;
	}

	public static boolean captureAndCompare(Robot robot, int x, int y, int w, int h, File fileA) {
		return compareImage(fileA, capture(robot, x, y, w, h));
	}
}
