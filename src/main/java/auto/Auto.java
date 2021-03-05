package auto;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Auto {
	private static int windowX = 0, windowY = 0;
	private static List<Account> listAcc;
	private static List<AutoStep> listSteps;
	private static Robot robot;
	private static Map<String, File> imageMap;
	private static List<String> listImageCoors;

	// Trash:855:515
	private static void updateWindowCoors(String name) {
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
			if (desktopWindow.getTitle().contains(name)) {
				Rectangle rec = desktopWindow.getLocAndSize();
				windowX = rec.x;
				windowY = rec.y;
				return;
			}
		});
	}

	private static void drawFrameBody() throws InterruptedException {
		JFrame frame = new JFrame("Auto TQC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panelTop = new JPanel(new FlowLayout());
		JLabel xCoorLabel = new JLabel("X:");
		JTextField xCoor = new JTextField("", 5);
		JLabel yCoorLabel = new JLabel("Y:");
		JTextField yCoor = new JTextField("", 5);
		JLabel wLabel = new JLabel("W:");
		JTextField w = new JTextField("", 5);
		JLabel hLabel = new JLabel("H:");
		JTextField h = new JTextField("", 5);
		JLabel fileNameLabel = new JLabel("File:");
		JTextField fileName = new JTextField("", 10);
		JLabel windowLabel = new JLabel("Window:");
		JTextField window = new JTextField("", 10);
		panelTop.add(xCoorLabel);
		panelTop.add(xCoor);
		panelTop.add(yCoorLabel);
		panelTop.add(yCoor);
		panelTop.add(wLabel);
		panelTop.add(w);
		panelTop.add(hLabel);
		panelTop.add(h);
		panelTop.add(fileNameLabel);
		panelTop.add(fileName);
		panelTop.add(windowLabel);
		panelTop.add(window);
		frame.add(panelTop);

		frame.setLayout(new FlowLayout());
		JLabel xLabel = new JLabel("X:");
		JTextField x = new JTextField("", 5);
		JLabel yLabel = new JLabel("Y:");
		JTextField y = new JTextField("", 5);
		frame.add(xLabel);
		frame.add(x);
		frame.add(yLabel);
		frame.add(y);

		JButton button = new JButton("Capture");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Rectangle rect = new Rectangle(windowX + Integer.parseInt(xCoor.getText()),
						windowY + Integer.parseInt(yCoor.getText()), Integer.parseInt(w.getText()),
						Integer.parseInt(h.getText()));
				BufferedImage img = robot.createScreenCapture(rect);
				AutoAction.drawBlackAndWhite(img, "D:\\Projects\\AutoTQC\\src\\main\\java\\img\\", fileName.getText());
//				System.out.println("is the same: " + AutoAction.compareImage(, f));
//				for (int y = 0; y < 4; y++) {
//					for (int x = 0; x < 10; x++) {
//						Rectangle rect = new Rectangle(windowX + 579 + 36 * x, windowY + 179 + 36 * y, 35, 35);
//						BufferedImage img = robot.createScreenCapture(rect);
//						File f = new File("myimagex" + x + "y" + y + ".jpg");
//						try {
//							ImageIO.write(img, "jpg", f);
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//					}
//				}
			}
		});
		frame.add(button);
		JButton button2 = new JButton("Login");
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
//					for (Account acc : listAcc) {
					startGame(listAcc.get(0));
						login(listAcc.get(0));
//					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(button2);
		JButton button3 = new JButton("Update Coors");
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWindowCoors(window.getText());
			}
		});
		frame.add(button3);
		frame.pack();
		frame.setVisible(true);
