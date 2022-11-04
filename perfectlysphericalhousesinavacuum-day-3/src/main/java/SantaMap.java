import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SantaMap {

	private final List<HouseCoords> historyOfVisitedHouses = new ArrayList<>();

	public int runCountingHousesFromSchema( String schemaName, RunningMode runningMode ) {
		String input = readSchemaFile( schemaName );
		return countHouses( input, runningMode );
	}

	public int countHouses( final String entry, final RunningMode runningMode ) {

		String cleanedUpEntry = cleanUpEntry(entry);
		HouseCoords houseVisitedBySanta = new HouseCoords( 0, 0 );
		HouseCoords houseVisitedByRoboSanta = new HouseCoords( 0, 0 );

		if ( !cleanedUpEntry.isEmpty() ) {
			historyOfVisitedHouses.add( houseVisitedBySanta );
			for ( int i = 0; i < cleanedUpEntry.length(); i++) {
				char currentChar = cleanedUpEntry.charAt( i );
				houseVisitedBySanta = executeAMove(currentChar, houseVisitedBySanta);
				if(i+1<cleanedUpEntry.length() && runningMode.equals( RunningMode.WITH_ROBO_SANTA )){
					char roboSantaChar = cleanedUpEntry.charAt( i+1 );
					houseVisitedByRoboSanta = executeAMove( roboSantaChar, houseVisitedByRoboSanta );
					i++;
				}
			}
		}
		return historyOfVisitedHouses.size();
	}

	private Directions defineDirection( char character ) {
		Directions direction;
		switch ( character ) {
		case '^':
			direction = Directions.UP;
			break;
		case '>':
			direction = Directions.RIGHT;
			break;
		case 'v':
			direction = Directions.DOWN;
			break;
		case '<':
			direction = Directions.LEFT;
			break;
		default:
			direction = Directions.NOTHING;
			break;
		}
		return direction;
	}

	private void checkIfTheHouseHasAlreadyBeenVisited( HouseCoords house ) {
		List<HouseCoords> hsc = historyOfVisitedHouses.stream()
				.filter( h -> h.getX() == house.getX() && h.getY() == house.getY() )
				.collect( Collectors.toList() );
		if ( hsc.isEmpty() ) {
			historyOfVisitedHouses.add( house );
		}
	}

	private String readSchemaFile( String schemaName ) {
		List<String> parsedInput = FileReaderHelper.readFileAsLinesOfStrings( SantaMap.class,
				schemaName );
		if ( parsedInput.size() != 1 ) {
			throw new IllegalStateException( ErrorMessages.TOO_MANY_LINES );
		}
		return parsedInput.get( 0 );
	}

	private String cleanUpEntry( String entry ){
		String cleanedUpEntry = entry.toLowerCase()
				.replace( " ", "" )
				.replaceAll( "[^v<>^]", "" );
		return cleanedUpEntry;
	}

	public static void main( String[] args ) {
		SantaMap santaMap = new SantaMap();
		System.out.println(santaMap.runCountingHousesFromSchema( "input.txt" , RunningMode.WITH_ROBO_SANTA));
	}

	private HouseCoords executeAMove(char currentChar, HouseCoords visitedHouse){
		Directions direction = defineDirection( currentChar );
		visitedHouse = new HouseCoords( visitedHouse.getX() + direction.getX(),
				visitedHouse.getY() + direction.getY() );
		checkIfTheHouseHasAlreadyBeenVisited( visitedHouse );
		return visitedHouse;
	}

}

