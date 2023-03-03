import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RockPaperScissorsTest {

	private RockPaperScissors rockPaperScissors;

	@BeforeEach
	public void before() {
		rockPaperScissors = new RockPaperScissors();
	}

	private static Stream<Arguments> paperAndRock() {
		return Stream.of(
				Arguments.of( new RoundMove( RPSMoves.ROCK, 0 ), new RoundMove( RPSMoves.PAPER, 1 ),
						new RoundResult( 1, RPSResult.WIN, RPSMoves.PAPER ),
						new RoundResult( 0, RPSResult.LOST, RPSMoves.ROCK ) ),
				Arguments.of( new RoundMove( RPSMoves.PAPER, 0 ), new RoundMove( RPSMoves.ROCK, 1 ),
						new RoundResult( 0, RPSResult.WIN, RPSMoves.PAPER ),
						new RoundResult( 1, RPSResult.LOST, RPSMoves.ROCK ) ) );
	}

	private static Stream<Arguments> rockAndScissors() {
		return Stream.of( Arguments.of( new RoundMove( RPSMoves.SCISSORS, 0 ),
				new RoundMove( RPSMoves.ROCK, 1 ),
				new RoundResult( 1, RPSResult.WIN, RPSMoves.ROCK ),
				new RoundResult( 0, RPSResult.LOST, RPSMoves.SCISSORS ) ),
				Arguments.of( new RoundMove( RPSMoves.ROCK, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ),
						new RoundResult( 0, RPSResult.WIN, RPSMoves.ROCK ),
						new RoundResult( 1, RPSResult.LOST, RPSMoves.SCISSORS ) ) );
	}

	private static Stream<Arguments> scissorsAndPaper() {
		return Stream.of( Arguments.of( new RoundMove( RPSMoves.PAPER, 0 ),
				new RoundMove( RPSMoves.SCISSORS, 1 ),
				new RoundResult( 1, RPSResult.WIN, RPSMoves.SCISSORS ),
				new RoundResult( 0, RPSResult.LOST, RPSMoves.PAPER ) ),
				Arguments.of( new RoundMove( RPSMoves.SCISSORS, 0 ),
						new RoundMove( RPSMoves.PAPER, 1 ),
						new RoundResult( 0, RPSResult.WIN, RPSMoves.SCISSORS ),
						new RoundResult( 1, RPSResult.LOST, RPSMoves.PAPER ) ) );
	}

	private static Stream<Arguments> scissorsAndGameResult() {
		return Stream.of( Arguments.of( new RoundMove( RPSResult.WIN, 0 ),
				new RoundMove( RPSMoves.SCISSORS, 1 ),
				new RoundResult( 1, RPSResult.LOST, RPSMoves.SCISSORS ),
				new RoundResult( 0, RPSResult.WIN, RPSMoves.ROCK ) ),
				Arguments.of( new RoundMove( RPSResult.DRAW, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ),
						new RoundResult( 0, RPSResult.DRAW, RPSMoves.SCISSORS ),
						new RoundResult( 1, RPSResult.DRAW, RPSMoves.SCISSORS )),
				Arguments.of( new RoundMove( RPSResult.LOST, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ),
				new RoundResult( 0, RPSResult.LOST, RPSMoves.PAPER ),
				new RoundResult( 1, RPSResult.WIN, RPSMoves.SCISSORS )));
	}

	private static Stream<Arguments> correctlyFormattedEntries() {
		return Stream.of( Arguments.of( "C Z", new RoundMove( RPSMoves.SCISSORS, 0 ),
				new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "C Y", new RoundMove( RPSMoves.PAPER, 1 ),
						new RoundMove( RPSMoves.SCISSORS, 0 ) ),
				Arguments.of( "c y", new RoundMove( RPSMoves.PAPER, 1 ),
						new RoundMove( RPSMoves.SCISSORS, 0 ) ),
				Arguments.of( "   A Z   ", new RoundMove( RPSMoves.ROCK, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "AZ", new RoundMove( RPSMoves.ROCK, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ) ) );
	}

	private static Stream<Arguments> correctlyFormattedEntriesAlternativeCalculation() {
		return Stream.of( Arguments.of( "A Y", new RoundMove( RPSMoves.SCISSORS, 0 ),
				new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "B X", new RoundMove( RPSMoves.PAPER, 0 ),
						new RoundMove( RPSMoves.ROCK, 1 ) ),
				Arguments.of( "C Z", new RoundMove( RPSMoves.SCISSORS, 0 ),
						new RoundMove( RPSMoves.ROCK, 1 ) ) );
	}

	private static Stream<Arguments> correctlyFormattedEntriesWithProvidedResultAndOpponentMove() {
		return Stream.of( Arguments.of( "C Z", new RoundMove( RPSResult.WIN, 0 ),
				new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "C Y", new RoundMove( RPSResult.LOST, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "c y", new RoundMove( RPSResult.LOST, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ) ),
				Arguments.of( "   A Z   ", new RoundMove( RPSResult.WIN, 0 ),
						new RoundMove( RPSMoves.ROCK, 1 ) ),
				Arguments.of( "AZ", new RoundMove( RPSResult.WIN, 0 ),
						new RoundMove( RPSMoves.ROCK, 1 ) ) );
	}

	private static Stream<Arguments> calculatePoints() {
		return Stream.of(
				Arguments.of( List.of( new RoundResult( 0, RPSResult.LOST, RPSMoves.SCISSORS ),//0+3
						new RoundResult( 1, RPSResult.WIN, RPSMoves.ROCK ) ),//6+1
						3, 7 ),
				Arguments.of( List.of( new RoundResult( 0, RPSResult.LOST, RPSMoves.PAPER ),//0+2
						new RoundResult( 1, RPSResult.WIN, RPSMoves.SCISSORS ) ),//6+3
						2, 9 ),
				Arguments.of( List.of( new RoundResult( 0, RPSResult.DRAW, RPSMoves.PAPER ),//3+2
						new RoundResult( 1, RPSResult.DRAW, RPSMoves.PAPER ) ),//3+2
						5, 5 ) );
	}

	//TODO handle skipping invalid entries
	private static Stream<Arguments> incorrectlyFormattedEntries() {
		return Stream.of( Arguments.of( "Z C" ), Arguments.of( "Z C C" ), Arguments.of( "Z,C" ),
				Arguments.of( "1 C" ), Arguments.of( "chess zest" ), Arguments.of( "" ),
				Arguments.of( "hello" ), Arguments.of( " " ) );
	}

	@ParameterizedTest
	@MethodSource("paperAndRock")
	public void paperWinsOverRock( RoundMove move1, RoundMove move2,
			RoundResult expectedRoundResult1, RoundResult expectedRoundResult2 ) {
		List<RoundResult> resultList = rockPaperScissors.resolveRound( List.of( move1, move2 ) );
		assertThat( resultList ).hasSize( 2 );
		RoundResult roundResult1 = resultList.get( 0 );
		RoundResult roundResult2 = resultList.get( 1 );

		assertThat( roundResult1.getResult() ).isEqualTo( expectedRoundResult1.getResult() );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult1.getResultsAchievedBy() );
		assertThat( roundResult1.getUserId() ).isEqualTo( expectedRoundResult1.getUserId() );

		assertThat( roundResult2.getResult() ).isEqualTo( expectedRoundResult2.getResult() );
		assertThat( roundResult2.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult2.getResultsAchievedBy() );
		assertThat( roundResult2.getUserId() ).isEqualTo( expectedRoundResult2.getUserId() );
	}

	@ParameterizedTest
	@MethodSource("rockAndScissors")
	public void rockWinsOverScissors( RoundMove move1, RoundMove move2,
			RoundResult expectedRoundResult1, RoundResult expectedRoundResult2 ) {
		List<RoundResult> resultList = rockPaperScissors.resolveRound( List.of( move1, move2 ) );
		assertThat( resultList ).hasSize( 2 );
		RoundResult roundResult1 = resultList.get( 0 );
		RoundResult roundResult2 = resultList.get( 1 );

		assertThat( roundResult1.getResult() ).isEqualTo( expectedRoundResult1.getResult() );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult1.getResultsAchievedBy() );
		assertThat( roundResult1.getUserId() ).isEqualTo( expectedRoundResult1.getUserId() );

		assertThat( roundResult2.getResult() ).isEqualTo( expectedRoundResult2.getResult() );
		assertThat( roundResult2.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult2.getResultsAchievedBy() );
		assertThat( roundResult2.getUserId() ).isEqualTo( expectedRoundResult2.getUserId() );
	}

	@ParameterizedTest
	@MethodSource("scissorsAndPaper")
	public void scissorsWinsOverPaper( RoundMove move1, RoundMove move2,
			RoundResult expectedRoundResult1, RoundResult expectedRoundResult2 ) {
		List<RoundResult> resultList = rockPaperScissors.resolveRound( List.of( move1, move2 ) );
		assertThat( resultList ).hasSize( 2 );
		RoundResult roundResult1 = resultList.get( 0 );
		RoundResult roundResult2 = resultList.get( 1 );

		assertThat( roundResult1.getResult() ).isEqualTo( expectedRoundResult1.getResult() );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult1.getResultsAchievedBy() );
		assertThat( roundResult1.getUserId() ).isEqualTo( expectedRoundResult1.getUserId() );

		assertThat( roundResult2.getResult() ).isEqualTo( expectedRoundResult2.getResult() );
		assertThat( roundResult2.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult2.getResultsAchievedBy() );
		assertThat( roundResult2.getUserId() ).isEqualTo( expectedRoundResult2.getUserId() );
	}

	@ParameterizedTest
	@MethodSource("scissorsAndGameResult")
	public void scissorsAreCorrectlyChallengedWithMovesBasedOnGameResult(RoundMove move1, RoundMove move2,
			RoundResult expectedRoundResult1, RoundResult expectedRoundResult2){
		List<RoundResult> resultList = rockPaperScissors.resolveRoundBasedOnProvidedResult( List.of( move1, move2 ) );
		assertThat( resultList ).hasSize( 2 );
		RoundResult roundResult1 = resultList.get( 0 );
		RoundResult roundResult2 = resultList.get( 1 );

		assertThat( roundResult1.getResult() ).isEqualTo( expectedRoundResult1.getResult() );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult1.getResultsAchievedBy() );
		assertThat( roundResult1.getUserId() ).isEqualTo( expectedRoundResult1.getUserId() );

		assertThat( roundResult2.getResult() ).isEqualTo( expectedRoundResult2.getResult() );
		assertThat( roundResult2.getResultsAchievedBy() ).isEqualTo(
				expectedRoundResult2.getResultsAchievedBy() );
		assertThat( roundResult2.getUserId() ).isEqualTo( expectedRoundResult2.getUserId() );
	}

	@Test
	public void drawsAreDetected() {
		List<RoundResult> resultList = rockPaperScissors.resolveRound(
				List.of( new RoundMove( RPSMoves.SCISSORS, 0 ),
						new RoundMove( RPSMoves.SCISSORS, 1 ) ) );
		assertThat( resultList ).hasSize( 2 );
		RoundResult roundResult1 = resultList.get( 0 );
		RoundResult roundResult2 = resultList.get( 1 );

		assertThat( roundResult1.getResult() ).isEqualTo( RPSResult.DRAW );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo( RPSMoves.SCISSORS );
		assertThat( roundResult1.getResultsAchievedBy() ).isEqualTo( RPSMoves.SCISSORS );

		assertThat( roundResult2.getResult() ).isEqualTo( RPSResult.DRAW );
		assertThat( roundResult2.getResultsAchievedBy() ).isEqualTo( RPSMoves.SCISSORS );
	}

	@ParameterizedTest
	@MethodSource("correctlyFormattedEntries")
	public void entriesCanBeTranslatedToGameMoves( String entry, RoundMove move1,
			RoundMove move2 ) {
		List<RoundMove> moves = rockPaperScissors.translateRoundsToMoves( entry, false );
		assertThat( moves ).hasSize( 2 );
		assertThat( moves.get( 0 ).getRpsMoves() ).isEqualTo( move1.getRpsMoves() );
		assertThat( moves.get( 0 ).getUserId() ).isEqualTo( move1.getUserId() );
		assertThat( moves.get( 1 ).getRpsMoves() ).isEqualTo( move2.getRpsMoves() );
		assertThat( moves.get( 1 ).getUserId() ).isEqualTo( move2.getUserId() );
	}

	@ParameterizedTest
	@MethodSource("correctlyFormattedEntriesWithProvidedResultAndOpponentMove")
	public void entriesCanBeTranslatedToGameMovesAlternativeResult( String entry, RoundMove move1,
			RoundMove move2 ) {
		List<RoundMove> moves = rockPaperScissors.translateRoundsToMoves( entry, true );
		assertThat( moves ).hasSize( 2 );
		assertThat( moves.get( 0 ).getProvidedResult() ).isEqualTo( move1.getProvidedResult() );
		assertThat( moves.get( 0 ).getUserId() ).isEqualTo( move1.getUserId() );
		assertThat( moves.get( 1 ).getRpsMoves() ).isEqualTo( move2.getRpsMoves() );
		assertThat( moves.get( 1 ).getUserId() ).isEqualTo( move2.getUserId() );
	}

	//TODO expand the tests to more specific exceptions
	@ParameterizedTest
	@MethodSource("incorrectlyFormattedEntries")
	public void entriesThatCannotBeTranslatedToGameMoves( String entry ) {
		assertThrows( Exception.class,
				() -> rockPaperScissors.translateRoundsToMoves( entry, false ) );
	}

	@ParameterizedTest
	@MethodSource("calculatePoints")
	public void calculatesPointsFromResults( List<RoundResult> results, int result1Total,
			int result2Total ) {
		Map<Integer, Integer> totals = rockPaperScissors.calculatePoints( results );
		assertThat( totals.get( results.get( 0 ).getUserId() ) ).isEqualTo( result1Total );
		assertThat( totals.get( results.get( 1 ).getUserId() ) ).isEqualTo( result2Total );
	}

	//	@Test
	//	public void definesPlayerMoveBasedOnOpponentMoveAndResult(){
	//		Map<Integer, Integer> totals = rockPaperScissors.calculatePoints( results );
	//		assertThat( totals.get( results.get( 0 ).getUserId() ) ).isEqualTo( result1Total );
	//		assertThat( totals.get( results.get( 1 ).getUserId() ) ).isEqualTo( result2Total );
	//	}

	@Test
	public void definesPlayersMoveBasedOnOutcomeAndOpponentMove() {
		rockPaperScissors.runGames( "correctlyformattedinput.txt", false );
		Map<Integer, Integer> scores = rockPaperScissors.getScores();
		assertThat( scores.get( 0 ) ).isEqualTo( 30 );
		assertThat( scores.get( 1 ) ).isEqualTo( 12 );
	}

	@Test
	public void parsingOfFileGeneratesScores() {
		rockPaperScissors.runGames( "correctlyformattedinput.txt", false );
		Map<Integer, Integer> scores = rockPaperScissors.getScores();
		assertThat( scores.get( 0 ) ).isEqualTo( 30 );
		assertThat( scores.get( 1 ) ).isEqualTo( 12 );
	}

	@Test
	public void parsingOfFileGeneratesScoresTheActualInput() {
		rockPaperScissors.runGames( "input.txt", false );
		Map<Integer, Integer> scores = rockPaperScissors.getScores();
		assertThat( scores.get( 0 ) ).isEqualTo( 16114 );
		assertThat( scores.get( 1 ) ).isEqualTo( 9241 );
	}
}