//		frame.setLocation(1500, 200);
//		frame.setSize(400, 800);
		updateCoors(x, y);
	}

	public static void updateCoors(JTextField x, JTextField y) throws InterruptedException {
		while (true) {
			Thread.sleep(100);
			x.setText(MouseInfo.getPointerInfo().getLocation().getX() - windowX + "");
			y.setText(MouseInfo.getPointerInfo().getLocation().getY() - windowY + "");
//			x.setText(MouseInfo.getPointerInfo().getLocation().getX()+ "");
//			y.setText(MouseInfo.getPointerInfo().getLocation().getY()+ "");
		}
	}
	
	private static void startGame(Account acc) throws IOException, InterruptedException {
		Desktop.getDesktop().open(new File("D:\\Games\\Dzogame_TamQuocChi_V16\\Loader.exe"));
		Thread.sleep(500);
		boolean isAbleToClick = false;
		updateWindowCoors("Script");		
		int[] coors = AutoAction.convertToIntArr(listImageCoors.get(0), windowX, windowY);
		while (!isAbleToClick) {		
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("LoaderNo"));
		}		
		AutoAction.click(robot, windowX, windowY, listSteps.get(0));
		isAbleToClick = false;
		updateWindowCoors("Sanol");
		coors = AutoAction.convertToIntArr(listImageCoors.get(1), windowX, windowY);
		while (!isAbleToClick) {			
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("LoaderServer"));
		}
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
			if (desktopWindow.getTitle().contains("Sanol") && acc.getWindow() == null) {
				Rectangle rec = desktopWindow.getLocAndSize();
				acc.setWindow(desktopWindow);				
				acc.setWindowX(rec.x);
				acc.setWindowY(rec.y);
			}
		});
		AutoAction.click(robot, windowX, windowY, listSteps.get(1));
		isAbleToClick = false;
		Thread.sleep(2000);
		updateWindowCoors("Three");
		coors = AutoAction.convertToIntArr(listImageCoors.get(2), windowX, windowY);
		while (!isAbleToClick) {			
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameAccept"));
		}
		AutoAction.click(robot, windowX, windowY, listSteps.get(2));
		for(int i = 0; i < 2; i++) {
			Thread.sleep(1500);
			AutoAction.click(robot, windowX, windowY, listSteps.get(2));
		}
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
			if (desktopWindow.getTitle().contains("Three") && acc.getWindow() == null) {
				Rectangle rec = desktopWindow.getLocAndSize();
				acc.setWindow(desktopWindow);
				windowX = rec.x;
				windowY = rec.y;
				acc.setWindowX(rec.x);
				acc.setWindowY(rec.y);
			}
		});		
	}

	private static void login(Account acc) throws IOException, InterruptedException {
		Thread.sleep(500);
		boolean isAbleToClick = false;
		int[] coors = AutoAction.convertToIntArr(listImageCoors.get(3), windowX, windowY);
		while (!isAbleToClick) {		
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameTamQuocServer"));
		}		
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, listSteps.get(3));
		AutoAction.click(robot, windowX, windowY, listSteps.get(4));
		AutoAction.type(robot, acc.getUsername());
		Thread.sleep(200);
		AutoAction.click(robot, windowX, windowY, listSteps.get(5));
		AutoAction.type(robot, acc.getPassword());
		Thread.sleep(200);
		AutoAction.click(robot, windowX, windowY, listSteps.get(6));
		coors = AutoAction.convertToIntArr(listImageCoors.get(4), windowX, windowY);
		while (!isAbleToClick) {			
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameSelectSub"));
		}
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, listSteps.get(7));
		AutoAction.click(robot, windowX, windowY, listSteps.get(8));
		coors = AutoAction.convertToIntArr(listImageCoors.get(5), windowX, windowY);
		while (!isAbleToClick) {			
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("CharAccept"));
		}
		AutoAction.click(robot, windowX, windowY, listSteps.get(9));
		AutoAction.click(robot, windowX, windowY, listSteps.get(10));
		coors = AutoAction.convertToIntArr(listImageCoors.get(6), windowX, windowY);
		while (!isAbleToClick) {			
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("AutoStart"));
		}
		Thread.sleep(3000);
		AutoAction.toggleAuto(robot);
		AutoAction.click(robot, windowX, windowY, listSteps.get(11));
		AutoAction.toggleAuto(robot);
		AutoAction.toggleInventory(robot);

	}

	private static void showWindow(HWND hWnd) {
		User32 user32 = User32.INSTANCE;
		if (user32.IsWindowVisible(hWnd)) {
			user32.ShowWindow(hWnd, User32.SW_SHOWNORMAL);
			user32.SetFocus(hWnd);
		}
	}

	public static void main(String[] args) throws AWTException, InterruptedException {
		robot = new Robot();
		listAcc = InitResources.initListAcc();
		listSteps = InitResources.initListStep();
		imageMap = InitResources.initImageMap();
		listImageCoors = InitResources.initListImageCoors();
		drawFrameBody();
	}
}
