public class TreeCoord {

	private final int row;
	private final int column;
	private final int value;

	public TreeCoord( final int row, final int column, final int value ) {
		this.row = row;
		this.column = column;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getValue() {
		return value;
	}
}
