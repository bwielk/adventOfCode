import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CampCleanupTest {

	private CampCleanup campCleanup;

	@BeforeEach
	public void before(){
		campCleanup = new CampCleanup();
	}

	@Test
	public void firstPairIsNotContainedWithinTheSecondOne(){
		List<Integer> input = List.of(1,2,2,3);
		boolean result = campCleanup.findIfPairsAreMutuallyContained(input);
		assertThat(result).isFalse();
	}

	@Test
	public void secondPairIsNotContainedWithinTheFirstOne() {
		List<Integer> input = List.of( 2, 4, 3, 5 );
		boolean result = campCleanup.findIfPairsAreMutuallyContained( input );
		assertThat( result ).isFalse();
	}

	@Test
	public void firstPairIsContainedWithinTheSecondOne(){
		List<Integer> input = List.of(2,4,1,5);
		boolean result = campCleanup.findIfPairsAreMutuallyContained(input);
		assertThat(result).isTrue();
	}

	@Test
	public void secondPairIsContainedWithinTheFirstOne(){
		List<Integer> input = List.of(1,6,4,6);
		boolean result = campCleanup.findIfPairsAreMutuallyContained(input);
		assertThat(result).isTrue();
	}

}