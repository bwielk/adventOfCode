import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChristmasLightsManagement {

	public static final String VERIFY_SENTENCE_REGEX = "^(?=.*(turn on|turn off|toggle)+( )+(.)+(through)).*$";
	public static final String CLEAN_UP_REGEX = "(turn on|turn off|toggle|through )";
	private final boolean[][] christmasLightsMatrix;

	public ChristmasLightsManagement( boolean[][] christmasLightsMatrix ) {
		this.christmasLightsMatrix = christmasLightsMatrix;
	}

	public boolean verifyEntryMatchesSentenceModel( String entry ) {
		return entry.matches( VERIFY_SENTENCE_REGEX );
	}

	public List<ChristmasLightCoords> parseEntryForChristmasLightCoords( String entry ) {
		String cleanedUp = entry.trim().replaceAll( CLEAN_UP_REGEX, "" );
		String[] splitCoordsAsString = cleanedUp.trim().split( " " );
		String[] firstChristmasLightCoords = splitCoordsAsString[0].split( "," );
		String[] secondChristmasLightCoords = splitCoordsAsString[1].split( "," );
		List<ChristmasLightCoords> lights = new ArrayList<>();
		int christmasLight1x = Integer.parseInt( firstChristmasLightCoords[0] );
		int christmasLight1y = Integer.parseInt( firstChristmasLightCoords[1] );
		int christmasLight2x = Integer.parseInt( secondChristmasLightCoords[0] );
		int christmasLight2y = Integer.parseInt( secondChristmasLightCoords[1] );
		if ( ListHelper.checkIfAllIntegersInAListAreGreaterThanZero(
				Arrays.asList( christmasLight1x, christmasLight2x, christmasLight1y,
						christmasLight2y ) ) ) {
			if ( christmasLight1x <= christmasLight2x && christmasLight1y <= christmasLight2y ) {
				lights.add( new ChristmasLightCoords( christmasLight1x, christmasLight1y ) );
				lights.add( new ChristmasLightCoords( christmasLight2x, christmasLight2y ) );
			} else {
				throw new IllegalStateException(
						"Bad coordinates - start light must have " + "lower coords values than the end one!" );
			}
		} else {
			throw new IllegalStateException(
					"Bad coordinates - coordinates must be greater than 0!" );
		}
		return lights;
	}

	public void switchLightsOnOrOff(
			List<ChristmasLightCoords> christmasLightsCoordsToManipulateCoords,
			LightsAction lightsAction ) {
		if ( christmasLightsCoordsToManipulateCoords.size() == 2 ) {
			ChristmasLightCoords christmasLightCoordsCoord1 = christmasLightsCoordsToManipulateCoords
					.get( 0 );
			ChristmasLightCoords christmasLightCoordsCoord2 = christmasLightsCoordsToManipulateCoords
					.get( 1 );
			for ( int x = christmasLightCoordsCoord1.getX(); x <= christmasLightCoordsCoord2.getX(); x++ ) {
				for ( int y = christmasLightCoordsCoord1.getY(); y <= christmasLightCoordsCoord2.getY(); y++ ) {
					switch ( lightsAction ) {
					case TURN_ON:
						if ( !christmasLightsMatrix[x][y] ) {
							christmasLightsMatrix[x][y] = true;
						}
						break;
					case TURN_OFF:
						if ( christmasLightsMatrix[x][y] ) {
							christmasLightsMatrix[x][y] = false;
						}
						break;
					}
				}
			}
		}
	}

	public void toggleLights() {
		for ( int x = 0; x <= christmasLightsMatrix.length - 1; x++ ) {
			for ( int y = 0; y <= christmasLightsMatrix[x].length - 1; y++ ) {
				christmasLightsMatrix[x][y] = !christmasLightsMatrix[x][y];
			}
		}
	}

	public int numberOfLightsLit() {
		int lightsOn = 0;
		for ( int x = 0; x < christmasLightsMatrix.length; x++ ) {
			for ( int y = 0; y < christmasLightsMatrix[x].length; y++ ) {
				if ( christmasLightsMatrix[x][y] ) {
					lightsOn++;
				}
				;
			}
		}
		return lightsOn;
	}
}
