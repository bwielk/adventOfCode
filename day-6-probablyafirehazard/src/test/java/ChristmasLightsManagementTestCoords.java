import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasLightsManagementTestCoords {

	ChristmasLightsManagement christmasLightsManagement;
	boolean[][] christmasLightsMatrixCoords;

	@BeforeEach
	public void before() {
		christmasLightsMatrixCoords = new boolean[3][3];
		for ( int x = 0; x < christmasLightsMatrixCoords.length; x++ ) {
			Arrays.fill( christmasLightsMatrixCoords[x], false );
		}
		christmasLightsManagement = new ChristmasLightsManagement( christmasLightsMatrixCoords );
	}

	private static Stream<Arguments> entriesToBeVerified() {
		return Stream.of( Arguments.of( "turn on 887,9 through 959,629", true ),
				Arguments.of( "turn off 887,900 through 959,629", true ),
				Arguments.of( "turn off 887,900 through 959,629", true ),
				Arguments.of( "     turn off 887,900 through 959,629     ", true ),
				Arguments.of( "switch on 887,900 up to 959,629", false ),
				Arguments.of( "turn off 8 through 959629", true ),
				Arguments.of( "from 887,900 up to 959,629", false ),
				Arguments.of( "887,900 through 959,629", false ),
				Arguments.of( "switch off 887,911 through 959,629", false ) );
	}

	private static Stream<Arguments> entriesToBeParsed() {
		return Stream.of( Arguments.of( "turn off 539,243 through 559,965",
				new ChristmasLightCoords( 539, 243 ), new ChristmasLightCoords( 559, 965 ) ),
				Arguments.of( "    turn on 0,0 through 999,999    ",
						new ChristmasLightCoords( 0, 0 ), new ChristmasLightCoords( 999, 999 ) ),
				Arguments.of( "    toggle 100,100 through 999,999    ",
						new ChristmasLightCoords( 100, 100 ),
						new ChristmasLightCoords( 999, 999 ) ) );
	}

	@ParameterizedTest
	@MethodSource("entriesToBeVerified")
	public void verifyTheEntriesAreCorrectlyFormulated( String entry, boolean result ) {
		boolean actualResult = christmasLightsManagement.verifyEntryMatchesSentenceModel( entry );
		assertThat( actualResult ).isEqualTo( result );
	}

	@ParameterizedTest
	@MethodSource("entriesToBeParsed")
	public void entryCanBeParsedWithChristmasLightEntityCreated( String entry,
			ChristmasLightCoords christmasLightCoords1,
			ChristmasLightCoords christmasLightCoords2 ) {
		List<ChristmasLightCoords> actualResult = christmasLightsManagement.parseEntryForChristmasLightCoords(
				entry );
		ChristmasLightCoords christmasLightCoords1Actual = actualResult.get( 0 );
		ChristmasLightCoords christmasLightCoords2Actual = actualResult.get( 1 );
		assertThat( christmasLightCoords1Actual.getX() ).isEqualTo( christmasLightCoords1.getX() );
		assertThat( christmasLightCoords2Actual.getX() ).isEqualTo( christmasLightCoords2.getX() );
		assertThat( christmasLightCoords1Actual.getY() ).isEqualTo( christmasLightCoords1.getY() );
		assertThat( christmasLightCoords2Actual.getY() ).isEqualTo( christmasLightCoords2.getY() );
	}

	@Test
	public void higherCoordsOfTheStartingLightComparingWithTheEndOneThrowException() {
		Exception e = assertThrows( IllegalStateException.class,
				() -> christmasLightsManagement.parseEntryForChristmasLightCoords(
						"turn off 887,900 through 887,899" ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Bad coordinates - start light must have lower coords values than the end one!" );
	}

	@Test
	public void coordsThatAreLessThan0ThrowException() {
		Exception e = assertThrows( IllegalStateException.class,
				() -> christmasLightsManagement.parseEntryForChristmasLightCoords(
						"turn off -22,-10 through -1,1" ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Bad coordinates - coordinates must be greater than 0!" );
	}

	@Test
	public void turningOnAllLightsInGrid() {
		List<ChristmasLightCoords> christmasLightsToManipulateCoords = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsToManipulateCoords,
				LightsAction.TURN_ON );
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 9 );
	}

	@Test
	public void turningOffAllLightsInGrid() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn off 0,0 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOff,
				LightsAction.TURN_OFF );
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 0 );
	}

	@Test
	public void turnOnOnly4LightsOutOf9() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 1,1" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 4 );
	}

	@Test
	public void turnOffOnly4LightsOutOf9() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 1,1 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOff,
				LightsAction.TURN_OFF );
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 5 );
	}

	@Test
	public void toggling5LightsOffResultsIn5LightsOnAnd4Off() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 1,1" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		christmasLightsManagement.toggleLights();
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 5 );
	}

	@Test
	public void toggling5LightsOnResultsIn5LightsOffAnd4On() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn off 1,1 through 2,2" );
		christmasLightsManagement.switchLightsOnOrOff( christmasLightsCoordsSwitchedOff,
				LightsAction.TURN_OFF );
		christmasLightsManagement.toggleLights();
		int areLightsAreOn = christmasLightsManagement.numberOfLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 4 );
	}
}
