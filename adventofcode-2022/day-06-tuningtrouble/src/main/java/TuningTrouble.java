import java.util.ArrayList;
import java.util.List;

public class TuningTrouble {

	public List<Integer> findSignalIndexFromFile( String fileName, int markerInterval ) {
		List<Integer> results = new ArrayList<>();
		List<String> input = FileReaderHelper.readFileAsLinesOfStrings( TuningTrouble.class,
				fileName );
		for ( String in : input ) {
			results.add( findSignalIndex( in, markerInterval ) );
		}
		return results;
	}

	public int findSignalIndex( final String input, int markerInterval ) {
		int indexFound = -1;
		String steriliseInput = input.trim().replace( " ", "" );
		if ( steriliseInput.length() >= markerInterval ) {
			for ( int i = 0; i < steriliseInput.length(); i++ ) {
				if ( i + markerInterval <= steriliseInput.length() ) {
					if ( StringHelper.isStringMadeOfUniqueChars(
							steriliseInput.substring( i, i + markerInterval ) ) ) {
						indexFound = i + markerInterval;
						break;
					}
				}
			}
		}
		return indexFound;
	}
}
