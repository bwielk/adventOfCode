import java.util.ArrayList;
import java.util.List;

public class RucksackReorganisation {

	public int runReorganisation(String file){
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings( RucksackReorganisation.class,
				file );
		int total = 0;
		for(String e : entries){
			total+= calculatePointsForSharedCharacters( findSharedElements( defineTwoCompartments( e ) ) );
		}
		return total;
	}

	public List<String> defineTwoCompartments(String input){
		input = input.replace( " ", "" );
		List<String> result = new ArrayList<>();
		if(input.length()%2 != 0 || input.isEmpty()){
			return result;
		}else{
			int splitIndex = input.length()/2;
			result.add( input.substring( 0, splitIndex ) );
			result.add( input.substring( splitIndex ) );
		}
		return result;
	}

	public List<Character> findSharedElements( final List<String> input ) {
		List<Character> result = new ArrayList<>();
		for(Character c1 : input.get( 0 ).toCharArray()){
			for(Character c2 : input.get( 1 ).toCharArray()){
				if(c1 == c2 ){
					if(!result.contains( c1 )){
						result.add( c1 );
					}
				}
			}
		}
		return result;
	}

	public int calculatePointsForSharedCharacters(List<Character> listOfCharacters){
		int total = 0;
		for(Character c : listOfCharacters){
			if(Character.isLowerCase( c )){
				//based on the ASCII character values - a == 97 and z == 122
				total += c - 96;
			}else{
				//based on the ASCII character values - A == 65 and Z == 90
				total += c - 38;
			}
		}
		return total;
	}
}
