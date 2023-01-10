public class ChristmasLight {

	private int brightness = 0;
	private boolean isSwitchedOn = false;

	public void switchOn() {
		if ( !isSwitchedOn ) {
			isSwitchedOn = true;
		}
	}

	public void switchOff() {
		if ( isSwitchedOn ) {
			isSwitchedOn = false;
		}
	}

	public void setBrightness( final int brightness ) {
		this.brightness = brightness;
	}

	public int getBrightness() {
		return brightness;
	}

	public boolean isSwitchedOn() {
		return isSwitchedOn;
	}
}
