import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChristmasLightsManagement {

	public static final String VERIFY_SENTENCE_REGEX = "^((turn off )|(turn on )|(toggle ))+[\\d]{1,3}+,+[\\d]{1,3}+ through +[\\d]{1,3}+,+[\\d]{1,3}";
	public static final String CLEAN_UP_REGEX = "(turn on |turn off |toggle |through )";
	private final ChristmasLight[][] christmasLightsMatrix;

	public ChristmasLightsManagement( ChristmasLight[][] christmasLightsMatrix ) {
		this.christmasLightsMatrix = christmasLightsMatrix;
	}

	public boolean verifyEntryMatchesSentenceModel( String entry ) {
		return entry.
				trim().toLowerCase().matches( VERIFY_SENTENCE_REGEX );
	}

	public LightsAction specifyActionFromEntry( String entry ) {
		LightsAction lightsAction = null;
		if ( entry.startsWith( "turn on" ) ) {
			lightsAction = LightsAction.TURN_ON;
		} else if ( entry.startsWith( "turn off" ) ) {
			lightsAction = LightsAction.TURN_OFF;
		} else if ( entry.startsWith( "toggle" ) ) {
			lightsAction = LightsAction.TOGGLE;
		}
		return lightsAction;
	}

	public List<ChristmasLightCoords> parseEntryForChristmasLightCoords( String entry ) {
		String cleanedUp = entry.replaceAll( CLEAN_UP_REGEX, "" );
		String[] splitCoordsAsString = cleanedUp.split( " " );
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

	public void processLightsRequest(
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
						if ( !christmasLightsMatrix[x][y].isSwitchedOn() ) {
							christmasLightsMatrix[x][y].switchOn();
						}
						christmasLightsMatrix[x][y] = increaseBrightness(
								christmasLightsMatrix[x][y], 1 );
						break;
					case TURN_OFF:
						if ( christmasLightsMatrix[x][y].isSwitchedOn() ) {
							christmasLightsMatrix[x][y].switchOff();
						}
						christmasLightsMatrix[x][y] = decreaseBrightness(
								christmasLightsMatrix[x][y], 1 );
						break;
					case TOGGLE:
						if ( christmasLightsMatrix[x][y].isSwitchedOn() ) {
							christmasLightsMatrix[x][y].switchOff();
						} else {
							christmasLightsMatrix[x][y].switchOn();
						}
						christmasLightsMatrix[x][y] = increaseBrightness(
								christmasLightsMatrix[x][y], 2 );
						break;
					}
				}
			}
		}
	}

	public ChristmasLight increaseBrightness( ChristmasLight christmasLight, int value ) {
		christmasLight.setBrightness( christmasLight.getBrightness() + value );
		return christmasLight;
	}

	public ChristmasLight decreaseBrightness( ChristmasLight christmasLight, int value ) {
		if ( christmasLight.getBrightness() > 0 ) {
			christmasLight.setBrightness( christmasLight.getBrightness() - value );
		}
		return christmasLight;
	}

	public ChristmasLightsManagementReadings processLightsSchema( String schemaInput ) {
		List<String> entries = readTheSchemaFile( schemaInput );
		for ( String entry : entries ) {
			if ( verifyEntryMatchesSentenceModel( entry ) ) {
				List<ChristmasLightCoords> actionCoords = parseEntryForChristmasLightCoords(
						entry );
				LightsAction lightsAction = specifyActionFromEntry( entry );
				processLightsRequest( actionCoords, lightsAction );
			}
		}
		return gatherFinalReadings( schemaInput );
	}

	public ChristmasLightsManagementReadings gatherFinalReadings( String schemaName ) {
		int lightsOn = 0;
		int totalBrightness = 0;
		for ( int x = 0; x < christmasLightsMatrix.length; x++ ) {
			for ( int y = 0; y < christmasLightsMatrix[x].length; y++ ) {
				if ( christmasLightsMatrix[x][y].isSwitchedOn() ) {
					lightsOn++;
				}
				totalBrightness += christmasLightsMatrix[x][y].getBrightness();
			}
		}
		return new ChristmasLightsManagementReadings( lightsOn, totalBrightness, schemaName );
	}

	public List<String> readTheSchemaFile( String schemaInput ) {
		return FileReaderHelper.readFileAsLinesOfStrings( ChristmasLightsManagement.class,
				schemaInput )
				.stream()
				.map( String::trim )
				.filter( e -> !e.isEmpty() || !e.isBlank() )
				.collect( Collectors.toList() );
	}
}
