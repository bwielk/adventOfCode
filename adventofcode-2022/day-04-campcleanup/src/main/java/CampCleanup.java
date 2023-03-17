import java.util.List;

public class CampCleanup {

	public boolean findIfPairsAreMutuallyContained( final List<Integer> input ) {
		return (input.get( 0 ) >= input.get( 2 ) && input.get( 1 ) <= input.get( 3 )) ||
				(input.get( 0 ) <= input.get( 2 ) && input.get( 1 ) >= input.get( 3 ));
	}
}
