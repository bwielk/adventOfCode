import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

public class RockPaperScissors {

	private List<RPSMoves> gameRound = new ArrayList<>();

	public List<RPSMoves> getGameRound() {
		return gameRound;
	}

	public List<RPSMoves> translateEntries( String entry ) {
		List<RPSMoves> moves = new ArrayList<>();
		List<String> cleansedEntry = Arrays.asList(
				entry.strip().replaceAll( " ", "" ).split( "" ) );
		if ( cleansedEntry.size() == 2 && cleansedEntry.get( 0 ).length() == 1 && cleansedEntry.get(
				1 ).length() == 1 ) {
			List<Character> player1AllowedParams = Arrays.stream( RPSMoves.values() )
					.map( x -> Character.toUpperCase( x.getSchema().get( 0 ) ) )
					.collect( Collectors.toList() );
			List<Character> player2AllowedParams = Arrays.stream( RPSMoves.values() )
					.map( x -> Character.toUpperCase( x.getSchema().get( 1 ) ) )
					.collect( Collectors.toList() );
			char entryPlayer1 = Character.toUpperCase(cleansedEntry.get( 0 ).charAt( 0 ) );
			char entryPlayer2 = Character.toUpperCase(cleansedEntry.get( 1 ).charAt( 0 ) );
			if ( player1AllowedParams.contains( entryPlayer1 ) &&
					player2AllowedParams.contains( entryPlayer2 )){
				for(RPSMoves move : RPSMoves.values()){
					if(move.getSchema().get( 0 ).equals( entryPlayer1 )){
						moves.add( move );
					}
					if(move.getSchema().get( 1 ).equals( entryPlayer2 )){
						moves.add( move);
					}
				}
			}else{
				throw new IllegalStateException( String.format("The entry '%s' should only contain acceptable content", entry ));
			}
		} else {
			throw new IllegalStateException( "The entry should only contain two parameters" );
		} return moves;
	}

	public List<RPSResult> resolveRound( @NonNull final RPSMoves playerOneMove,
			@NonNull final RPSMoves playerTwoMove ) {
		List<RPSResult> results = new ArrayList<>();
		gameRound.add( playerOneMove );
		gameRound.add( playerTwoMove );
		if ( gameRound.contains( RPSMoves.PAPER ) && gameRound.contains( RPSMoves.ROCK ) ) {
			if ( gameRound.get( 0 ) == RPSMoves.PAPER ) {
				results.add( RPSResult.WIN );
				results.add( RPSResult.LOST );
			} else {
				results.add( RPSResult.LOST );
				results.add( RPSResult.WIN );
			}
		} else if ( gameRound.contains( RPSMoves.ROCK ) && gameRound.contains(
				RPSMoves.SCISSORS ) ) {
			if ( gameRound.get( 0 ) == RPSMoves.ROCK ) {
				results.add( RPSResult.WIN );
				results.add( RPSResult.LOST );
			} else {
				results.add( RPSResult.LOST );
				results.add( RPSResult.WIN );
			}
		} else if ( gameRound.contains( RPSMoves.SCISSORS ) && gameRound.contains(
				RPSMoves.PAPER ) ) {
			if ( gameRound.get( 0 ) == RPSMoves.SCISSORS ) {
				results.add( RPSResult.WIN );
				results.add( RPSResult.LOST );
			} else {
				results.add( RPSResult.LOST );
				results.add( RPSResult.WIN );
			}
		} else if ( gameRound.get( 0 ) == gameRound.get( 1 ) ) {
			results.add( RPSResult.DRAW );
			results.add( RPSResult.DRAW );
		}
		gameRound.clear();
		return results;
	}
}
