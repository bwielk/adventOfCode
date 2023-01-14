import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalorieCounting {

	public Integer findHighestCalorieIntake( String fileName ) {
		List<Integer> records = createListOfElfCalorieIntake( fileName );
		Collections.sort( records );
		return records.get( records.size() - 1 );
	}

	public Integer findHighestCalorieIntakeForTopThreeElves( String fileName ) {
		List<Integer> records = createListOfElfCalorieIntake( fileName );
		Collections.sort( records );
		Integer elf1 = records.get( records.size() - 1 );
		Integer elf2 = records.get( records.size() - 2 );
		Integer elf3 = records.get( records.size() - 3 );
		return elf1 + elf2 + elf3;
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
