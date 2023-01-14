import java.util.ArrayList;
import java.util.List;

public class RockPaperScissors {

	private List<RPSMoves> gameRound = new ArrayList<>();

	public List<RPSMoves> getGameRound() {
		return gameRound;
	}

	public List<RPSResult> resolveRound( final RPSMoves playerOneMove,
			final RPSMoves playerTwoMove ) {
		List<RPSResult> results = new ArrayList<>();
		gameRound.add( playerOneMove );
		gameRound.add( playerTwoMove );
		if ( gameRound.contains( RPSMoves.PAPER ) && gameRound.contains( RPSMoves.ROCK ) ) {
			if(gameRound.get( 0 ) == RPSMoves.PAPER){
				results.add(RPSResult.WIN );
				results.add(RPSResult.LOST );
			}else{
				results.add(RPSResult.LOST );
				results.add(RPSResult.WIN );
			}
		} else if ( gameRound.contains( RPSMoves.ROCK ) && gameRound.contains(
				RPSMoves.SCISSORS ) ) {
			if(gameRound.get( 0 ) == RPSMoves.ROCK){
				results.add(RPSResult.WIN );
				results.add(RPSResult.LOST );
			}else{
				results.add(RPSResult.LOST );
				results.add(RPSResult.WIN );
			}
		} else if ( gameRound.contains( RPSMoves.SCISSORS ) && gameRound.contains(
				RPSMoves.PAPER ) ) {
			if(gameRound.get( 0 ) == RPSMoves.SCISSORS){
				results.add(RPSResult.WIN );
				results.add(RPSResult.LOST );
			}else{
				results.add(RPSResult.LOST );
				results.add(RPSResult.WIN );
			}
		} else if ( gameRound.get( 0 ) == gameRound.get( 1 ) ) {
			results.add(RPSResult.DRAW );
			results.add(RPSResult.DRAW );
		}
		gameRound.clear();
		return results;
	}
}
