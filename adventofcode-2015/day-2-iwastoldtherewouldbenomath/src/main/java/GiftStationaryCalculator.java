import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GiftStationaryCalculator {

	/***
	 *
	 * @param inputSchema - path to the file with inputs
	 * @return - area of wrapping paper
	 */

	public int calculateAreaForWrappingPaper( String inputSchema ) {
		int result = 0;

		List<String> contents = FileReaderHelper.readFileAsLinesOfStrings(
				GiftStationaryCalculator.class, inputSchema );

		for ( int i = 0; i < contents.size(); i++ ) {
			String currentLine = contents.get( i );
			result += parseEntryForAreaOfWrappingPaper( currentLine );
		}
		return result;
	}

	/***
	 *
	 * @param inputSchema - path to the file with inputs
	 * @return - length of ribbon used
	 */

	public int calculateRibbonLengthForBoxes( String inputSchema ) {
		int result = 0;

		List<String> contents = FileReaderHelper.readFileAsLinesOfStrings(
				GiftStationaryCalculator.class, inputSchema );

		for ( int i = 0; i < contents.size(); i++ ) {
			String currentLine = contents.get( i );
			result += parseEntryForLengthOfRibbon( currentLine );
		}

		return result;
	}

	/***
	 *
	 * @param entry - entry with measurements data
	 * @return - calcualted length of ribbon to be used
	 */

	public int parseEntryForLengthOfRibbon( String entry ) {
		int results = 0;

		List<Integer> measurements = parseEntries( entry );

		if ( measurements.size() == 3 && !measurements.contains( 0 ) ) {
			Collections.sort( measurements );//ascending order
			results += 2 * measurements.get( 0 ) + 2 * measurements.get( 1 );
			results += measurements.get( 0 ) * measurements.get( 1 ) * measurements.get( 2 );
		}

		return results;
	}

	/***
	 *
	 * @param entry - entry with measurements data
	 * @return - area of wrapping paper
	 */

	public int parseEntryForAreaOfWrappingPaper( String entry ) {
		int result = 0;

		List<Integer> measurements = parseEntries( entry );

		if ( measurements.size() == 3 && !measurements.contains( 0 ) ) {
			int sideA = measurements.get( 0 ) * measurements.get( 1 );
			int sideB = measurements.get( 1 ) * measurements.get( 2 );
			int sideC = measurements.get( 0 ) * measurements.get( 2 );

			List<Integer> collectionOfAreaValues = Arrays.asList( sideA, sideB, sideC );
			int slack = ListHelper.findSmallestValueInListOfIntegers(
					collectionOfAreaValues );
			result += 2 * ( sideA + sideB + sideC ) + slack;
		}
		return result;
	}

	/***
	 *
	 * @param entry - raw entry to be parsed
	 * @return - list of measurements data
	 */

	public List<Integer> parseEntries( String entry ) {
		String cleanedEntry = entry.replace( " ", "" ).trim().toLowerCase();

		String inputWithoutInvalidCharacters = cleanedEntry.replaceAll( "[^0-9x]", "" );

		List<String> separated = Arrays.stream( inputWithoutInvalidCharacters.split( "x" ) )
				.filter( x -> !x.equals( "" ) )
				.collect( Collectors.toList() );

		return separated.stream().map( Integer::parseInt ).collect( Collectors.toList() );
	}
}
