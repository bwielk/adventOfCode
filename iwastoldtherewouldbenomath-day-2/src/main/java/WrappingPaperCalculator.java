import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WrappingPaperCalculator {

	public int calculateAreaForWrappingPaper( String inputSchema ) {

		int result = 0;

		List<String> contents = FileReaderHelper.readFileAsLinesOfStrings(
				WrappingPaperCalculator.class, inputSchema );

		for ( int i = 0; i < contents.size(); i++ ) {
			String currentLine = contents.get( i );
			result += parseEntryForAreaOfWrappingPaper( currentLine );
		}
		return result;
	}

	public int parseEntryForAreaOfWrappingPaper( String entry ) {
		int result = 0;
		String cleanedEntry =
				entry.replace( " ", "" )
				.trim()
				.toLowerCase();

		String inputWithoutInvalidCharacters = cleanedEntry.replaceAll( "[^0-9x]", "" );

		List<String> separated = Arrays.stream( inputWithoutInvalidCharacters.split( "x" ) )
				.filter( x -> !x.equals( "" ) )
				.collect( Collectors.toList() );

		List<Integer> measurements = separated.stream()
				.map( Integer::parseInt )
				.collect( Collectors.toList() );
		if ( measurements.size() == 3 && !measurements.contains( 0 ) ) {
			int sideA = measurements.get( 0 ) * measurements.get( 1 );
			int sideB = measurements.get( 1 ) * measurements.get( 2 );
			int sideC = measurements.get( 0 ) * measurements.get( 2 );

			List<Integer> collectionOfAreaValues = Arrays.asList( sideA, sideB, sideC );
			int slack = ListHelper.identifyTheSmallestValueInArrayListOfIntegers( collectionOfAreaValues );
			result += 2*(sideA+sideB+sideC)+slack;
		}
		return result;
	}

	public static void main( String[] args ) {
		WrappingPaperCalculator wrappingPaperCalculator = new WrappingPaperCalculator();
		int results = wrappingPaperCalculator.calculateAreaForWrappingPaper( "input.txt" );
		System.out.println(results);
	}
}
