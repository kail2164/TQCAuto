package auto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.FileUtils;

public class Resources {

	private static Map<String, File> imageMap;
	private static Map<Integer, BufferedImage> cashDigitsMap;
	private static Map<Integer, BufferedImage> pointDigitsMap;
	private static Map<String, Integer[]> stepImageCoorsMap;
	private static Map<String, Integer[]> characterImageCoorsMap;

	private static Map<String, Integer[]> stepCoorsMap;
	private static List<Account> listAcc1;
	private static List<Account> listAcc2;

	static {
		initImageMap();
		try {
			initCashDigitsMap();
			initPointDigitsMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initListAcc();
		initListAcc2();
		initStepCoorsMap();
		initCharacterImageCoorsMap();
		initStepImageCoorsMap();
		initStepCoorsMap();
	}
	

	public static Map<String, Integer[]> getStepImageCoorsMap() {
		return stepImageCoorsMap;
	}

	public static Map<String, Integer[]> getCharacterImageCoorsMap() {
		return characterImageCoorsMap;
	}

	public static Map<Integer, BufferedImage> getPointDigitsMap() {
		return pointDigitsMap;
	}

	public static List<Account> getListAcc1() {
		return listAcc1;
	}

	public static List<Account> getListAcc2() {
		return listAcc2;
	}

	public static Map<String, File> getImageMap() {
		return imageMap;
	}

	public static Map<Integer, BufferedImage> getCashDigitsMap() {
		return cashDigitsMap;
	}

	public static Map<String, Integer[]> getStepCoorsMap() {
		return stepCoorsMap;
	}

	private static void initImageMap() {
		Map<String, File> map = new HashMap<>();
		map.put("LoaderNo", new File("./img\\LoaderNo.png"));
		map.put("LoaderServer", new File("./img\\LoaderServer.png"));
		map.put("GameAccept", new File("./img\\GameAccept.png"));
		map.put("GameTamQuocServer", new File("./img\\GameTamQuocServer.png"));
		map.put("GameSelectSub", new File("./img\\GameSelectSub.png"));
		map.put("CharAccept", new File("./img\\CharAccept.png"));
		map.put("AutoStart", new File("./img\\AutoStart.png"));
		imageMap = map;
	}

	private static void initCashDigitsMap() throws IOException {
		Map<Integer, BufferedImage> map = new HashMap<>();
		map.put(0, FileUtils.getImage("./img\\numbers\\0-inv-digit.png"));
		map.put(1, FileUtils.getImage("./img\\numbers\\1-inv-digit.png"));
		map.put(2, FileUtils.getImage("./img\\numbers\\2-inv-digit.png"));
		map.put(3, FileUtils.getImage("./img\\numbers\\3-inv-digit.png"));
		map.put(4, FileUtils.getImage("./img\\numbers\\4-inv-digit.png"));
		map.put(5, FileUtils.getImage("./img\\numbers\\5-inv-digit.png"));
		map.put(6, FileUtils.getImage("./img\\numbers\\6-inv-digit.png"));
		map.put(7, FileUtils.getImage("./img\\numbers\\7-inv-digit.png"));
		map.put(8, FileUtils.getImage("./img\\numbers\\8-inv-digit.png"));
		map.put(9, FileUtils.getImage("./img\\numbers\\9-inv-digit.png"));
		cashDigitsMap = map;
	}

	private static void initPointDigitsMap() throws IOException {
		Map<Integer, BufferedImage> map = new HashMap<>();
		map.put(0, FileUtils.getImage("./img\\numbers\\0-digit.png"));
		map.put(1, FileUtils.getImage("./img\\numbers\\1-digit.png"));
		map.put(2, FileUtils.getImage("./img\\numbers\\2-digit.png"));
		map.put(3, FileUtils.getImage("./img\\numbers\\3-digit.png"));
		map.put(4, FileUtils.getImage("./img\\numbers\\4-digit.png"));
		map.put(5, FileUtils.getImage("./img\\numbers\\5-digit.png"));
		map.put(6, FileUtils.getImage("./img\\numbers\\6-digit.png"));
		map.put(7, FileUtils.getImage("./img\\numbers\\7-digit.png"));
		map.put(8, FileUtils.getImage("./img\\numbers\\8-digit.png"));
		map.put(9, FileUtils.getImage("./img\\numbers\\9-digit.png"));
		pointDigitsMap = map;
	}

	private static void initStepImageCoorsMap() {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("LoaderNo", new Integer[] { 5, 30, 360, 180 });
		map.put("LoaderServer", new Integer[] { 525, 500, 115, 60 });
		map.put("GameAccept", new Integer[] { 280, 230, 475, 20 });
		map.put("GameTamQuocServer", new Integer[] { 430, 660, 180, 20 });
		map.put("GameSelectSub", new Integer[] { 420, 605, 200, 25 });
		map.put("CharAccept", new Integer[] { 360, 340, 320, 150 });
		map.put("AutoStart", new Integer[] { 745, 770, 270, 20 });	
		stepImageCoorsMap = map;
	}

	private static void initCharacterImageCoorsMap() {
		Map<String, Integer[]> map = new HashMap<>();		
		map.put("Map", new Integer[] { 30, 622, 220, 20 });
		map.put("Location", new Integer[] { 755, 730, 272, 40 });
		map.put("Info", new Integer[] { 15, 655, 260, 140 });
		map.put("Stats", new Integer[] { 106, 297, 128, 132 });
		map.put("Inventory", new Integer[] { 579, 179, 360, 288 });
		map.put("Cash", new Integer[] { 635, 499, 130, 9 });
		map.put("Points", new Integer[] { 213, 454, 27, 10 });
		map.put("ItemAmount", new Integer[] { 0, 20, 35, 15 });
		map.put("Level", new Integer[] { 195, 165, 30, 10 });
		characterImageCoorsMap = map;
	}
	private static void initListAcc2() {
		List<Account> listAcc = new ArrayList<Account>();
		Account acc = new Account("kail14", "12345678@Ab", "K01");
		listAcc.add(acc);
		listAcc2 = listAcc;
	}

	private static void initListAcc() {
		List<Account> listAcc = new ArrayList<Account>();
		Account acc = new Account("headshot1st", "123456789", "K01");
		listAcc.add(acc);
		acc = new Account("natso1st", "123456789", "K02");
		listAcc.add(acc);
		acc = new Account("tetso1st", "123456789", "K03");
		listAcc.add(acc);
		acc = new Account("headshot2nd", "123456789", "K04");
		listAcc.add(acc);
		acc = new Account("natso2nd", "123456789", "K05");
		listAcc.add(acc);
		acc = new Account("tetso2nd", "123456789", "K06");
		listAcc.add(acc);
		acc = new Account("headshot3rd", "123456", "K07");
		listAcc.add(acc);
		acc = new Account("natso3rd", "123456", "K08");
		listAcc.add(acc);
		acc = new Account("tetso3rd", "123456", "K09");
		listAcc.add(acc);
		acc = new Account("kail2166", "123456", "Kail10");
		listAcc.add(acc);
		acc = new Account("kail2167", "123456", "Kail11");
		listAcc.add(acc);
		acc = new Account("kail2168", "123456", "Kail12");
		listAcc.add(acc);
		acc = new Account("kail2169", "123456", "Kail13");
		listAcc.add(acc);
		acc = new Account("kail2170", "123456", "Kail14");
		listAcc.add(acc);
		acc = new Account("kail01", "123456", "Kail15");
		listAcc.add(acc);
		acc = new Account("kail02", "123456", "Kail16");
		listAcc.add(acc);
		acc = new Account("kail03", "123456", "Kail17");
		listAcc.add(acc);
		acc = new Account("kail04", "123456", "Kail18");
		listAcc.add(acc);
		acc = new Account("kail2164", "123456789", "Kail");
		listAcc.add(acc);
		listAcc1 = listAcc;
	}

	private static void initStepCoorsMap() {
		Map<String, Integer[]> map = new HashMap<>();
		map.put("LoaderNo", new Integer[] { 235, 250 });
		map.put("LoaderServer", new Integer[] { 580, 530 });
		map.put("GameAccept", new Integer[] { 475, 570 });
		map.put("GameTamQuocServer", new Integer[] { 450, 300 });
		map.put("GameID", new Integer[] { 475, 705 });
		map.put("GamePW", new Integer[] { 475, 735 });
		map.put("GameLogin", new Integer[] { 460, 670 });
		map.put("GameSub1", new Integer[] { 500, 300 });
		map.put("GameSub2", new Integer[] { 500, 330 });
		map.put("GameSub3", new Integer[] { 500, 360 });
		map.put("GameSelectSub", new Integer[] { 450, 610 });
		map.put("CharAccept", new Integer[] { 515, 460 });
		map.put("CharPlay", new Integer[] { 925, 425 });
		map.put("AutoStart", new Integer[] { 598, 168 });
		map.put("SortInv", new Integer[] { 860, 150 });
		map.put("Remove", new Integer[] { 855, 515 });
		stepCoorsMap = map;
	}
}
