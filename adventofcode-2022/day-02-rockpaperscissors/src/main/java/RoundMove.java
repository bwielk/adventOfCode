public class RoundMove {

	private RPSMoves rpsMoves;
	private int userId;

	public RoundMove( final RPSMoves rpsMoves, final int userId ) {
		this.rpsMoves = rpsMoves;
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public RPSMoves getRpsMoves() {
		return rpsMoves;
	}
}
