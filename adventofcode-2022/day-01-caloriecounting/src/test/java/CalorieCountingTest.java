import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalorieCountingTest {

	private CalorieCounting calorieCounting;

	@BeforeEach
	public void before(){
		calorieCounting = new CalorieCounting();
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakes(){
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake("testInput.txt");
		assertThat(sums.size()).isEqualTo( 4 );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 15, 15 );
	}

	@Test
	public void canFindHighestIntakeBasedOnRealInput(){
		int result = calorieCounting.runFindHighestCalorieIntake( "input.txt" );
		assertThat( result ).isEqualTo( 67633 );
	}

	@Test
	public void canFindSumOfThreeHighestIntakesBasedOnRealInput(){
		int result = calorieCounting.runFindHighestCalorieIntakeForTopThreeElves( "input.txt" );
		assertThat( result ).isEqualTo( 199628 );
	}
		//findHighestCalorieIntakeForTopThreeElves
		//findHighestCalorieIntake
		//createListOfElfCalorieIntake
}