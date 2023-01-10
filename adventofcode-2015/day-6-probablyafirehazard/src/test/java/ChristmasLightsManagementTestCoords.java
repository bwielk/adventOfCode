import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChristmasLightsManagementTestCoords {

	ChristmasLightsManagement christmasLightsManagement;
	ChristmasLight[][] christmasLightsMatrixCoords;

	@BeforeEach
	public void before() {
		christmasLightsMatrixCoords = new ChristmasLight[3][3];
		for ( int x = 0; x < christmasLightsMatrixCoords.length; x++ ) {
			for ( int y = 0; y < christmasLightsMatrixCoords[x].length; y++ ) {
				christmasLightsMatrixCoords[x][y] = new ChristmasLight();
			}
			;
		}
		christmasLightsManagement = new ChristmasLightsManagement( christmasLightsMatrixCoords );
	}

	private static Stream<Arguments> entriesToBeVerified() {
		return Stream.of( Arguments.of( "turn on 887,9 through 959,629", true ),
				Arguments.of( "turn on 887,91 through 959,621", true ),
				Arguments.of( "turn on 887,91 through 959,6", true ),
				Arguments.of( "turn off 887,900 through 959,629", true ),
				Arguments.of( "turn off 887,900 through 959,629", true ),
				Arguments.of( "     turn off 887,900 through 959,629     ", true ),
				Arguments.of( "switch on 887,900 up to 959,629", false ),
				Arguments.of( "turn off 8 through 959629", false ),
				Arguments.of( "from 887,900 up to 959,629", false ),
				Arguments.of( "887,900 through 959,629", false ),
				Arguments.of( "switch off 887,911 through 959,629", false ),
				Arguments.of( "switch on off 887,911 through 959,629", false ),
				Arguments.of( "toggle off 887,911 through 959,629", false ),
				Arguments.of( "toggle on 887,911 through 959,629", false ),
				Arguments.of( "toggle off turn on 887,911 through 959,629", false ),
				Arguments.of( "turn off toggle on 887,911 through 959,629", false ),
				Arguments.of( "turn off toggle on 887,911 through 959,629", false ),
				Arguments.of( "887,911 turn off 959,629 turn on", false ) );
	}

	private static Stream<Arguments> expectedAction() {
		return Stream.of( Arguments.of( "turn on 887,9 through 959,629", LightsAction.TURN_ON ),
				Arguments.of( "turn off 887,900 through 959,629", LightsAction.TURN_OFF ),
				Arguments.of( "toggle 887,900 through 959,629", LightsAction.TOGGLE ) );
	}

	private static Stream<Arguments> entriesToBeParsed() {
		return Stream.of( Arguments.of( "turn off 539,243 through 559,965",
				new ChristmasLightCoords( 539, 243 ), new ChristmasLightCoords( 559, 965 ) ),
				Arguments.of( "turn on 0,0 through 999,999", new ChristmasLightCoords( 0, 0 ),
						new ChristmasLightCoords( 999, 999 ) ),
				Arguments.of( "toggle 100,100 through 999,999",
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
	@MethodSource("expectedAction")
	public void actionCanBeExtractedFromEntry( String entry, LightsAction expectedAction ) {
		LightsAction actualResult = christmasLightsManagement.specifyActionFromEntry( entry );
		assertThat( actualResult ).isEqualTo( expectedAction );
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
		christmasLightsManagement.processLightsRequest( christmasLightsToManipulateCoords,
				LightsAction.TURN_ON );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		assertThat( areLightsAreOn ).isEqualTo( 9 );
	}

	@Test
	public void turningOffAllLightsInGrid() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn off 0,0 through 2,2" );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				LightsAction.TURN_OFF );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		;
		assertThat( areLightsAreOn ).isEqualTo( 0 );
	}

	@Test
	public void turnOnOnly4LightsOutOf9() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 1,1" );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		;
		assertThat( areLightsAreOn ).isEqualTo( 4 );
	}

	@Test
	public void turnOffOnly4LightsOutOf9() {
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 0,0 through 2,2" );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				LightsAction.TURN_ON );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				"turn on 1,1 through 2,2" );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				LightsAction.TURN_OFF );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		;
		assertThat( areLightsAreOn ).isEqualTo( 5 );
	}

	@Test
	public void toggling5LightsOffResultsIn5LightsOnAnd4Off() {
		String turnOnStatement = "turn on 0,0 through 1,1";
		String toggleStatement = "toggle 0,0 through 2,2";
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement ) );
		List<ChristmasLightCoords> christmasLightsCoordsSToggle = christmasLightsManagement.parseEntryForChristmasLightCoords(
				toggleStatement );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSToggle,
				christmasLightsManagement.specifyActionFromEntry( toggleStatement ) );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		;
		assertThat( areLightsAreOn ).isEqualTo( 5 );
	}

	@Test
	public void toggling5LightsOnResultsIn5LightsOffAnd4On() {
		String turnOnStatement = "turn on 0,0 through 2,2";
		String turnOffStatement = "turn off 1,1 through 2,2";
		String toggleStatement = "toggle 0,0 through 2,2";
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement ) );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOffStatement );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				christmasLightsManagement.specifyActionFromEntry( turnOffStatement ) );
		List<ChristmasLightCoords> christmasLightsCoordsSToggle = christmasLightsManagement.parseEntryForChristmasLightCoords(
				toggleStatement );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSToggle,
				christmasLightsManagement.specifyActionFromEntry( toggleStatement ) );
		int areLightsAreOn = christmasLightsManagement.gatherFinalReadings( "" ).getLightsLit();
		;
		assertThat( areLightsAreOn ).isEqualTo( 4 );
	}

	@Test
	public void brightnessTestIncreasingWithTurningOnTheLights() {
		String turnOnStatement1 = "turn on 0,0 through 2,2";
		String turnOnStatement2 = "turn on 0,0 through 1,1";
		String turnOnStatement3 = "turn on 0,0 through 0,0";
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement1 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement1 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement2 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement2 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSToggle = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement3 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSToggle,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement3 ) );
		int totalBrightness = christmasLightsManagement.gatherFinalReadings( "" )
				.getLightsBrightness();
		assertThat( totalBrightness ).isEqualTo( 14 );
	}

	@Test
	public void brightnessTestDecreasingWithTurningOffTheLights() {
		String turnOnStatement1 = "turn on 0,0 through 2,2";
		String turnOffStatement1 = "turn off 0,0 through 1,1";
		String turnOffStatement2 = "turn off 0,0 through 0,0";
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement1 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement1 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOffStatement1 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				christmasLightsManagement.specifyActionFromEntry( turnOffStatement1 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSToggle = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOffStatement2 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSToggle,
				christmasLightsManagement.specifyActionFromEntry( turnOffStatement2 ) );
		int totalBrightness = christmasLightsManagement.gatherFinalReadings( "" )
				.getLightsBrightness();
		assertThat( totalBrightness ).isEqualTo( 5 );
	}

	@Test
	public void brightnessIncreaseWithTogglingTheLights() {
		String turnOnStatement1 = "turn on 0,0 through 2,2";
		String toggleStatement1 = "toggle 0,0 through 1,1";
		String toggleStatement2 = "toggle 0,0 through 0,0";
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOn = christmasLightsManagement.parseEntryForChristmasLightCoords(
				turnOnStatement1 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOn,
				christmasLightsManagement.specifyActionFromEntry( turnOnStatement1 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSwitchedOff = christmasLightsManagement.parseEntryForChristmasLightCoords(
				toggleStatement1 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSwitchedOff,
				christmasLightsManagement.specifyActionFromEntry( toggleStatement1 ) );
		List<ChristmasLightCoords> christmasLightsCoordsSToggle = christmasLightsManagement.parseEntryForChristmasLightCoords(
				toggleStatement2 );
		christmasLightsManagement.processLightsRequest( christmasLightsCoordsSToggle,
				christmasLightsManagement.specifyActionFromEntry( toggleStatement2 ) );
		int totalBrightness = christmasLightsManagement.gatherFinalReadings( "" )
				.getLightsBrightness();
		assertThat( totalBrightness ).isEqualTo( 19 );
	}

	@Test
	public void processChristmasLightsInput() {
		ChristmasLightsManagementReadings actualResults = christmasLightsManagement.processLightsSchema(
				"correctlyFormattedEntry.txt" );
		assertThat( actualResults.getLightsLit() ).isEqualTo( 4 );
	}

	@Test
	@Disabled("Investigation required")
	public void fileProcessingFailsIfAtLeastOneCoordIsLessThan0() {
		Exception e = assertThrows( IllegalStateException.class,
				() -> christmasLightsManagement.processLightsSchema(
						"correctEntriesWithAnEntryWithCoordsLessThan0.txt" ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Bad coordinates - coordinates must be greater than 0!" );
	}

	@Test
	@Disabled("Investigation required")
	public void fileProcessingFailsIfStartCoordsAreLessThanTheEndCoords() {
		Exception e = assertThrows( IllegalStateException.class,
				() -> christmasLightsManagement.processLightsSchema(
						"correctEntriesWithAnEntryWithStartCoordsLessThanEndCoords.txt" ) );
		assertThat( e.getMessage() ).isEqualTo(
				"Bad coordinates - start light must have lower coords values than the end one!" );
	}

	@Test
	public void fileWithoutRecordsReturns0() {
		ChristmasLightsManagementReadings actualResults = christmasLightsManagement.processLightsSchema(
				"emptyFile.txt" );
		assertThat( actualResults.getLightsLit() ).isEqualTo( 0 );
	}

	@Test
	public void wronglyFormulatedEntriesAreIgnoredAndPrcessingContinues() {
		ChristmasLightsManagementReadings actualResults = christmasLightsManagement.processLightsSchema(
				"correctlyFormattedEntriesWithSomeIncorrectlyFormulatedOnes.txt" );
		assertThat( actualResults.getLightsLit() ).isEqualTo( 5 );
	}

	@Test
	public void processTheActualChristmasLightsSchema() {
		christmasLightsMatrixCoords = new ChristmasLight[1000][1000];
		for ( int x = 0; x < christmasLightsMatrixCoords.length; x++ ) {
			for ( int y = 0; y < christmasLightsMatrixCoords[x].length; y++ ) {
				christmasLightsMatrixCoords[x][y] = new ChristmasLight();
			}
		}
		ChristmasLightsManagement christmasLightsManagement = new ChristmasLightsManagement(
				christmasLightsMatrixCoords );
		ChristmasLightsManagementReadings actualResult = christmasLightsManagement.processLightsSchema(
				"input.txt" );
		assertThat( actualResult.getLightsLit() ).isEqualTo( 377891 );
		assertThat( actualResult.getLightsBrightness() ).isEqualTo( 14110788 );
		assertThat( actualResult.getSchemaName() ).isEqualTo( "input.txt" );
	}
}
