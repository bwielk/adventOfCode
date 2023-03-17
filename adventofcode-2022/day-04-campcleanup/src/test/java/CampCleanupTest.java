import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampCleanupTest {

	private CampCleanup campCleanup;

	@BeforeEach
	public void before() {
		campCleanup = new CampCleanup();
	}

	@Test
	public void countForContainedPairsWorks() {
		List<String> input = List.of("1, 6, 6, 6", "2, 4, 3, 5", "3, 3, 2, 6");
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
		String input = "04-63,0155-064";
		List<Integer> result = campCleanup.parseEntry( input );
		assertThat( result ).containsExactlyInAnyOrder( 4, 63, 155, 64 );
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
}