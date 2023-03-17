import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalorieCounting {

	public Integer runFindHighestCalorieIntake( String fileName ) {
		List<String> inputs = FileReaderHelper.readFileAsLinesOfStrings( CalorieCounting.class,
				fileName );
		List<Integer> intake = createListOfElfCalorieIntake( inputs );
		return findHighestCalorieIntake( intake );
	}

	public Integer runFindHighestCalorieIntakeForTopThreeElves( String fileName ) {
		List<String> inputs = FileReaderHelper.readFileAsLinesOfStrings( CalorieCounting.class,
				fileName );
		List<Integer> intake = createListOfElfCalorieIntake( inputs );
		return findSumOfHighestCalorieIntakeForTopThreeElves( intake );
	}

	public Integer findSumOfHighestCalorieIntakeForTopThreeElves( List<Integer> intake ) {
		Collections.sort( intake );
		List<Integer> firstThreeHighestIntakes;
		if ( intake.size() >= 3 ) {
			firstThreeHighestIntakes = intake.subList( intake.size()-3, intake.size() );
		} else {
			firstThreeHighestIntakes = intake.subList( 0, intake.size() );
		}
		List<Integer> allHighestIntakes = new ArrayList<>();
		for(Integer topValue : firstThreeHighestIntakes){
			for ( Integer integer : intake ) {
				if ( topValue.equals( integer ) ) {
					allHighestIntakes.add( integer );
				}
			}
		}
		return allHighestIntakes.stream().mapToInt( Integer::intValue ).sum();
	}

	public Integer findHighestCalorieIntake( List<Integer> intake ) {
		Collections.sort( intake );
		return intake.get( intake.size() - 1 );
	}

	public List<Integer> createListOfElfCalorieIntake( List<String> inputs ) {
		List<Integer> records = new ArrayList<>();
		List<List<String>> groupedEntries = createGroups( inputs );
		int tempTotal = 0;
		for(List<String> entry : groupedEntries){
			for(int i=0; i<entry.size(); i++){
				String sanitisedEntry = entry.get( i ).replace( " ", "" );
				if( !sanitisedEntry.equals( "" )){
					tempTotal+= Integer.parseInt( sanitisedEntry );
				}
			}
			records.add( tempTotal );
			tempTotal=0;
		}
		return records;
	}

	private List<List<String>> createGroups(List<String> input){
		List<List<String>> sublists = new ArrayList<>();
		int startIndex = 0;
		int endIndex = 0;
		for(int i=0; i<input.size(); i++){
			if(input.get( i ).equals( "" ) || i == input.size()-1){
				if(i==input.size()-1){
					endIndex = input.size();
				}else{
					endIndex = i;
				}
				sublists.add( input.subList( startIndex, endIndex ));
				startIndex = endIndex+1;
			}
		}
		return sublists;
	}
}
