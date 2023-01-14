import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RockPaperScissorsTest {

	private RockPaperScissors rockPaperScissors;

	@BeforeEach
	public void before(){
		rockPaperScissors = new RockPaperScissors();
	}

	private static Stream<Arguments> paperAndRock() {
		return Stream.of(
				Arguments.of(RPSMoves.ROCK, RPSMoves.PAPER, RPSResult.LOST, RPSResult.WIN),
				Arguments.of(RPSMoves.PAPER, RPSMoves.ROCK, RPSResult.WIN, RPSResult.LOST)
		);
	}

	private static Stream<Arguments> rockAndScissors() {
		return Stream.of(
				Arguments.of(RPSMoves.SCISSORS, RPSMoves.ROCK, RPSResult.LOST, RPSResult.WIN),
				Arguments.of(RPSMoves.ROCK, RPSMoves.SCISSORS, RPSResult.WIN, RPSResult.LOST)
		);
	}

	private static Stream<Arguments> scissorsAndPaper() {
		return Stream.of(
				Arguments.of(RPSMoves.PAPER, RPSMoves.SCISSORS, RPSResult.LOST, RPSResult.WIN),
				Arguments.of(RPSMoves.SCISSORS, RPSMoves.PAPER, RPSResult.WIN, RPSResult.LOST)
		);
	}

	private static Stream<Arguments> correctlyFormattedEntries() {
		return Stream.of(
				Arguments.of("C Z", RPSMoves.SCISSORS, RPSMoves.SCISSORS),
				Arguments.of("C Y", RPSMoves.SCISSORS, RPSMoves.PAPER),
				Arguments.of("c y", RPSMoves.SCISSORS, RPSMoves.PAPER),
				Arguments.of("   A Z   ", RPSMoves.ROCK, RPSMoves.SCISSORS),
//				Arguments.of("", Collections.emptyList() ),
//				Arguments.of("   ", Collections.emptyList()),
				Arguments.of("AZ", RPSMoves.ROCK, RPSMoves.SCISSORS)
		);
	}

	private static Stream<Arguments> incorrectlyFormattedEntries() {
		return Stream.of(
				Arguments.of("Z C"),
				Arguments.of("Z C C"),
				Arguments.of("Z,C"),
				Arguments.of("1 C"),
				Arguments.of("chess zest"),
				Arguments.of("hello")
				);
	}



	@ParameterizedTest
	@MethodSource("paperAndRock")
	public void paperWinsOverRock(RPSMoves move1, RPSMoves move2, RPSResult result1, RPSResult result2){
		List<RPSResult> resultList = rockPaperScissors.resolveRound(move1, move2);
		assertThat(resultList).hasSize( 2 );
		assertThat(resultList.get( 0 )).isEqualTo( result1 );
		assertThat(resultList.get( 1 )).isEqualTo( result2 );
	}

	@ParameterizedTest
	@MethodSource("rockAndScissors")
	public void rockWinsOverScissors(RPSMoves move1, RPSMoves move2, RPSResult result1, RPSResult result2){
		List<RPSResult> resultList = rockPaperScissors.resolveRound(move1, move2);
		assertThat(resultList).hasSize( 2 );
		assertThat(resultList.get( 0 )).isEqualTo( result1 );
		assertThat(resultList.get( 1 )).isEqualTo( result2 );
	}

	@ParameterizedTest
	@MethodSource("scissorsAndPaper")
	public void scissorsWinsOverPaper(RPSMoves move1, RPSMoves move2, RPSResult result1, RPSResult result2){
		List<RPSResult> resultList = rockPaperScissors.resolveRound(move1, move2);
		assertThat(resultList).hasSize( 2 );
		assertThat(resultList.get( 0 )).isEqualTo( result1 );
		assertThat(resultList.get( 1 )).isEqualTo( result2 );
	}

	@Test
	public void drawsAreDetected(){
		List<RPSResult> resultList = rockPaperScissors.resolveRound( RPSMoves.SCISSORS, RPSMoves.SCISSORS );
		assertThat(resultList).hasSize( 2 );
		assertThat( resultList.get( 0 )).isEqualTo( RPSResult.DRAW );
		assertThat( resultList.get( 1 )).isEqualTo( RPSResult.DRAW );
	}

	@Test
	public void eitherOfArgumentsCannotBeNull(){
		assertThrows(NullPointerException.class,
				() -> rockPaperScissors.resolveRound( null, RPSMoves.SCISSORS ));
	}


	@Test
	public void eachRoundGetsCleared(){
		rockPaperScissors.resolveRound( RPSMoves.SCISSORS, RPSMoves.PAPER );
		assertThat( rockPaperScissors.getGameRound()).isEmpty();
	}

	@ParameterizedTest
	@MethodSource("correctlyFormattedEntries")
	public void entriesCanBeTranslatedToGameMoves(String entry, RPSMoves move1, RPSMoves move2){
		List<RPSMoves> moves = rockPaperScissors.translateEntries( entry );
		assertThat( moves ).hasSize( 2 );
		assertThat( moves ).containsExactlyInAnyOrder( move1, move2 );
	}
}