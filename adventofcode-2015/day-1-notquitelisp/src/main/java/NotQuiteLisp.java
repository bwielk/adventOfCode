import java.util.ArrayList;
import java.util.List;

public class NotQuiteLisp {

	/***
	 *
	 * @param schemaName - name of the file e.g. "input.txt"
	 * @return floor number
	 */

	public int calculateFinalFloor(String schemaName){

		int level = 0;

		List<String> content = readFile(schemaName);
		String input = content.get( 0 );

		for(int i=0; i<input.length(); i++){
			char currentChar = input.charAt( i );
			if(currentChar == '('){
				level++;
			}else if(currentChar == ')') {
				level--;
			}
		}
		return level;
	}

	/***
	 *
	 * @param schemaName - name of the file e.g. "input.txt"
	 * @return records about how many times, when and at what level the basement was reached
	 */

	public List<BasementRecord> investigateReachingBasement( String schemaName ) {

		int level = 0;

		List<BasementRecord> basementRecords = new ArrayList<>();

		List<String> content = readFile(schemaName);
		String input = content.get( 0 );

		for(int i=0; i<input.length(); i++){
			char currentChar = input.charAt( i );
			if(currentChar == '('){
				level++;
			}else if(currentChar == ')') {
				level--;
			}
			if(level < 0){
				basementRecords.add( new BasementRecord( i, level ) );
			}
		}

		return basementRecords;
	}

	private List<String> readFile(String schemaName){
		List<String> content = FileReaderHelper.readFileAsLinesOfStrings( NotQuiteLisp.class,
				schemaName );

		if( content.size() != 1 ){
			throw new IllegalArgumentException(ErrorContent.INVALID_ROW_NUMBER);
		}

		return content;
	}
}
