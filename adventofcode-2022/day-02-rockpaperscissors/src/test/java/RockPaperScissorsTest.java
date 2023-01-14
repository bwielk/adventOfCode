import static org.assertj.core.api.Assertions.assertThat;

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
	public void eachRoundGetsCleared(){
		rockPaperScissors.resolveRound( RPSMoves.SCISSORS, RPSMoves.PAPER );
		assertThat( rockPaperScissors.getGameRound()).isEmpty();
	}
}