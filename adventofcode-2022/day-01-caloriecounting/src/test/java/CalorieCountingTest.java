import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalorieCountingTest {

	private CalorieCounting calorieCounting;

	@BeforeEach
	public void before() {
		calorieCounting = new CalorieCounting();
	}

	@Test
	public void inputCannotBeTranslatedIntoAListOfSumsOfIntakesIfFileEmpty() {
		List<String> inputs = List.of( "" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake(inputs);
		assertThat( sums.size() ).isEqualTo( 0 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithSingularEntry() {
		List<String> inputs = List.of( "1" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake(inputs);
		assertThat( sums.size() ).isEqualTo( 1 );
		assertThat( sums ).containsExactlyInAnyOrder( 1 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithSingularSpacing() {
		List<String> inputs = List.of( "1", "", "2", "3", "", "4", "5", "6", "", "7", "8" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake(inputs);
		assertThat( sums.size() ).isEqualTo( 4 );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 15, 15 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithMultipleSpacing() {
		List<String> inputs = List.of( "1", "", "2", "3", "", "", "4", "5", "6", "", "", "", "7", "8" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake(inputs);
		assertThat( sums.size() ).isEqualTo( 4 );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 15, 15 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithMultipleLeadingAndTrailingSpacing() {
		List<String> inputs = List.of(" ", "1", "", "2", "3", "", "", "4", "5", "6", "", "", "", "7", "8", " " );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake(inputs);
		assertThat( sums.size() ).isEqualTo( 4 );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 15, 15 );
	}

	@Test
	public void canFindHighestIntakeBasedOnRealInput() {
		int result = calorieCounting.runFindHighestCalorieIntake( "input.txt" );
		assertThat( result ).isEqualTo( 67633 );
	}

	@Test
	public void canFindSumOfThreeHighestIntakesBasedOnRealInput() {
		int result = calorieCounting.runFindHighestCalorieIntakeForTopThreeElves( "input.txt" );
		assertThat( result ).isEqualTo( 199628 );
	}
}