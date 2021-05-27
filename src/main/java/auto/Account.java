package auto;

import com.sun.jna.platform.DesktopWindow;

public class Account {
	DesktopWindow window;
	String username;
	String password;
	String charName;
	int money;
	int level;
	String map;
	int windowX;
	int windowY;
	
	public int getWindowX() {
		return windowX;
	}
	public void setWindowX(int windowX) {
		this.windowX = windowX;
	}
	public int getWindowY() {
		return windowY;
	}
	public void setWindowY(int windowY) {
		this.windowY = windowY;
	}
	public DesktopWindow getWindow() {
		return window;
	}
	public void setWindow(DesktopWindow window) {
		this.window = window;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCharName() {
		return charName;
	}
	public void setCharName(String charName) {
		this.charName = charName;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public Account(String username, String password, String charName) {
		super();
		this.username = username;
		this.password = password;
		this.charName = charName;
	}
	
}
