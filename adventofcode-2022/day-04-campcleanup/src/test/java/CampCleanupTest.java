import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampCleanupTest {

	private CampCleanup campCleanup;

	@BeforeEach
	public void before() {
		campCleanup = new CampCleanup();
	}

	@Test
	public void result() {
		int result = campCleanup.findContainedPairs();
		assert true;
	}

	@Test
	public void countForContainedPairsWorks() {
		List<String> input = List.of( "1, 6, 6, 6", "2, 4, 3, 5", "3, 3, 2, 6" );
		int result = campCleanup.calculateContainedPairs( input );
		assertThat( result ).isEqualTo( 2 );
	}

	@Test
	public void valuesCanBeExtractedFromInputs() {
		String input = "14-63,15-64";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 14, 63, 15, 64 );
	}

	@Test
	public void valuesCanBeExtractedFromInputs_precedingZeros() {
		String input = "04-63,015-064";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 4, 63, 15, 64 );
	}

	@Test
	public void valuesCanBeExtractedFromInputs_zerosAreAccepted() {
		String input = "0-1,0-0";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 0, 1, 0, 0 );
	}

	@Test
	public void valuesCannotBeExtractedFromInputs_negativeValuesInInputResultInNoParsing() {
		String input = "-4--63,015-064";
		assertThrows( NumberFormatException.class, () -> campCleanup.parseEntry( input ) );
	}

	@Test
	public void valuesCannotBeExtractedFromInputs_attemptToCreate3PairsResultsInAnExceptionThrown() {
		String input = "4-6,1-2,1-1";
		Exception e = assertThrows( IllegalStateException.class,
				() -> campCleanup.parseEntry( input ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Only 4 values are accepted as input within the list" );
	}

	@Test
	public void valuesCannotBeExtractedFromInputs_attemptToCreateOnlyOnePairResultsInAnExceptionThrown() {
		String input = "4-6";
		Exception e = assertThrows( IllegalStateException.class,
				() -> campCleanup.parseEntry( input ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Only 4 values are accepted as input within the list" );
	}

	@Test
	public void valuesCannotBeExtractedFromInputs_pairsWithMoreThanOneDigit() {
		String input = "4-6-7,1-2-3";
		Exception e = assertThrows( IllegalStateException.class,
				() -> campCleanup.parseEntry( input ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Only 4 values are accepted as input within the list" );
	}

	@Test
	public void valuesCanBeExtractedFromInputs_onlyDashesAsSeparators() {
		String input = "4-6-0-1";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 4, 6, 0, 1 );
	}

	@Test
	public void valuesCanBeExtractedFromInputs_onlyCommasAsSeparators() {
		String input = "4,6,0,1";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 4, 6, 0, 1 );
	}

	private static Stream<Arguments> firstValueOfAPairGreaterThanTheSecondOne() {
		return Stream.of( Arguments.of( "3-2,15-64" ), Arguments.of( "1-2,15-11" ) );
	}

	@ParameterizedTest
	@MethodSource("firstValueOfAPairGreaterThanTheSecondOne")
	public void valuesCannotBeExtractedFromInputs_firstDigitOfAPairGreaterThanTheSecondThrowsException(
			String input ) {
		Exception e = assertThrows( IllegalStateException.class,
				() -> campCleanup.parseEntry( input ) );
		assertThat( e.getMessage() ).isEqualTo(
				"The first digit of a pair cannot be greater than the second one" );
	}

	@Test
	public void firstPairIsNotContainedWithinTheSecondOne() {
		List<Integer> input = List.of( 1, 2, 2, 3 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isFalse();
	}

	@Test
	public void secondPairIsNotContainedWithinTheFirstOne() {
		List<Integer> input = List.of( 2, 4, 3, 5 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isFalse();
	}

	@Test
	public void firstPairIsContainedWithinTheSecondOne() {
		List<Integer> input = List.of( 2, 4, 1, 5 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void secondPairIsContainedWithinTheFirstOne() {
		List<Integer> input = List.of( 1, 6, 4, 5 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void secondPairIsContainedWithinTheFirstOneBySingularValue() {
		List<Integer> input = List.of( 1, 6, 6, 6 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void firstPairIsContainedWithinTheSecondOneBySingularValue() {
		List<Integer> input = List.of( 3, 3, 2, 6 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void firstPairIsContainedWithinTheSecondOne_extremeEdges() {
		List<Integer> input = List.of( 6, 6, 2, 6 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void pairsOverlappingAtAll_singleSection() {
		List<Integer> input = List.of( 5, 7, 7, 9 );
		boolean result = campCleanup.findIfPairsOverlapAtAll( input );
		assertThat( result ).isTrue();
	}

	@Test
	public void pairsOverlappingAtAll_entireSection() {
		List<Integer> input = List.of( 6, 9, 2, 8 );
		boolean result = campCleanup.findIfPairsOverlapAtAll( input );
		assertThat( result ).isTrue();
	}
}
