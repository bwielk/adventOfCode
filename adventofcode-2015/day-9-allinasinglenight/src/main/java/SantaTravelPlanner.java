import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SantaTravelPlanner {

	private String CLEAN_UP_REGEX = "(to |= |\n)";

	public List<String> shortestDistance( final List<Relation> relations ) {
		List<String> results = new ArrayList<>();

		for ( Relation relation : relations ) {
			StringBuilder sb = new StringBuilder();
			int totalDistance = 0;
			if ( relations.size() <= 1 ) {
				sb.append( relation.getFrom() )
						.append( " -> " )
						.append( relation.getTo() )
						.append( ": " )
						.append( relation.getDistance() );
				results.add( sb.toString() );
			} else {
				for ( int x = 0; x < relations.size(); x++ ) {
					Relation currentRelation = relations.get( x );
					totalDistance += currentRelation.getDistance();
					for ( int y = 0; y < relations.size(); y++ ) {
						if ( x != y ) {
							Relation relationToInspect = relations.get( y );
							if ( currentRelation.getTo().equals( relationToInspect.getFrom() ) ) {
								sb.append( currentRelation.getFrom() )
										.append( " -> " )
										.append( currentRelation.getTo() );
							} else {
								sb.append( currentRelation.getTo() )
										.append( " -> " )
										.append( currentRelation.getFrom() );
							}
							totalDistance += relationToInspect.getDistance();
							if ( relations.size() - 1 == x + 1 ) {
								sb.append( ": " ).append( totalDistance );
							}
							results.add( sb.toString() );
							totalDistance = 0;
						}
					}
				}
			}
		}
		return results;
	}

	public List<Relation> createRelationEntities( String input ) {
		List<Relation> results = new ArrayList<>();
		List<String> listOfDestinations = Arrays.asList( input.split( "\n" ) );
		for ( String s : listOfDestinations ) {
			String cleanedUpString = s.replaceAll( CLEAN_UP_REGEX, "" );
			String[] data = cleanedUpString.split( " " );
			results.add( new Relation( data[0], data[1], Integer.parseInt( data[2] ) ) );
		}
		return results;
	}
}
