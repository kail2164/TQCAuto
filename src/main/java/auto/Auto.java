package auto;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.sun.jna.platform.WindowUtils;

public class Auto {
	private static int windowX = 0, windowY = 0;
	private static List<Account> listAcc;
	private static List<String> listPos;
	
	private static void initListAcc() {
		listAcc = new ArrayList<Account>();
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
		acc = new Account("headshot3rd", "123456789", "K07");
		listAcc.add(acc);
		acc = new Account("natso3rd", "123456789", "K08");
		listAcc.add(acc);
		acc = new Account("tetso3rd", "123456789", "K09");
		listAcc.add(acc);
	}
	
	private static void initListPos() {
		listPos = new ArrayList<>();
		String pos = "LoaderNo:415:400";
		listPos.add(pos);
		pos = "LoaderServer:580:530";
		listPos.add(pos);
		pos = "GameAccept:475:570";
		listPos.add(pos);
		pos = "GameTamQuocServer:450:300";
		listPos.add(pos);
		pos = "GameID:475:705";
		listPos.add(pos);
		pos = "GamePW:475:735";
		listPos.add(pos);
		pos = "GameLogin:460:670";
		listPos.add(pos);
		pos = "GameSub1:500:300";
		listPos.add(pos);
		pos = "GameSub2:500:330";
		listPos.add(pos);
		pos = "GameSub3:500:360";
		listPos.add(pos);
		pos = "GameSelectSub:450:610";
		listPos.add(pos);
		pos = "CharAccept:515:460";
		listPos.add(pos);
		pos = "CharPlay:925:425";
		listPos.add(pos);
		pos = "AutoStart:598:168";
		listPos.add(pos);
		pos = "Trash:855:515";
		listPos.add(pos);
	}
	
	
	
	private static void drawFrameBody() {
		JFrame frame = new JFrame("Auto TQC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		JLabel xLabel = new JLabel("X:");
//		xLabel.setBounds(30, 50, 20, 30);
		JTextField x = new JTextField("", 5);
//		x.setBounds(50, 50, 80, 30);
		JLabel yLabel = new JLabel("Y:");
//		yLabel.setBounds(120, 50, 20, 30);
		JTextField y = new JTextField("", 5);
//		y.setBounds(160, 50, 80, 30);
		frame.add(xLabel);
		frame.add(x);
		frame.add(yLabel);
		frame.add(y);
		
		JButton button = new JButton("Capture");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			     Robot ro = null;
				try {
					ro = new Robot();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int y = 0; y < 4; y++) {
					for(int x = 0; x < 10; x++ ) {
						 Rectangle rect = new Rectangle(windowX + 579 + 36*x, windowY + 179 + 36*y, 35, 35);  
					     BufferedImage img = ro.createScreenCapture(rect);
					     File f = new File("myimagex"+x+"y"+y+".jpg");
					     try {
							ImageIO.write(img, "jpg", f);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}					
				}				
			}
		});
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(1500, 200);
//		frame.setSize(400, 800);
		while (true) {
			x.setText(MouseInfo.getPointerInfo().getLocation().getX() - windowX + "");
			y.setText(MouseInfo.getPointerInfo().getLocation().getY() - windowY + "");
		}
	}
	//580 180 
	//width 34 
	//height 34
	public static void main(String[] args) {
		initListAcc();
		initListPos();
		
		WindowUtils.getAllWindows(true).forEach(desktopWindow -> {
			System.out.println(desktopWindow.getFilePath());
			if (desktopWindow.getTitle().contains("Three")){
				Rectangle rec = desktopWindow.getLocAndSize();
				windowX = rec.x;
				windowY = rec.y;
			}
		});
		drawFrameBody();
	}
}
