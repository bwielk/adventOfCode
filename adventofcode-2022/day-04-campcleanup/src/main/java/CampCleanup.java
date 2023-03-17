import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanup {

	public int findContainedPairs(){
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings( CampCleanup.class, "input.txt");
		return calculateContainedPairs( entries );
	}

	public int calculateContainedPairs(List<String> input){
		int countOfContainedPairs = 0;
		for(String entry : input){
			if(findIfPairsAreMutuallyContained( parseEntry( entry ) )){
				countOfContainedPairs++;
			}
		}
		return countOfContainedPairs;
	}

	public List<Integer> parseEntry(String entry){
		return Arrays.stream( Arrays.stream(
				entry.replace( " ", "" ).strip().split( "," ) )
				.map( x -> x.split( "-" ) )
				.flatMap( Arrays::stream )
				.toArray() )
				.map( v -> Integer.parseInt( (String) v ) ).collect( Collectors.toList() );
	}

	public boolean findIfPairsAreMutuallyContained( final List<Integer> input ) {
		return ( input.get( 0 ) >= input.get( 2 ) && input.get( 1 ) <= input.get(
				3 ) ) || ( input.get( 0 ) <= input.get( 2 ) && input.get( 1 ) >= input.get( 3 ) );
	}
}
