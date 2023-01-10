public class Relation {

	private final String from;
	private final String to;
	private final int distance;

	public Relation( final String from, final String to, final int distance ) {
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
