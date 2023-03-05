public class RoundResult {

	private RPSResult result;
	private RPSMoves resultsAchievedBy;
	private int userId;

	public RoundResult( final int userId, final RPSResult result,
			final RPSMoves resultsAchievedBy ) {
		this.userId = userId;
		this.result = result;
		this.resultsAchievedBy = resultsAchievedBy;
	}

	public RPSResult getResult() {
		return result;
	}

	public RPSMoves getResultsAchievedBy() {
		return resultsAchievedBy;
	}

	public int getUserId() {
		return userId;
	}
}
