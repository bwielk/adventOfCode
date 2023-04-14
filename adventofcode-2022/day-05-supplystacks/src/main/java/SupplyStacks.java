import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SupplyStacks {

	private final List<Stack<Character>> stacks = new ArrayList<>();
	private final List<Command> commands = new ArrayList<>();
	private final String commandSyntaxRegex = "move \\d from \\d to \\d";

	public List<Command> getCommands() {
		return commands;
	}

	public List<Stack<Character>> getStacks() {
		return stacks;
	}

	public void run(String nameOfFile){
		runMovingCrates( generateStacksFromContent( nameOfFile ), generateCommandsFromContent( nameOfFile ) );
		System.out.println(stacks);
	}

	public List<Stack<Character>> runMovingCrates(List<Stack<Character>> stacks, List<Command> commands){
		for(Command c : commands){
			for(int pop=0; pop<c.getAmountOfCratesToMove(); pop++){
				stacks.get( c.getTargetStackIndex()-1 ).push( stacks.get( c.getEntryStackIndex()-1 ).pop());
			}
		}
		return stacks;
	}

	public List<Stack<Character>> generateStacksFromContent( String nameOfFile ) {
		List<String> readLines = FileReaderHelper.readFileAsLinesOfStrings( SupplyStacks.class,
				nameOfFile );
		return extractStacks( readLines );
	}

	public List<Command> generateCommandsFromContent( String nameOfFile ) {
		List<String> readLines = FileReaderHelper.readFileAsLinesOfStrings( SupplyStacks.class,
				nameOfFile );
		return extractCommands( readLines );
	}

	public List<Command> extractCommands( List<String> readLines ) {
		for ( String l : readLines ) {
			String sanitisedString =  l.toLowerCase().trim();
			if ( sanitisedString.matches( commandSyntaxRegex ) ) {
				List<String> splitFoundCommand = Arrays.asList( sanitisedString.split( " " ) );
				List<Integer> foundCommandArgs = new ArrayList<>();
				for ( String w : splitFoundCommand ) {
					if ( w.chars().allMatch( Character::isDigit ) && ( Integer.parseInt(
							w ) > 0 && Integer.parseInt( w ) < 10 ) ) {
						foundCommandArgs.add( Integer.parseInt( w ) );
					}
				}
				if ( foundCommandArgs.size() == 3 ) {
					commands.add( new Command( foundCommandArgs.get( 0 ), foundCommandArgs.get( 1 ),
							foundCommandArgs.get( 2 ) ) );
				}
			}
		}
		return commands;
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
