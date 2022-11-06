public class ChristmasLight {

	private boolean switchedOn = false;
	private int x = 0;
	private int y = 0;

	public ChristmasLight( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}

	public void setSwitchedOn( final boolean switchedOn ) {
		this.switchedOn = switchedOn;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isSwitchedOn() {
		return switchedOn;
	}
}
