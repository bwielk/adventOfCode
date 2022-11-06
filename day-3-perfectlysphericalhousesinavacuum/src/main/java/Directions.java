public enum Directions {

	UP( 1, 0 ),
	RIGHT( 0, 1 ),
	DOWN( -1, 0 ),
	LEFT( 0, -1 ),
	NOTHING( 0, 0 );

	int x;
	int y;

	Directions( int x, int y ) {
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
