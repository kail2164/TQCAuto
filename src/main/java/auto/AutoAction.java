package auto;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.FileUtils;
import utils.ImageUtils;

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

	public static void click(Robot robot, int windowCoorsX, int windowCoorsY, Integer[] step)
			throws InterruptedException {
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

	public static void drawBlackAndWhite(BufferedImage img, String path, String name, boolean isCreateFile) {
		if (isCreateFile) {
			File f = new File(path + name + ".png");
			try {
				ImageIO.write(ImageUtils.getBlackAndWhiteImage(img), "png", f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static BufferedImage draw(BufferedImage img, String name, boolean isCreateFile) {
		if (isCreateFile) {
			FileUtils.writeFile(img, "./img\\" + name + ".png");
		}
		return img;
	}

	public static JSONObject getCharacter(BufferedImage img) {
		Map<String, Integer[]> coorsMap = Resources.getCharacterImageCoorsMap();
//		String map = ImageUtils.getBase64(ImageUtils.getSubImageBlackAndWhite(img, coorsMap.get("Map")));
//		String location = ImageUtils.getBase64(ImageUtils.getSubImage(img, coorsMap.get("Location")));
//		String info = ImageUtils.getBase64(ImageUtils.getSubImage(img, coorsMap.get("Info")));
//		String stats = ImageUtils.getBase64(ImageUtils.getSubImage(img, coorsMap.get("Stats")));
//		String inventory = ImageUtils.getBase64(ImageUtils.getSubImage(img, coorsMap.get("Inventory")));
		JSONArray inventory = ImageUtils.getInventory(ImageUtils.getSubImage(img, coorsMap.get("Inventory")));
//		int points = NumberUtils.detectNumber(ImageUtils.getSubImageBlackAndWhite(img, coorsMap.get("Points")), Resources.getPointDigitsMap());
//		int cash = NumberUtils.detectNumber(ImageUtils.getSubImageBlackAndWhite(img, coorsMap.get("Cash")), Resources.getCashDigitsMap());
		JSONObject result = new JSONObject();
//		result.put("map", map);
//		result.put("location", location);
//		result.put("info", info);
//		result.put("stats", stats);
//		result.put("inventory", inventory);
//		result.put("points", points);
//		result.put("cash", cash);
//		result.put("character", "KailSell");
		result.put("inventory", inventory);
		return result;
	}

	public static void sendCharacter(BufferedImage img) {
		getCharacter(img);
//		RestTemplate template = new RestTemplate();	
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);		
//		HttpEntity<String> request = new HttpEntity<>(getCharacter(img).toString(), headers);
//		template.exchange("http://192.168.1.81:3000/character", HttpMethod.POST, request, String.class);
	}

	public static BufferedImage captureRGB(Robot robot, int[] coors, String name, boolean isCreateFile) {
		Rectangle rect = new Rectangle(coors[0], coors[1], coors[2], coors[3]);
		BufferedImage img = robot.createScreenCapture(rect);
		return draw(img, name, isCreateFile);
	}

	public static BufferedImage capture(Robot robot, Integer[] coors, String path, String name, boolean isCreateFile) {
		Rectangle rect = new Rectangle(coors[0], coors[1], coors[2], coors[3]);
		BufferedImage img = robot.createScreenCapture(rect);
		return ImageUtils.getBlackAndWhiteImage(img);
	}

	public static boolean captureAndCompare(Robot robot, Integer[] coors, File fileA) {
		return ImageUtils.compareImage(fileA, capture(robot, coors, "", "", false), false);
	}

	public static int[] getCoor(int windowCoorsX, int windowCoorsY, Integer[] step) {
		int[] result = { windowCoorsX + step[0], windowCoorsY + step[1] };
		return result;
	}

}
