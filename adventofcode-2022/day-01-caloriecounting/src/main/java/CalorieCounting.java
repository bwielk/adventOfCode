import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalorieCounting {

	public Integer runFindHighestCalorieIntake( String fileName ) {
		List<Integer> intake = createListOfElfCalorieIntake( fileName );
		return findHighestCalorieIntake( intake );
	}

	public Integer runFindHighestCalorieIntakeForTopThreeElves( String fileName ) {
		List<Integer> intake = createListOfElfCalorieIntake( fileName );
		return findHighestCalorieIntakeForTopThreeElves( intake );
	}

	public Integer findHighestCalorieIntakeForTopThreeElves( List<Integer> intake){
		Collections.sort( intake );
		List<Integer> highestIntake;
		if( intake.size() >= 3 ){
			highestIntake = intake.subList( 0, 3 );
		}else{
			highestIntake = intake.subList( 0, intake.size() );
		}
		return highestIntake.stream().mapToInt(Integer::intValue).sum();
	}

	public Integer findHighestCalorieIntake( List<Integer> intake){
		Collections.sort( intake );
		return intake.get( intake.size() - 1 );
	}

	public List<Integer> createListOfElfCalorieIntake( String fileName ) {
		List<String> inputs = FileReaderHelper.readFileAsLinesOfStrings( CalorieCounting.class,
				fileName );
		List<Integer> records = new ArrayList<>();
		int caloriesSumRecorder = 0;
		for ( int i = 0; i<inputs.size(); i++) {
			String clearedLine = inputs.get( i ).strip();
			if ( clearedLine.isEmpty() && ( i<inputs.size() && !inputs.get( i+1 ).isEmpty() )) {
				records.add( caloriesSumRecorder );
				caloriesSumRecorder = 0;
			} else {
				if(!clearedLine.isEmpty()){
					caloriesSumRecorder += Integer.parseInt( clearedLine );
				}
			}
		}
		return records;
	}
}
