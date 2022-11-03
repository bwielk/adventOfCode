import java.util.List;

public class ListHelper {

	public static int identifyTheSmallestValueInArrayListOfIntegers( List<Integer> integerList){
		int result = integerList.get( 0 );
		for(int i=0; i<integerList.size(); i++){
			if(integerList.get( i ) < result){
				result = integerList.get( i );
			}
		}
		return result;
	}
}
