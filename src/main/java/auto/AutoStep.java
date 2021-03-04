package auto;

public class AutoStep {
	String key;
	int x;
	int y;
	int waitTime;
	int delay;
	int delayTimes;
	
	
	public int getDelayTimes() {
		return delayTimes;
	}
	public void setDelayTimes(int delayTimes) {
		this.delayTimes = delayTimes;
	}
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
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public AutoStep(String key, int x, int y, int waitTime, int delay) {
		super();
		this.key = key;
		this.x = x;
		this.y = y;
		this.waitTime = waitTime;
		this.delay = delay;
	}	
}
