import java.util.List;

public enum RPSMoves {

	ROCK( List.of( 'A', 'X' ), 1 ),
	PAPER( List.of( 'B', 'Y' ), 2 ),
	SCISSORS( List.of( 'C', 'Z' ), 3 );

	private final List<Character> schema;
	private final Integer point;

	RPSMoves( List<Character> schema, Integer point ) {
		this.schema = schema;
		this.point = point;
	}

	public Integer getPoint() {
		return point;
	}

	public List<Character> getSchema() {
		return schema;
	}
}
