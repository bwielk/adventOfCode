import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RockPaperScissors {

	private final Map<Integer, Integer> scores = new HashMap<>();

	public Map<Integer, Integer> getScores() {
		return scores;
	}

	public void runGames( String fileWithRounds ) {
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings( RockPaperScissors.class,
				fileWithRounds );
		entries.forEach( round -> {
			List<RoundMove> moves = translateRoundsToMoves( round );
			List<RoundResult> roundResult = resolveRound( moves );
			calculatePoints( roundResult );
		} );
	}

	public Map<Integer, Integer> calculatePoints( List<RoundResult> results ) {
		for ( RoundResult result : results ) {
			if ( !scores.containsKey( result.getUserId() ) ) {
				scores.put( result.getUserId(), 0 );
			}
			int total = result.getResult().getPoint() + result.getResultsAchievedBy()
					.getPoint();
			scores.put( result.getUserId(), scores.get( result.getUserId() ) + total );
			}
		return scores;
	}

	public List<RoundMove> translateRoundsToMoves( String entry ) {
		List<RoundMove> moves = new ArrayList<>();
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
			char entryPlayer1 = Character.toUpperCase( cleansedEntry.get( 0 ).charAt( 0 ) );
			char entryPlayer2 = Character.toUpperCase( cleansedEntry.get( 1 ).charAt( 0 ) );
			if ( player1AllowedParams.contains( entryPlayer1 ) && player2AllowedParams.contains(
					entryPlayer2 ) ) {
				for ( RPSMoves move : RPSMoves.values() ) {
					if ( move.getSchema().get( 0 ).equals( entryPlayer1 ) ) {
						moves.add( new RoundMove(move, 0) );
					}
					if ( move.getSchema().get( 1 ).equals( entryPlayer2 ) ) {
						moves.add( new RoundMove( move, 1 ) );
					}
				}
			} else {
				throw new IllegalStateException(
						String.format( "The entry '%s' should only contain acceptable content",
								entry ) );
			}
		} else {
			throw new IllegalStateException( "The entry should only contain two parameters" );
		}
		return moves;
	}

	public List<RoundResult> resolveRound( List<RoundMove> roundMoves ) {
		List<RoundResult> results = new ArrayList<>();
		List<RPSMoves> moves = roundMoves.stream()
				.map( RoundMove::getRpsMoves )
				.collect( Collectors.toList());
		if ( moves.contains( RPSMoves.PAPER ) && moves.contains( RPSMoves.ROCK ) ) {
			int winningUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.PAPER )
					.findFirst().get().getUserId();
			int losingUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.ROCK )
					.findFirst().get().getUserId();
			results.add( new RoundResult( winningUserId, RPSResult.WIN,
					RPSMoves.PAPER ) );
			results.add( new RoundResult( losingUserId, RPSResult.LOST,
					RPSMoves.ROCK ) );
		} else if ( moves.contains( RPSMoves.ROCK ) && moves.contains( RPSMoves.SCISSORS ) ) {
			int winningUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.ROCK )
					.findFirst().get().getUserId();
			int losingUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.SCISSORS )
					.findFirst().get().getUserId();
			results.add( new RoundResult( winningUserId, RPSResult.WIN,
					RPSMoves.ROCK ) );
			results.add( new RoundResult(losingUserId, RPSResult.LOST,
					RPSMoves.SCISSORS ) );
		} else if ( moves.contains( RPSMoves.SCISSORS ) && moves.contains( RPSMoves.PAPER ) ) {
			int winningUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.SCISSORS )
					.findFirst().get().getUserId();
			int losingUserId = roundMoves.stream().filter( x -> x.getRpsMoves() == RPSMoves.PAPER )
					.findFirst().get().getUserId();
			results.add( new RoundResult( winningUserId, RPSResult.WIN,
					RPSMoves.SCISSORS ) );
			results.add( new RoundResult( losingUserId, RPSResult.LOST,
					RPSMoves.PAPER ) );
		} else if ( moves.get( 0 ) == moves.get( 1 ) ) {
			results.add( new RoundResult( roundMoves.get( 0 ).getUserId(), RPSResult.DRAW,
					moves.get( 0 ) ) );
			results.add( new RoundResult( roundMoves.get( 1 ).getUserId(), RPSResult.DRAW,
					moves.get( 0 ) ) );
		}
		return results;
	}
}
