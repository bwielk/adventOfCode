public class RoundMove {

	private RPSMoves rpsMoves;
	private RPSResult providedResult;
	private int userId;

	public RoundMove( final RPSMoves rpsMoves, final int userId ) {
		this.rpsMoves = rpsMoves;
		this.userId = userId;
		this.providedResult = null;
	}

	public RoundMove( final RPSResult providedResult, final int userId ) {
		this.rpsMoves = null;
		this.userId = userId;
		this.providedResult = providedResult;
	}

	public int getUserId() {
		return userId;
	}

	public RPSMoves getRpsMoves() {
		return rpsMoves;
	}

	public RPSResult getProvidedResult() {
		return providedResult;
	}
}
