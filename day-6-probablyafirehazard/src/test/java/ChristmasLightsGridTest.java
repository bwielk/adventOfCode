import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

	private static Stream<Arguments> entriesToBeParsed() {
		return Stream.of( Arguments.of("turn off 539,243 through 559,965",
				new ChristmasLight( 539, 243 ), new ChristmasLight( 559, 965 )),
				Arguments.of("    turn on 0,0 through 999,999    ",
						new ChristmasLight( 0, 0 ), new ChristmasLight( 999, 999 )),
				Arguments.of("    toggle 100,100 through 999,999    ",
						new ChristmasLight( 100, 100 ), new ChristmasLight( 999, 999 )));
	}

	@ParameterizedTest
	@MethodSource("entriesToBeVerified")
	public void verifyTheEntriesAreCorrectlyFormulated(String entry, boolean result){
		boolean actualResult = christmasLightsGrid.verifyEntryMatchesSentenceModel( entry );
		assertThat(actualResult).isEqualTo( result );
	}

	@ParameterizedTest
	@MethodSource("entriesToBeParsed")
	public void entryCanBeParsedWithChristmasLightEntityCreated(String entry, ChristmasLight
			christmasLight1, ChristmasLight christmasLight2){
		List<ChristmasLight> actualResult = christmasLightsGrid
				.parseEntryForChristmasLightCoords( entry );
		ChristmasLight christmasLight1Actual = actualResult.get( 0 );
		ChristmasLight christmasLight2Actual = actualResult.get( 1 );
		assertThat( christmasLight1Actual.getX() ).isEqualTo( christmasLight1.getX() );
		assertThat( christmasLight2Actual.getX() ).isEqualTo( christmasLight2.getX() );
		assertThat( christmasLight1Actual.getY() ).isEqualTo( christmasLight1.getY() );
		assertThat( christmasLight2Actual.getY() ).isEqualTo( christmasLight2.getY() );
	}

	@Test
	public void higherCoordsOfTheStartingLightComparingWithTheEndOneThrowException(){
		Exception e = assertThrows(  IllegalStateException.class,
				() -> christmasLightsGrid.parseEntryForChristmasLightCoords( "turn off 887,900 through 887,899" ));
		assertThat( e.getMessage() ).isEqualTo( "Bad coordinates - start light must have lower coords values than the end one!" );
	};
}