public class Octopus {

	private int value;
	private final int x;
	private final int y;
	private boolean toBeFlashed;
	private boolean isFlashed;

	public Octopus( final int value, final int x, final int y ) {
		this.value = value;
		this.x = x;
		this.y = y;
		this.isFlashed = false;
		this.toBeFlashed = false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValue() {
		return value;
	}

	public void setValue( final int value ) {
		this.value = value;
		if(this.value == 0){
			this.toBeFlashed = true;
		}
	}

	public void setFlashed( final boolean flashed ) {
		isFlashed = flashed;
	}

	public void setToBeFlashed( final boolean toBeFlashed ) {
		this.toBeFlashed = toBeFlashed;
	}

	public boolean isFlashed() {
		return isFlashed;
	}

	public boolean isToBeFlashed() {
		return toBeFlashed;
	}

	@Override
	public String toString() {
		return "" + value + "";
//		return "val: " + value + " isToBeF: " + isToBeFlashed() + " isFl: " + isFlashed();
	}
}
