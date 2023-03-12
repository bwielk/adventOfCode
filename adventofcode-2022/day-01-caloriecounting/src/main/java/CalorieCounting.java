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

	private List<Integer> createListOfElfCalorieIntake( String fileName ) {
		List<String> inputs = FileReaderHelper.readFileAsLinesOfStrings( CalorieCounting.class,
				fileName );
		List<Integer> records = new ArrayList<>();
		Integer caloriesSumRecorder = 0;
		for ( String line : inputs ) {
			String clearedLine = line.strip();
			if ( clearedLine.isEmpty() ) {
				records.add( caloriesSumRecorder );
				caloriesSumRecorder = 0;
			} else {
				caloriesSumRecorder += Integer.parseInt( line );
			}
		}
		return records;
	}
}
