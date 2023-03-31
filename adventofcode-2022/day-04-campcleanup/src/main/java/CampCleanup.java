import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CampCleanup {

	public int findContainedPairs(){
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings( CampCleanup.class, "input.txt");
		int numberOfContainedPairs = calculateContainedPairs( entries );
		System.out.println(numberOfContainedPairs);
		return numberOfContainedPairs;
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
		List<Integer> parsedEntries =  Arrays.stream( Arrays.stream(
				entry.replace( " ", "" ).strip().split( "," ) )
				.map( x -> x.split( "-" ) )
				.flatMap( Arrays::stream )
				.toArray() )
				.map( v -> Integer.parseInt( (String) v ) ).collect( Collectors.toList() );
		if(parsedEntries.size() != 4){
			throw new IllegalStateException("Only 4 values are accepted as input within the list");
		}else{
			if(parsedEntries.get( 0 ) > parsedEntries.get( 1 ) || parsedEntries.get( 2 ) > parsedEntries.get( 3 )){
				throw new IllegalStateException("The first digit of a pair cannot be greater than the second one");
			}else{
				return parsedEntries;
			}
		}
	}

	public boolean findIfPairsAreMutuallyContained( final List<Integer> input ) {
		return ( input.get( 0 ) >= input.get( 2 ) && input.get( 1 ) <= input.get(
				3 ) ) || ( input.get( 0 ) <= input.get( 2 ) && input.get( 1 ) >= input.get( 3 ) );
	}

	public boolean findIfPairsOverlapAtAll( final List<Integer> input ) {
		return ( input.get( 0 ) >= input.get( 2 ) && input.get( 1 ) <= input.get(
				3 ) ) || ( input.get( 0 ) <= input.get( 2 ) && input.get( 1 ) >= input.get( 3 ) );
	}
}
