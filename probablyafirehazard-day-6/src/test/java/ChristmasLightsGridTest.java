import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasLightsGridTest {

	ChristmasLightsGrid christmasLightsGrid;

	@BeforeEach
	public void before(){
		christmasLightsGrid = new ChristmasLightsGrid();
	}


	private static Stream<Arguments> entriesToBeVerified() {
		return Stream.of( Arguments.of("turn on 887,9 through 959,629", true),
				Arguments.of("turn off 887,900 through 959,629", true),
				Arguments.of("turn off 887,900 through 959,629", true),
				Arguments.of("     turn off 887,900 through 959,629     ", true),
				Arguments.of("switch on 887,900 up to 959,629", false),
				Arguments.of("turn off 8 through 959629", true),
				Arguments.of("from 887,900 up to 959,629", false),
				Arguments.of("887,900 through 959,629", false),
				Arguments.of("switch off 887,911 through 959,629", false));
	}

	@ParameterizedTest
	@MethodSource("entriesToBeVerified")
	public void verifyTheEntriesAreCorrectlyFormulated(String entry, boolean result){
		boolean actualResult = christmasLightsGrid.verifyEntryMatchesSentenceModel( entry );
		assertThat(actualResult).isEqualTo( result );
	}

}