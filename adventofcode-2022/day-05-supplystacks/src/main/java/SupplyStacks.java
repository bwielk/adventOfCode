import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class SupplyStacks {

	private final String commandSyntaxRegex = "move [0-9]+ from \\d to \\d";

	public String runMovingSingleCratesWithReturningTopElementsOfStacks( String nameOfFile ) {
		List<Stack<Character>> result = runMovingCratesOneByOne(
				generateStacksFromContent( nameOfFile ),
				generateCommandsFromContent( nameOfFile ) );
		return printLastElementsOfStacks( result );
	}

	public String runMovingBatchesWithReturningTopElementsOfStacks( String nameOfFile ) {
		List<Stack<Character>> result = runMovingCratesInBatches(
				generateStacksFromContent( nameOfFile ),
				generateCommandsFromContent( nameOfFile ) );
		return printLastElementsOfStacks( result );
	}

	public List<Stack<Character>> runMovingCratesOneByOne( List<Stack<Character>> stacks,
			List<Command> commands ) {
		for ( Command c : commands ) {
			for ( int pop = 0; pop < c.getAmountOfCratesToMove(); pop++ ) {
				try {
					stacks.get( c.getTargetStackIndex() - 1 )
							.push( stacks.get( c.getEntryStackIndex() - 1 ).pop() );
				} catch ( EmptyStackException e ) {
					e.printStackTrace();
				}
			}
		}
		return stacks;
	}

	public List<Stack<Character>> runMovingCratesInBatches( List<Stack<Character>> stacks,
			List<Command> commands ) {
		for ( Command c : commands ) {
			List<Character> tempList = new ArrayList<>();
			for ( int pop = 0; pop < c.getAmountOfCratesToMove(); pop++ ) {

				try {
					tempList.add( stacks.get( c.getEntryStackIndex() - 1 ).pop() );
				} catch ( EmptyStackException e ) {
					e.printStackTrace();
				}
			}
			for ( int ch = tempList.size() - 1; ch >= 0; ch-- ) {
				stacks.get( c.getTargetStackIndex() - 1 ).push( tempList.get( ch ) );
			}
		}
		return stacks;
	}

	public String printLastElementsOfStacks( List<Stack<Character>> stacks ) {
		StringBuilder sb = new StringBuilder();
		for ( Stack<Character> s : stacks ) {
			if ( !s.isEmpty() ) {
				sb.append( s.lastElement() );
			}
		}
		String result = String.format( "Top elements of stacks from left to right:\n %s", sb );
		System.out.println( result );
		return result;
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
		List<Command> commands = new ArrayList<>();
		for ( String l : readLines ) {
			String sanitisedString = l.toLowerCase().trim();
			if ( sanitisedString.matches( commandSyntaxRegex ) ) {
				List<String> splitFoundCommand = Arrays.asList( sanitisedString.split( " " ) );
				List<Integer> foundCommandArgs = new ArrayList<>();
				for ( String w : splitFoundCommand ) {
					if ( w.chars().allMatch( Character::isDigit ) ) {
						foundCommandArgs.add( Integer.parseInt( w ) );
					}
				}
				// entry target cannot be equal to end target
				if ( foundCommandArgs.size() == 3 && ( !foundCommandArgs.get( 1 )
						.equals( foundCommandArgs.get( 2 ) ) && ( foundCommandArgs.get(
						1 ) > 0 && foundCommandArgs.get( 1 ) < 10 ) && ( foundCommandArgs.get(
						2 ) > 0 && foundCommandArgs.get( 2 ) < 10 ) ) ) {
					commands.add( new Command( foundCommandArgs.get( 0 ), foundCommandArgs.get( 1 ),
							foundCommandArgs.get( 2 ) ) );
				}
			}
		}
		return commands;
	}

	public List<Stack<Character>> extractStacks( List<String> readLines ) {
		List<Stack<Character>> stacks = new ArrayList<>();
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
