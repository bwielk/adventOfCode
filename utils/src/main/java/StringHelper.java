import java.util.Arrays;
import java.util.stream.Collectors;

public class StringHelper {

	public static String removeDuplicateChars(String input){
		return Arrays.stream( input.split( "" ) )
				.distinct()
				.collect( Collectors.joining() );
	}
}
