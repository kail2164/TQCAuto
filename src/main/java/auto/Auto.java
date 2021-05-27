package auto;

import java.awt.AWTException;
import java.awt.BorderLayout;
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
import java.util.stream.Collectors;

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
	private static boolean isDrawing = false;
	private static JTextField xPosTextField, yPosTextField;
	private static List<File> listNumbers;
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

	private static JFrame drawCaptureFrame(JFrame frame) {
		JLabel xCoorLabel = new JLabel("X:");
		xCoorLabel.setBounds(5, 5, 15, 20);
		JTextField xCoor = new JTextField("", 5);
		xCoor.setBounds(20, 5, 80, 20);
		JLabel yCoorLabel = new JLabel("Y:");
		yCoorLabel.setBounds(105, 5, 15, 20);
		JTextField yCoor = new JTextField("", 5);
		yCoor.setBounds(120, 5, 80, 20);
		JLabel wLabel = new JLabel("W:");
		wLabel.setBounds(205, 5, 15, 20);
		JTextField w = new JTextField("", 5);
		w.setBounds(225, 5, 80, 20);
		JLabel hLabel = new JLabel("H:");
		hLabel.setBounds(310, 5, 15, 20);
		JTextField h = new JTextField("", 5);
		h.setBounds(325, 5, 80, 20);
		JLabel fileNameLabel = new JLabel("File name:");
		fileNameLabel.setBounds(5, 30, 60, 20);
		JTextField fileName = new JTextField("", 10);
		fileName.setBounds(65, 30, 80, 20);
		JLabel windowLabel = new JLabel("Window:");
		windowLabel.setBounds(5, 55, 50, 20);
		JTextField window = new JTextField("", 10);
		window.setBounds(65, 55, 80, 20);
		frame.add(xCoorLabel);
		frame.add(xCoor);
		frame.add(yCoorLabel);
		frame.add(yCoor);
		frame.add(wLabel);
		frame.add(w);
		frame.add(hLabel);
		frame.add(h);
		frame.add(fileNameLabel);
		frame.add(fileName);
		frame.add(windowLabel);
		frame.add(window);
		JButton btnCapture = new JButton("Capture");
		btnCapture.setBounds(150, 30, 80, 20);
		btnCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Rectangle rect = new Rectangle(windowX + Integer.parseInt(xCoor.getText()),
						windowY + Integer.parseInt(yCoor.getText()), Integer.parseInt(w.getText()),
						Integer.parseInt(h.getText()));
				BufferedImage img = robot.createScreenCapture(rect);
				AutoAction.drawBlackAndWhite(img, "D:\\Code\\TQCAuto\\src\\main\\java\\img\\", fileName.getText(), true);
			}
		});
		frame.add(btnCapture);
		JButton btnUpdate = new JButton("Update Coors");
		btnUpdate.setBounds(150, 55, 115, 20);
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWindowCoors(window.getText());
			}
		});
		frame.add(btnUpdate);
		return frame;
	}

	private static JFrame drawMousePointerCoorsFrame(JFrame frame) {
		JLabel xLabel = new JLabel("X:");
		xLabel.setBounds(5, 80, 15, 20);
		xPosTextField = new JTextField("", 5);
		xPosTextField.setBounds(20, 80, 80, 20);
		JLabel yLabel = new JLabel("Y:");
		yLabel.setBounds(105, 80, 15, 20);
		yPosTextField = new JTextField("", 5);
		yPosTextField.setBounds(125, 80, 80, 20);
		frame.add(xLabel);
		frame.add(xPosTextField);
		frame.add(yLabel);
		frame.add(yPosTextField);
		JButton btnGetPos = new JButton("Get Position");
		btnGetPos.setBounds(210, 80, 120, 20);
		btnGetPos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (btnGetPos.getText().equals("Get Position")) {
						btnGetPos.setText("Stop");
						isDrawing = true;
					} else {
						btnGetPos.setText("Get Position");
						isDrawing = false;
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(btnGetPos);
		return frame;
	}

	private static JFrame drawLoginFrame(JFrame frame) {
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(5, 105, 80, 20);
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for (Account acc : listAcc) {
						startGame(acc);
						login(acc);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(btnLogin);
		return frame;
	}

	private static JFrame drawItemGenerator(JFrame frame) {
		JLabel row = new JLabel("Row:");
		row.setBounds(5, 130, 30, 20);
		JTextField rowT = new JTextField("", 5);
		rowT.setBounds(40, 130, 80, 20);
		JLabel col = new JLabel("Column:");
		col.setBounds(125, 130, 50, 20);
		JTextField colT = new JTextField("", 5);
		colT.setBounds(175, 130, 80, 20);
		JLabel name = new JLabel("Item Name:");
		name.setBounds(5, 155, 70, 20);
		JTextField nameT = new JTextField("", 5);
		nameT.setBounds(75, 155, 80, 20);
		JLabel value = new JLabel("Item Value:");
		value.setBounds(160, 155, 70, 20);
		JTextField valueT = new JTextField("", 5);
		valueT.setBounds(230, 155, 80, 20);
		frame.add(row);
		frame.add(rowT);
		frame.add(col);
		frame.add(colT);
		frame.add(name);
		frame.add(nameT);
		frame.add(value);
		frame.add(valueT);
		JButton btnCapture = new JButton("Capture Item");
		btnCapture.setBounds(315, 155, 107, 20);
		btnCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int recX = windowX + 579 + Integer.parseInt(colT.getText())*36;
				int recY = windowY + 179 + Integer.parseInt(rowT.getText())*36;
				int[] coors = { recX, recY, 35 ,35 };
				AutoAction.capture(robot, coors, "D:\\Code\\TQCAuto\\src\\main\\java\\img\\items\\", nameT.getText() + "-" + valueT.getText(), true);				
			}
		});
		frame.add(btnCapture);
		JButton btnValue = new JButton("Get Value");
		btnValue.setBounds(5, 180, 110, 20);
		btnValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				try {
					for(int row =0; row < 4; row++) {
						String rowValue = "";
						for(int col =0; col < 10; col++) {
							rowValue += AutoAction.getItemAmount(robot, windowX, windowY, col, row, listNumbers) + "||";
//							System.out.println("Value: " +AutoAction.getItemAmount(robot, windowX, windowY,Integer.parseInt(colT.getText()), Integer.parseInt(rowT.getText()), listNumbers));
						}
						System.out.println(rowValue);
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
			}
		});
		frame.add(btnValue);
		return frame;
	}

	private static void drawFrameBody() throws InterruptedException {
		JFrame frame = new JFrame("Auto TQC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame = drawCaptureFrame(frame);
		frame = drawMousePointerCoorsFrame(frame);
		frame = drawLoginFrame(frame);
		frame = drawItemGenerator(frame);
		frame.setVisible(true);
		frame.setLocation(1400, 200);
		frame.setSize(440, 800);
		frame.setResizable(false);
		updateCoors();
	}

	public static void updateCoors() throws InterruptedException {
		while (true) {
			Thread.sleep(1000);
			while (isDrawing) {
				Thread.sleep(100);
				xPosTextField.setText(MouseInfo.getPointerInfo().getLocation().getX() - windowX + "");
				yPosTextField.setText(MouseInfo.getPointerInfo().getLocation().getY() - windowY + "");
			}
		}
	}

	private static void changeTitle(String titleToChange) throws IOException {
		Runtime.getRuntime().exec("D:\\Code\\TQCAuto\\ChangeTitle.exe TQC-" + titleToChange);
	}

	private static void startGame(Account acc) throws IOException, InterruptedException {
		Desktop.getDesktop().open(new File("D:\\Game\\TQC\\Loader.exe"));
		Thread.sleep(1000);	
		boolean isAbleToClick = false, doneScriptError = false;		
		while (!doneScriptError) {
			isAbleToClick = WindowUtils.getAllWindows(true).stream().filter(window -> window.getTitle().contains("Script")).collect(Collectors.toList()).size() > 0;		
			if(isAbleToClick) {
				updateWindowCoors("Script");
				AutoAction.click(robot, windowX, windowY, listSteps.get(0));				
			}
			Thread.sleep(500);		
			doneScriptError = WindowUtils.getAllWindows(true).stream().filter(window -> window.getTitle().contains("Script")).collect(Collectors.toList()).size() == 0;
		}		
		updateWindowCoors("Sanol");
		int[] coors = AutoAction.convertToIntArr(listImageCoors.get(1), windowX, windowY);
		while (!isAbleToClick) {
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
			Thread.sleep(500);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameAccept"));
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
		changeTitle(acc.getCharName());
	}

	private static void login(Account acc) throws IOException, InterruptedException {
		boolean isAbleToClick = false;
		int[] coors = AutoAction.convertToIntArr(listImageCoors.get(3), windowX, windowY);
		while (!isAbleToClick) {
			Thread.sleep(500);
			AutoAction.click(robot, windowX, windowY, listSteps.get(2));
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameTamQuocServer"));
		}
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, listSteps.get(3));
		AutoAction.click(robot, windowX, windowY, listSteps.get(4));
		AutoAction.type(robot, acc.getUsername());
		Thread.sleep(100);
		AutoAction.click(robot, windowX, windowY, listSteps.get(5));
		AutoAction.type(robot, acc.getPassword());
		Thread.sleep(100);
		AutoAction.click(robot, windowX, windowY, listSteps.get(6));
		coors = AutoAction.convertToIntArr(listImageCoors.get(4), windowX, windowY);
		while (!isAbleToClick) {
			Thread.sleep(100);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameSelectSub"));
		}
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, listSteps.get(7));
		AutoAction.click(robot, windowX, windowY, listSteps.get(8));
		coors = AutoAction.convertToIntArr(listImageCoors.get(5), windowX, windowY);
		while (!isAbleToClick) {
			Thread.sleep(100);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("CharAccept"));
		}
		AutoAction.click(robot, windowX, windowY, listSteps.get(9));
		AutoAction.click(robot, windowX, windowY, listSteps.get(10));
		coors = AutoAction.convertToIntArr(listImageCoors.get(6), windowX, windowY);
		while (!isAbleToClick) {
			Thread.sleep(100);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("AutoStart"));
		}
		Thread.sleep(2500);
		AutoAction.toggleAuto(robot);
		AutoAction.click(robot, windowX, windowY, listSteps.get(11));
		AutoAction.toggleAuto(robot);
		AutoAction.toggleInventory(robot);
		robot.mouseMove(400 + windowX, 100 + windowY);
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
		listNumbers = InitResources.initListNumber();
		listImageCoors = InitResources.initListImageCoors();
		drawFrameBody();
	}
}
