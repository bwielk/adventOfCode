import java.util.Arrays;
import java.util.stream.Collectors;

public class StringHelper {

	public static String removeDuplicatedChars(String input){
		return Arrays.stream( input.split( "" ) )
				.distinct()
				.collect( Collectors.joining() );
	}
}
