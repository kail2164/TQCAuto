package auto;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import utils.ImageUtils;
import utils.NumberUtils;

@SpringBootApplication
public class Auto {
	private static int windowX = 0, windowY = 0;
	private static List<Account> listAcc;
	private static Map<String, Integer[]> stepCoorsMap;
	private static Robot robot;
	private static Map<String, File> imageMap;
	private static Map<String, Integer[]> stepImageCoorsMap;
	private static boolean isDrawing = false;
	private static JTextField xPosTextField, yPosTextField;
	private static String gamePath;

	public Auto() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listAcc = Resources.getListAcc1();
		stepCoorsMap = Resources.getStepCoorsMap();
		imageMap = Resources.getImageMap();
		stepImageCoorsMap = Resources.getStepImageCoorsMap();
		try {
			drawFrameBody();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Auto.class).headless(false).run(args);		
	}

	private static void drawFrameBody() throws InterruptedException {
		JFrame frame = new JFrame("Auto TQC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		drawCaptureFrame(mainPanel);
		drawMousePointerCoorsFrame(mainPanel);
		drawItemGenerator(mainPanel);
		drawLoginFrame(mainPanel);
		frame.add(mainPanel);
		frame.setVisible(true);
		frame.setLocation(1400, 200);
		frame.setSize(500, 800);
		frame.setResizable(false);
		updateCoors();
	}
	// Trash:855:515

	private static void drawCaptureFrame(JPanel mainPanel) {
		String[] windows = WindowUtils.getAllWindows(true).stream().filter(window -> !window.getTitle().equals("")).map(window -> window.getTitle()).toArray(String[]::new);
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 470, 120);
		GridLayout grid = new GridLayout(4, 4);
		grid.setHgap(5);
		grid.setVgap(5);
		panel.setLayout(grid);
		JLabel xCoorLabel = new JLabel("X:");
		JTextField xCoor = new JTextField("");
		JLabel yCoorLabel = new JLabel("Y:");
		JTextField yCoor = new JTextField("");
		JLabel wLabel = new JLabel("W:");
		JTextField w = new JTextField("");
		JLabel hLabel = new JLabel("H:");
		JTextField h = new JTextField("");
		JLabel fileNameLabel = new JLabel("File name:");
		JTextField fileName = new JTextField("");
		JLabel windowLabel = new JLabel("Window:");
		JComboBox<String> comboBox = new JComboBox<>(windows);
		panel.add(xCoorLabel);
		panel.add(xCoor);
		panel.add(yCoorLabel);
		panel.add(yCoor);
		panel.add(wLabel);
		panel.add(w);
		panel.add(hLabel);
		panel.add(h);
		panel.add(fileNameLabel);
		panel.add(fileName);
		panel.add(windowLabel);
		panel.add(comboBox);
		JButton btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yCoor.getText().equals("")) {
					yCoor.setText("0");
				}
				if (xCoor.getText().equals("")) {
					xCoor.setText("0");
				}
				if (w.getText().equals("")) {
					w.setText("0");
				}
				if (h.getText().equals("")) {
					h.setText("0");
				}
				Rectangle rect = new Rectangle(windowX + Integer.parseInt(xCoor.getText()),
						windowY + Integer.parseInt(yCoor.getText()), Integer.parseInt(w.getText()),
						Integer.parseInt(h.getText()));
				BufferedImage img = robot.createScreenCapture(rect);
				AutoAction.drawBlackAndWhite(img, "./img\\", fileName.getText(), true);
			}
		});
		JButton btnCaptureAll = new JButton("Send Character");
		btnCaptureAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				Rectangle rect = new Rectangle(windowX,	windowY, 1030,	798);
				BufferedImage img = robot.createScreenCapture(rect);
				AutoAction.sendCharacter(img);
			}
		});
		JButton btnCaptureRGB = new JButton("Capture RGB");
		btnCaptureRGB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (yCoor.getText().equals("")) {
					yCoor.setText("0");
				}
				if (xCoor.getText().equals("")) {
					xCoor.setText("0");
				}
				if (w.getText().equals("")) {
					w.setText("0");
				}
				if (h.getText().equals("")) {
					h.setText("0");
				}
				Rectangle rect = new Rectangle(windowX + Integer.parseInt(xCoor.getText()),
						windowY + Integer.parseInt(yCoor.getText()), Integer.parseInt(w.getText()),
						Integer.parseInt(h.getText()));
				BufferedImage img = robot.createScreenCapture(rect);
				AutoAction.draw(img, fileName.getText(), true);
			}
		});
		JButton btnUpdate = new JButton("Update Coors");
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateWindowCoors((String)comboBox.getSelectedItem());
			}
		});
		panel.add(btnCaptureAll);
		panel.add(btnCaptureRGB);
		panel.add(btnCapture);
		panel.add(btnUpdate);
		panel.setBorder(BorderFactory.createTitledBorder("Capture Frame"));
		mainPanel.add(panel);
	}

	private static void drawMousePointerCoorsFrame(JPanel mainPanel) {
		JPanel panel = new JPanel();
		panel.setBounds(5, 130, 470, 80);
		GridLayout grid = new GridLayout(2, 4);
		grid.setHgap(5);
		grid.setVgap(5);
		panel.setLayout(grid);
		JLabel xLabel = new JLabel("X:");
		xPosTextField = new JTextField("");
		JLabel yLabel = new JLabel("Y:");
		yPosTextField = new JTextField("");
		panel.add(xLabel);
		panel.add(xPosTextField);
		panel.add(yLabel);
		panel.add(yPosTextField);
		JButton btnGetPos = new JButton("Get Position");
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
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(btnGetPos);
		panel.setBorder(BorderFactory.createTitledBorder("Get pointer coors"));
		mainPanel.add(panel);
	}

	private static void drawLoginFrame(JPanel mainPanel) {
		JPanel panel = new JPanel();
		panel.setBounds(5, 215, 470, 80);
		GridLayout grid = new GridLayout(2, 2);
		grid.setHgap(5);
		grid.setVgap(5);
		panel.setLayout(grid);
		JButton btnBrowse = new JButton("Browse Game Path");
		panel.add(btnBrowse);
		JLabel fileNameLabel = new JLabel("File Path:");
		panel.add(fileNameLabel);
		btnBrowse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Exe filter", "exe");
				fileChooser.setFileFilter(filter);
				int returnValue = fileChooser.showOpenDialog(mainPanel);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					gamePath = fileChooser.getSelectedFile().getAbsolutePath();
					fileNameLabel.setText(gamePath);
				}
			}
		});
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(gamePath);
					if (gamePath != null && gamePath.contains(".exe")) {
						for (Account acc : listAcc) {
							startGame(acc);
							login(acc);
						}
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		panel.add(btnLogin);
		panel.setBorder(BorderFactory.createTitledBorder("Login"));
		mainPanel.add(panel);
	}

	private static void drawItemGenerator(JPanel mainPanel) {
		JPanel panel = new JPanel();
		panel.setBounds(5, 300, 470, 120);
		GridLayout grid = new GridLayout(3, 4);
		grid.setHgap(5);
		grid.setVgap(5);
		panel.setLayout(grid);
		JLabel row = new JLabel("Row:");
		JTextField rowT = new JTextField("");
		JLabel col = new JLabel("Column:");
		JTextField colT = new JTextField("");
		JLabel name = new JLabel("Item Name:");
		JTextField nameT = new JTextField("");
		JLabel value = new JLabel("Item Value:");
		JTextField valueT = new JTextField("");
		panel.add(row);
		panel.add(rowT);
		panel.add(col);
		panel.add(colT);
		panel.add(name);
		panel.add(nameT);
		panel.add(value);
		panel.add(valueT);
		JButton btnCapture = new JButton("Capture Item");
		btnCapture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int recX = windowX + 579 + Integer.parseInt(colT.getText()) * 36;
				int recY = windowY + 179 + Integer.parseInt(rowT.getText()) * 36;
				Integer[] coors = { recX, recY, 35, 35 };
				AutoAction.capture(robot, coors, "./img\\items\\", nameT.getText() + "-" + valueT.getText(), true);
			}
		});
		JButton btnCaptureRGB = new JButton("Capture RGB");
		btnCaptureRGB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int recX = windowX + 579 + Integer.parseInt(colT.getText()) * 36;
				int recY = windowY + 179 + Integer.parseInt(rowT.getText()) * 36;
				int[] coors = { recX, recY, 35, 35 };
				AutoAction.captureRGB(robot, coors, nameT.getText() + "-RGB-" + valueT.getText(),
						true);
			}
		});
		JButton btnCountRGB = new JButton("Count Item");
		btnCountRGB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int y = 0; y < 8; y++) {
					for (int x = 0; x < 10; x++) {
						int recX = windowX + 579 + x * 36;
						int recY = windowY + 179 + y * 36;
						int[] coors = { recX, recY, 35, 35 };
						String item = ImageUtils.countRGB(robot, coors);
						if (!item.equals("empty")) {
							System.out.print("[" + item + "]");
						} else {
							System.out.print("[ ]");
						}
					}
				}
			}
		});
		JButton btnValue = new JButton("Get Value");
		btnValue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				try {
//					for (int row = 0; row < 4; row++) {
//						String rowValue = "";
//						for (int col = 0; col < 10; col++) {
////							rowValue += AutoAction.getItemAmount(robot, windowX, windowY, col, row, listNumbers) + "||";
////							System.out.println("Value: " +AutoAction.getItemAmount(robot, windowX, windowY,Integer.parseInt(colT.getText()), Integer.parseInt(rowT.getText()), listNumbers));
//						}
//						System.out.println(rowValue);
//					}
//				} catch (NumberFormatException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		panel.add(btnCountRGB);
		panel.add(btnCaptureRGB);
		panel.add(btnCapture);
		panel.add(btnValue);
		panel.setBorder(BorderFactory.createTitledBorder("Capture Item"));
		mainPanel.add(panel);
	}

	private static void changeTitle(String titleToChange) throws IOException {
		Runtime.getRuntime().exec("./ChangeTitle.exe TQC-" + titleToChange);
	}

	private static void startGame(Account acc) throws IOException, InterruptedException {
		Desktop.getDesktop().open(new File(gamePath));
		boolean isAbleToClick = false;
		for (int i = 0; i < 5; i++) {
			Thread.sleep(500);
			isAbleToClick = WindowUtils.getAllWindows(true).stream()
					.filter(window -> window.getTitle().contains("Script")).collect(Collectors.toList()).size() > 0;
			if (isAbleToClick) {
				updateWindowCoors("Script");
				AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("LoaderNo"));
				isAbleToClick = false;
			}
		}
		isAbleToClick = false;
		updateWindowCoors("Sanol");
		Integer[] coors = stepImageCoorsMap.get("LoaderServer");
		while (!isAbleToClick) {
			Thread.sleep(500);
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
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("LoaderServer"));
		isAbleToClick = false;
		Thread.sleep(2000);
		updateWindowCoors("Three");
		coors = stepImageCoorsMap.get("GameAccept");
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
		
		Integer[] coors = stepImageCoorsMap.get("GameTamQuocServer");
		while (!isAbleToClick) {
			Thread.sleep(500);
			AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameTamQuocServer"));
			System.out.println("Win x: " + windowX + " window y: " + windowY + " coors: " + Arrays.toString(coors));
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameTamQuocServer"));
		}
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameTamQuocServer"));
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameID"));
		AutoAction.type(robot, acc.getUsername());
		Thread.sleep(200);
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GamePW"));
		AutoAction.type(robot, acc.getPassword());
		Thread.sleep(200);
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameLogin"));
		coors = stepImageCoorsMap.get("GameSelectSub");
		while (!isAbleToClick) {
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("GameSelectSub"));
		}
		isAbleToClick = false;
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameSub2"));
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("GameSelectSub"));
		coors = stepImageCoorsMap.get("CharAccept");
		while (!isAbleToClick) {
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("CharAccept"));
		}
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("CharAccept"));
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("CharPlay"));
		coors = stepImageCoorsMap.get("AutoStart");
		while (!isAbleToClick) {
			Thread.sleep(200);
			isAbleToClick = AutoAction.captureAndCompare(robot, coors, imageMap.get("AutoStart"));
		}
		Thread.sleep(2000);
		AutoAction.toggleAuto(robot);
		AutoAction.click(robot, windowX, windowY, stepCoorsMap.get("AutoStart"));
		AutoAction.toggleAuto(robot);
		AutoAction.toggleInventory(robot);
		robot.mouseMove(400 + windowX, 100 + windowY);
	}

	private static void updateWindowCoors(String name) {
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
			if (desktopWindow.getTitle().contains(name)) {
				Rectangle rec = desktopWindow.getLocAndSize();
				windowX = rec.x;
				windowY = rec.y;
				NumberUtils.updateCoors(stepImageCoorsMap, windowX, windowY);
				return;
			}
		});
	}

	private static void updateCoors() throws InterruptedException {
		while (true) {
			Thread.sleep(1000);
			while (isDrawing) {
				Thread.sleep(100);
				xPosTextField.setText(MouseInfo.getPointerInfo().getLocation().getX() - windowX + "");
				yPosTextField.setText(MouseInfo.getPointerInfo().getLocation().getY() - windowY + "");
			}
		}
	}

	private static void showWindow(HWND hWnd) {
		User32 user32 = User32.INSTANCE;
		if (user32.IsWindowVisible(hWnd)) {
			user32.ShowWindow(hWnd, User32.SW_SHOWNORMAL);
			user32.SetFocus(hWnd);
		}
	}

}
