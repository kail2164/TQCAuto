package auto;

public class AutoStep {
	String key;
	int x;
	int y;	
	
	

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public AutoStep(String key, int x, int y) {
		super();
		this.key = key;
		this.x = x;
		this.y = y;		
	}	
}
