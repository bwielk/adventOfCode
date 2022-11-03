import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SantaMap {

	private final List<HouseCoords> historyOfVisitedHouses = new ArrayList<>();

	public int runCountingHousesFromSchema(String schemaName){
		String input = readSchemaFile( schemaName );
		return countHouses( input );
	}

	public int countHouses( final String entry ) {
		String cleanedUpEntry = entry.toLowerCase()
				.replace( " ", "" )
				.replaceAll( "[^v<>^]", "" );

		if ( !cleanedUpEntry.isEmpty() ) {
			HouseCoords house = new HouseCoords( 0, 0 );
			historyOfVisitedHouses.add( house );
			for ( int i = 0; i < cleanedUpEntry.length(); i++ ) {
				char currentChar = cleanedUpEntry.charAt( i );
				Directions direction = defineDirection( currentChar );
				house = new HouseCoords( house.getX() + direction.getX(),
						house.getY() + direction.getY() );
				checkIfTheHouseHasAlreadyBeenVisited( house );
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
		List<HouseCoords> hsc = historyOfVisitedHouses.stream().
				filter( h ->  h.getX() == house.getX() && h.getY() == house.getY()).collect(
				Collectors.toList());
		if(hsc.isEmpty()){
			historyOfVisitedHouses.add( house );
		}
	}

	private String readSchemaFile(String schemaName){
		List<String> parsedInput = FileReaderHelper.readFileAsLinesOfStrings( SantaMap.class, schemaName );
		if(parsedInput.size() != 1){
			throw new IllegalStateException( ErrorMessages.TOO_MANY_LINES);
		}
		return parsedInput.get( 0 );
	}
}

