import static java.util.Collections.EMPTY_LIST;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
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
	public void inputCannotBeTranslatedIntoAListOfSumsOfIntakesIfEmptyCollection() {
		List sums = calorieCounting.createListOfElfCalorieIntake( EMPTY_LIST );
		assertThat( sums).isEmpty();
	}

	@Test
	public void inputIsTranslatedIntoAListOf0sIfContainsJustAnEmptyString() {
		List<String> inputs = List.of( "" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake( inputs );
		assertThat( sums ).containsExactlyInAnyOrder( 0 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithSingularEntry() {
		List<String> inputs = List.of( "2" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake( inputs );
		assertThat( sums.size() ).isEqualTo( 1 );
		assertThat( sums ).containsExactlyInAnyOrder( 2 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithSingularSpacing() {
		List<String> inputs = List.of( "1", "", "2", "3" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake( inputs );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithMultipleSpacing() {
		List<String> inputs = List.of( "1", "", "2", "3", "", "", "4", "5", "6", "", "", "", "7",
				"8" );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake( inputs );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 0, 15, 0, 0, 15 );
	}

	@Test
	public void inputCanBeTranslatedIntoAListOfSumsOfIntakesStandardFormatWithMultipleLeadingAndTrailingSpacing() {
		List<String> inputs = List.of( " ", "1", "", "2", "3", "", "", "4", "5", "6", "", "", "",
				"7", "8", " " );
		List<Integer> sums = calorieCounting.createListOfElfCalorieIntake( inputs );
		assertThat( sums ).containsExactlyInAnyOrder( 1, 5, 0, 15, 15, 0, 0);
	}

	@Test
	public void canFindHighestIntake() {
		List<Integer> unmodifiableList = List.of(2, 3, 4, 55);
		List<Integer> inputs = new ArrayList<>(unmodifiableList);
		int result = calorieCounting.findHighestCalorieIntake( inputs );
		assertThat( result ).isEqualTo( 55 );
	}

	@Test
	public void canFindHighestIntakeWithDuplications() {
		List<Integer> unmodifiableList = List.of(2, 55, 3, 4, 55);
		List<Integer> inputs = new ArrayList<>(unmodifiableList);
		int result = calorieCounting.findHighestCalorieIntake( inputs );
		assertThat( result ).isEqualTo( 55 );
	}

	@Test
	public void canCalculateSumOfThreeHighestIntakes() {
		List<Integer> unmodifiableList = List.of(2, 54, 3, 4, 55, 51);
		List<Integer> inputs = new ArrayList<>(unmodifiableList);
		int result = calorieCounting.findSumOfHighestCalorieIntakeForTopThreeElves( inputs );
		assertThat( result ).isEqualTo( 160 );
	}

	@Test
	public void canCalculateSumOfThreeHighestIntakes_sumConsidersOtherRunningUpResultsOfTheSameValue() {
		List<Integer> unmodifiableList = List.of(2, 54, 5, 4, 55, 51, 51);
		List<Integer> inputs = new ArrayList<>(unmodifiableList);
		int result = calorieCounting.findSumOfHighestCalorieIntakeForTopThreeElves( inputs );
		assertThat( result ).isEqualTo( 211 );
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