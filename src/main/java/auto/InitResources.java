package auto;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InitResources {
	
	public static Map<String,File>  initImageMap() {
		Map<String, File> map = new HashMap<>();
		map.put("LoaderNo", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\LoaderNo.jpg"));
		map.put("LoaderServer", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\LoaderServer.jpg"));
		map.put("GameAccept", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\GameAccept.jpg"));
		map.put("GameTamQuocServer", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\GameTamQuocServer.jpg"));
		map.put("GameSelectSub", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\GameSelectSub.jpg"));
		map.put("CharAccept", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\CharAccept.jpg"));
		map.put("AutoStart", new File("D:\\Projects\\AutoTQC\\src\\main\\java\\img\\AutoStart.jpg"));
		return map;
	}
	public static List<String> initListImageCoors(){
		List<String> result = new ArrayList<>();
		result.add("5,30,360,180");
		result.add("525,500,115,60");
		result.add("280,230,475,20");
		result.add("410,650,220,30");
		result.add("390,595,260,40");
		result.add("360,340,320,150");
		result.add("745,770,270,20");
		return result;
	}
	public static List<Account> initListAcc() {
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
		return listAcc;
	}

	public static List<AutoStep> initListStep() {
		List<AutoStep>	listSteps = new ArrayList<>();
		AutoStep step = new AutoStep("LoaderNo", 375, 400, 0, 0);
		listSteps.add(step);
		step = new AutoStep("LoaderServer", 580, 530, 0, 0);
		listSteps.add(step);
		step = new AutoStep("GameAccept", 475, 570, 0, 1700);
		step.setDelayTimes(2);
		listSteps.add(step);
		step = new AutoStep("GameTamQuocServer", 450, 300, 0, 0);
		listSteps.add(step);
		step = new AutoStep("GameID", 475, 705, 0, 0);
		listSteps.add(step);
		step = new AutoStep("GamePW", 475, 735, 0, 0);
		listSteps.add(step);
		step = new AutoStep("GameLogin", 460, 670, 0, 0);
		listSteps.add(step);
//		step = new AutoStep("GameSub1", 500, 300, 2000, 0);
//		listSteps.add(step);
//		step = new AutoStep("GameSub2", 500, 330, 2000, 0);
//		listSteps.add(step);
		step = new AutoStep("GameSub3", 500, 360, 0, 0);
		listSteps.add(step);
		step = new AutoStep("GameSelectSub", 450, 610, 0, 0);
		listSteps.add(step);
		step = new AutoStep("CharAccept", 515, 460, 0, 0);
		listSteps.add(step);
		step = new AutoStep("CharPlay", 925, 425, 0, 0);
		listSteps.add(step);
		step = new AutoStep("AutoStart", 598, 168, 0, 0);
		listSteps.add(step);
		return listSteps;
	}
}
