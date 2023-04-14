import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SupplyStacks {

	private final List<Stack<Character>> stacks = new ArrayList<>();

	public List<Stack<Character>> generateStacksFromContent( String nameOfFile ) {
		List<String> readLines = FileReaderHelper.readFileAsLinesOfStrings( SupplyStacks.class,
				nameOfFile );
		return extractStacks( readLines );
	}

	public List<Stack<Character>> extractStacks( List<String> readLines ) {
		int beginningOfStackMap = -1;
		for ( String l : readLines ) {
			if ( l.matches( UsefulRegex.ONLY_NUMBERS_WITH_SPACES ) && !l.isEmpty() ) {
				beginningOfStackMap = readLines.indexOf( l );
				break;
			}
		}

		if ( beginningOfStackMap < 0 ) {
			return stacks;
		}

		for ( int n = 0; n < readLines.get( beginningOfStackMap ).length(); n++ ) {
			if ( Character.isDigit( readLines.get( beginningOfStackMap ).charAt( n ) ) ) {
				Stack<Character> stack = new Stack<>();
				for ( int i = beginningOfStackMap - 1; i > -1; i-- ) {
					String currentLine = readLines.get( i );
					if ( currentLine.length() >= n && currentLine.charAt( n ) != ' ' ) {
						stack.push( currentLine.charAt( n ) );
					}
				}
				stacks.add( stack );
			}
		}
		return stacks;
	}
}
