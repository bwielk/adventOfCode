import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WrappingPaperCalculator {

	public int calculateFromFile( String inputSchema ) {

		int result = 0;

		List<String> contents =  FileReaderHelper.readFileAsLinesOfStrings( WrappingPaperCalculator.class, inputSchema );

		for(int i=0; i<contents.size(); i++){
			List<Integer> measurements = Arrays.stream(contents.get( i ).split( "x" ))
					.map( Integer::parseInt ).collect( Collectors.toList());

			result += (3*measurements.get( 0 )*measurements.get( 1 )) +
					(2*measurements.get( 1 )*measurements.get( 2 )) +
					(2*measurements.get( 0 )*measurements.get( 2 ));
		}
		return result;
	}
}
