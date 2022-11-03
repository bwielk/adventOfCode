import java.util.ArrayList;
import java.util.List;

public class SantaMap {

	private boolean alreadyExists = false;
	private final List<HouseCoords> historyOfVisitedHouses = new ArrayList<>();

	public int countHouses( final String s ) {
		HouseCoords house = new HouseCoords( 0,0 );
		historyOfVisitedHouses.add( house );

		for(int i=0; i<s.length(); i++){
			char currentChar = s.charAt( i );
			Directions direction = defineDirection(currentChar);
			house = new HouseCoords( house.getX()+direction.getX(), house.getY()+direction.getY() );
			checkIfTheHouseHasAlreadyBeenVisited(house);
			if(!alreadyExists){
				historyOfVisitedHouses.add( house );
			}
		}
		return historyOfVisitedHouses.size();
	}

	private Directions defineDirection(char character){
		Directions direction;
		switch ( character ){
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
		}
		return direction;
	}

	private void checkIfTheHouseHasAlreadyBeenVisited(HouseCoords house){
		for ( HouseCoords currentHouses : historyOfVisitedHouses ) {
			if ( currentHouses.getX() == house.getX() && currentHouses.getY() == house.getY() ) {
				alreadyExists = true;
				break;
			}
		}
	}
}

