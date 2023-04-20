import java.util.ArrayList;
import java.util.List;

public class ListHelper {

	public static int findSmallestValueInListOfIntegers( List<Integer> integerList ) {
		int result = integerList.get( 0 );
		for ( int i = 0; i < integerList.size(); i++ ) {
			if ( integerList.get( i ) < result ) {
				result = integerList.get( i );
			}
		}
		return result;
	}

	public static int findLongestStringInList( List<String> input ) {
		int result = input.get( 0).length();
		for ( String s : input ) {
			if ( s.length() > result ) {
				result = s.length();
			}
		}
		return result;
	}

	public static boolean checkIfAllIntegersInAListAreGreaterThanZero( List<Integer> integerList ) {
		for ( Integer integer : integerList ) {
			if ( integer < 0 ) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkIfIntIsGreaterThanValuesInIntList( List<Integer> integerList, int value ) {
		for ( Integer integer : integerList ) {
			if ( integer >= value ) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkIfIntIsGreaterThanAtLeastOneIntInList( List<Integer> integerList, int value ) {
		List<Integer> results = new ArrayList<>();
		for ( Integer integer : integerList ) {
			if ( integer < value ) {
				results.add( value );
			}
		}
		return results.size()>1;
	}
}
