import java.util.ArrayList;
import java.util.List;

public class TuningTrouble {

	public static final int INTERVAL = 4;

	public List<Integer> findSignalIndexFromFile( String fileName ) {
		List<Integer> results = new ArrayList<>();
		List<String> input = FileReaderHelper.readFileAsLinesOfStrings( TuningTrouble.class,
				fileName );
		for ( String in : input ) {
			results.add( findSignalIndex( in ) );
		}
		return results;
	}

	public int findSignalIndex( final String input ) {
		int indexFound = -1;
		String steriliseInput = input.trim().replace( " ", "" );
		if ( steriliseInput.length() >= INTERVAL ) {
			for ( int i = 0; i < steriliseInput.length(); i++ ) {
				if ( i + INTERVAL <= steriliseInput.length() ) {
					if ( StringHelper.isStringMadeOfUniqueChars(
							steriliseInput.substring( i, i + INTERVAL ) ) ) {
						indexFound = i + INTERVAL;
						break;
					}
				}
			}
		}
		return indexFound;
	}
}
