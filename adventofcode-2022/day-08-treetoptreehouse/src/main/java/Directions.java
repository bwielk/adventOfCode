public enum Directions {

	EAST(0, 1),
	SOUTH(1, 0),
	WEST(0, -1),
	NORTH(-1, 0);

	int x;
	int y;

	Directions(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
}
