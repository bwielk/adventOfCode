import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RockPaperScissors {

	private final Map<Integer, Integer> scores = new HashMap<>();

	public Map<Integer, Integer> getScores() {
		return scores;
	}

	public void runGames( String fileWithRounds, boolean opponentMoveAndResultProvided ) {
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings( RockPaperScissors.class,
				fileWithRounds );
		entries.forEach( round -> {
			List<RoundMove> moves = translateRoundsToMoves( round, opponentMoveAndResultProvided );
			List<RoundResult> roundResult = resolveRound( moves );
			calculatePoints( roundResult );
		} );
	}

	public Map<Integer, Integer> calculatePoints( List<RoundResult> results ) {
		for ( RoundResult result : results ) {
			if ( !scores.containsKey( result.getUserId() ) ) {
				scores.put( result.getUserId(), 0 );
			}
			int total = result.getResult().getPoint() + result.getResultsAchievedBy().getPoint();
			scores.put( result.getUserId(), scores.get( result.getUserId() ) + total );
		}
		return scores;
	}

	public List<RoundMove> translateRoundsToMoves( String entry,
			boolean opponentMoveAndResultProvided ) {
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
			char firstInput = Character.toUpperCase( cleansedEntry.get( 0 ).charAt( 0 ) );
			char secondInput = Character.toUpperCase( cleansedEntry.get( 1 ).charAt( 0 ) );
			if ( player1AllowedParams.contains( firstInput ) && player2AllowedParams.contains(
					secondInput ) ) {
				if ( opponentMoveAndResultProvided ) {
					for ( RPSResult result : RPSResult.values() ) {
						if ( result.getResultCode().equals( secondInput ) ) {
							moves.add( new RoundMove( result, 0 ) );
						}
					}
					for ( RPSMoves move : RPSMoves.values() ) {
						if ( move.getSchema().get( 0 ).equals( firstInput ) ) {
							moves.add( new RoundMove( move, 1 ) );
						}
					}
				} else {
					for ( RPSMoves move : RPSMoves.values() ) {
						if ( move.getSchema().get( 0 ).equals( firstInput ) ) {
							moves.add( new RoundMove( move, 0 ) );
						}
						if ( move.getSchema().get( 1 ).equals( secondInput ) ) {
							moves.add( new RoundMove( move, 1 ) );
						}
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
				.collect( Collectors.toList() );
		if ( moves.contains( RPSMoves.PAPER ) && moves.contains( RPSMoves.ROCK ) ) {
			results = defineResult( roundMoves, RPSMoves.PAPER, RPSMoves.ROCK );
		} else if ( moves.contains( RPSMoves.ROCK ) && moves.contains( RPSMoves.SCISSORS ) ) {
			results = defineResult( roundMoves, RPSMoves.ROCK, RPSMoves.SCISSORS );
		} else if ( moves.contains( RPSMoves.SCISSORS ) && moves.contains( RPSMoves.PAPER ) ) {
			results = defineResult( roundMoves, RPSMoves.SCISSORS, RPSMoves.PAPER );
		} else if ( moves.get( 0 ) == moves.get( 1 ) ) {
			results.add( new RoundResult( roundMoves.get( 0 ).getUserId(), RPSResult.DRAW,
					moves.get( 0 ) ) );
			results.add( new RoundResult( roundMoves.get( 1 ).getUserId(), RPSResult.DRAW,
					moves.get( 0 ) ) );
		}
		return results;
	}

	public List<RoundResult> resolveRoundBasedOnProvidedResult( List<RoundMove> roundMoves ) {
		List<RoundResult> results = new ArrayList<>();
		RPSMoves move = roundMoves.stream()
				.map( RoundMove::getRpsMoves )
				.filter( Objects::nonNull ).findFirst().get();
		RPSResult result = roundMoves.stream()
				.map( RoundMove::getProvidedResult )
				.filter( Objects::nonNull ).findFirst().get();
		int winningUserId = 0;
		int losingUserId = 0;
		RPSMoves winningMove = null;
		RPSMoves losingMove = null;
		if(result != RPSResult.DRAW){
			switch(move) { // move of an opponent; we're calculating what we should do in order
				// to accomplish a result of our interest
			case SCISSORS:
				if ( result == RPSResult.WIN ) {
					winningMove = RPSMoves.ROCK;
					losingMove = RPSMoves.SCISSORS;
					winningUserId = 0;
					losingUserId = 1;
				} else if ( result == RPSResult.LOST ) {
					winningMove = RPSMoves.SCISSORS;
					losingMove = RPSMoves.PAPER;
					winningUserId = 1;
					losingUserId = 0;
				}
				break;
			case ROCK:
				if ( result == RPSResult.WIN ) {
					winningMove = RPSMoves.PAPER;
					losingMove = RPSMoves.ROCK;
					winningUserId = 0;
					losingUserId = 1;
				} else if ( result == RPSResult.LOST ) {
					winningMove = RPSMoves.ROCK;
					losingMove = RPSMoves.SCISSORS;
					winningUserId = 1;
					losingUserId = 0;
				}
				break;
			case PAPER:
				if ( result == RPSResult.WIN ) {
					winningMove = RPSMoves.SCISSORS;
					losingMove = RPSMoves.PAPER;
					winningUserId = 0;
					losingUserId = 1;
				} else if ( result == RPSResult.LOST ) {
					winningMove = RPSMoves.PAPER;
					losingMove = RPSMoves.ROCK;
					winningUserId = 1;
					losingUserId = 0;
					break;
				}
			default:
				break;
			}
			results.add( new RoundResult( winningUserId, RPSResult.WIN, winningMove ) );
			results.add( new RoundResult( losingUserId, RPSResult.LOST, losingMove ) );
		}else{
			results.add( new RoundResult( roundMoves.get( 0 ).getUserId(), RPSResult.DRAW,
					move ));
			results.add( new RoundResult( roundMoves.get( 1 ).getUserId(), RPSResult.DRAW,
					move ));
		}
		return results;
	}

	private List<RoundResult> defineResult( List<RoundMove> roundMoves, RPSMoves winningMove,
			RPSMoves losingMove ) {
		List<RoundResult> results = new ArrayList<>();
		int winningUserId = roundMoves.stream()
				.filter( x -> x.getRpsMoves() == winningMove )
				.findFirst()
				.get()
				.getUserId();
		int losingUserId = roundMoves.stream()
				.filter( x -> x.getRpsMoves() == losingMove )
				.findFirst()
				.get()
				.getUserId();
		results.add( new RoundResult( winningUserId, RPSResult.WIN, winningMove ) );
		results.add( new RoundResult( losingUserId, RPSResult.LOST, losingMove ) );
		return results;
	}

}
