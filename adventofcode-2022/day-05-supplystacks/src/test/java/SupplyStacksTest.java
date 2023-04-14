import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SupplyStacksTest {

	private SupplyStacks supplyStacks;

	@BeforeEach
	public void before() {
		supplyStacks = new SupplyStacks();
	}

	@Test
	public void extractStacks_happyPathWithMixtureOfStackHeights() {
		Stack<Character> stack1 = new Stack<>();
		stack1.push( 'S' );
		stack1.push( 'Q' );
		stack1.push( 'S' );
		stack1.push( 'Q' );

		Stack<Character> stack2 = new Stack<>();
		stack2.push( 'R' );
		stack2.push( 'Q' );
		stack2.push( 'R' );

		Stack<Character> stack3 = new Stack<>();
		stack3.push( 'Z' );
		stack3.push( 'B' );

		Stack<Character> stack4 = new Stack<>();
		stack4.push( 'V' );

		Stack<Character> stack5 = new Stack<>();

		Stack<Character> stack6 = new Stack<>();
		stack6.push( 'B' );
		stack6.push( 'S' );
		stack6.push( 'U' );

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent( "test.txt" );
		assertThat( actualStacks ).hasSize( 6 );

		assertThat( actualStacks.get( 0 ) ).containsExactlyElementsOf( stack1 );
		assertThat( actualStacks.get( 1 ) ).containsExactlyElementsOf( stack2 );
		assertThat( actualStacks.get( 2 ) ).containsExactlyElementsOf( stack3 );
		assertThat( actualStacks.get( 3 ) ).containsExactlyElementsOf( stack4 );
		assertThat( actualStacks.get( 4 ) ).containsExactlyElementsOf( stack5 );
		assertThat( actualStacks.get( 5 ) ).containsExactlyElementsOf( stack6 );
	}

	@Test
	public void extractStacks_emptyValuesInCratesAreIgnored() {
		Stack<Character> stack1 = new Stack<>();
		stack1.push( 'S' );
		stack1.push( 'S' );
		stack1.push( 'Q' );

		Stack<Character> stack2 = new Stack<>();
		stack2.push( 'R' );
		stack2.push( 'R' );

		Stack<Character> stack3 = new Stack<>();
		stack3.push( 'B' );

		Stack<Character> stack4 = new Stack<>();
		stack4.push( 'V' );

		Stack<Character> stack5 = new Stack<>();

		Stack<Character> stack6 = new Stack<>();
		stack6.push( 'B' );
		stack6.push( 'S' );

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"stackscontainemptyvaluesinbetweencrates.txt" );
		assertThat( actualStacks ).hasSize( 6 );

		assertThat( actualStacks.get( 0 ) ).containsExactlyElementsOf( stack1 );
		assertThat( actualStacks.get( 1 ) ).containsExactlyElementsOf( stack2 );
		assertThat( actualStacks.get( 2 ) ).containsExactlyElementsOf( stack3 );
		assertThat( actualStacks.get( 3 ) ).containsExactlyElementsOf( stack4 );
		assertThat( actualStacks.get( 4 ) ).containsExactlyElementsOf( stack5 );
		assertThat( actualStacks.get( 5 ) ).containsExactlyElementsOf( stack6 );
	}

	@Test
	public void extractStacks_stacksCanContainAnyCharacterType() {
		Stack<Character> stack1 = new Stack<>();
		stack1.push( 'S' );
		stack1.push( 'Q' );
		stack1.push( 'S' );
		stack1.push( '0' );

		Stack<Character> stack2 = new Stack<>();
		stack2.push( 'R' );
		stack2.push( 'Q' );
		stack2.push( '1' );

		Stack<Character> stack3 = new Stack<>();
		stack3.push( 'Z' );
		stack3.push( ',' );

		Stack<Character> stack4 = new Stack<>();
		stack4.push( ']' );

		Stack<Character> stack5 = new Stack<>();

		Stack<Character> stack6 = new Stack<>();
		stack6.push( '8' );
		stack6.push( 'S' );
		stack6.push( 'a' );

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"stackscontainingvariouschartypes.txt" );
		assertThat( actualStacks ).hasSize( 6 );

		assertThat( actualStacks.get( 0 ) ).containsExactlyElementsOf( stack1 );
		assertThat( actualStacks.get( 1 ) ).containsExactlyElementsOf( stack2 );
		assertThat( actualStacks.get( 2 ) ).containsExactlyElementsOf( stack3 );
		assertThat( actualStacks.get( 3 ) ).containsExactlyElementsOf( stack4 );
		assertThat( actualStacks.get( 4 ) ).containsExactlyElementsOf( stack5 );
		assertThat( actualStacks.get( 5 ) ).containsExactlyElementsOf( stack6 );
	}

	@Test
	public void extractStacks_stackNominatorCanContainDuplicatedSingleDigitNumbers() {
		Stack<Character> stack1 = new Stack<>();
		stack1.push( 'S' );
		stack1.push( 'Q' );
		stack1.push( 'S' );
		stack1.push( 'Q' );

		Stack<Character> stack2 = new Stack<>();
		stack2.push( 'R' );
		stack2.push( 'Q' );
		stack2.push( 'R' );

		Stack<Character> stack3 = new Stack<>();
		stack3.push( 'Z' );
		stack3.push( 'B' );

		Stack<Character> stack4 = new Stack<>();
		stack4.push( 'V' );

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"columnnominatorcontainsrandomsingledigitnumbers.txt" );
		assertThat( actualStacks ).hasSize( 4 );

		assertThat( actualStacks.get( 0 ) ).containsExactlyElementsOf( stack1 );
		assertThat( actualStacks.get( 1 ) ).containsExactlyElementsOf( stack2 );
		assertThat( actualStacks.get( 2 ) ).containsExactlyElementsOf( stack3 );
		assertThat( actualStacks.get( 3 ) ).containsExactlyElementsOf( stack4 );
	}

	@Test
	public void extractStacks_fileWithoutDefinedColumns() {
		Stack<Character> stack1 = new Stack<>();
		Stack<Character> stack2 = new Stack<>();
		Stack<Character> stack3 = new Stack<>();
		Stack<Character> stack4 = new Stack<>();

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"nocolumns.txt" );
		assertThat( actualStacks ).hasSize( 6 );

		assertThat( actualStacks.get( 0 ) ).containsExactlyElementsOf( stack1 );
		assertThat( actualStacks.get( 1 ) ).containsExactlyElementsOf( stack2 );
		assertThat( actualStacks.get( 2 ) ).containsExactlyElementsOf( stack3 );
		assertThat( actualStacks.get( 3 ) ).containsExactlyElementsOf( stack4 );
	}

	@Test
	public void extractStacks_missingColumnsNominator() {
		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"missingcolumnnominator.txt" );
		assertThat( actualStacks ).hasSize( 0 );
	}

	@Test
	public void extractStacks_columnsNominatorWithNonDigits() {
		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent(
				"columnnominatorwithnondigits.txt" );
		assertThat( actualStacks ).hasSize( 0 );
	}

	@Test
	public void extractCommands_commandsCanBeExtractedFromFileAmongstOtherContent() {
		Command expectedCommand1 = new Command( 3, 8, 2 );
		Command expectedCommand2 = new Command( 2, 2, 1 );
		List<Command> actualCommands = supplyStacks.generateCommandsFromContent( "test.txt" );
		assertThat( actualCommands ).hasSize( 2 );
		Command actualCommand1 = actualCommands.get( 0 );
		Command actualCommand2 = actualCommands.get( 1 );
		assertThat( actualCommand1.getAmountOfCratesToMove() ).isEqualTo(
				expectedCommand1.getAmountOfCratesToMove() );
		assertThat( actualCommand1.getEntryStackIndex() ).isEqualTo(
				expectedCommand1.getEntryStackIndex() );
		assertThat( actualCommand1.getTargetStackIndex() ).isEqualTo(
				expectedCommand1.getTargetStackIndex() );
		assertThat( actualCommand2.getAmountOfCratesToMove() ).isEqualTo(
				expectedCommand2.getAmountOfCratesToMove() );
		assertThat( actualCommand2.getEntryStackIndex() ).isEqualTo(
				expectedCommand2.getEntryStackIndex() );
		assertThat( actualCommand2.getTargetStackIndex() ).isEqualTo(
				expectedCommand2.getTargetStackIndex() );
	}

	private static Stream<Arguments> commandIsNotParsedIfOneOfTheValuesIsNotIn1To9Range() {
		return Stream.of( Arguments.of( List.of( "move 0 from 2 to 1" ) ),
				Arguments.of( List.of( "move 2 from 2 to 10" ) ) );
	}

	@ParameterizedTest
	@MethodSource("commandIsNotParsedIfOneOfTheValuesIsNotIn1To9Range")
	public void extractCommands_commandIsNotParsedIfOneOfTheValuesIsNotIn1To9Range(
			List<String> input ) {
		List<Command> actualCommands = supplyStacks.extractCommands( input );
		assertThat( actualCommands ).hasSize( 0 );
	}

	private static Stream<Arguments> caseInsensitiveText() {
		return Stream.of( Arguments.of( List.of( "Move 1 from 2 to 1" ), new Command( 1, 2, 1 ) ),
				Arguments.of( List.of( "MOVE 2 FROM 2 to 1" ), new Command( 2, 2,1 ) ) );
	}

	@ParameterizedTest
	@MethodSource("caseInsensitiveText")
	public void extractCommands_caseInsensitiveTextIsParsed(
			List<String> input, Command expectedCommand ) {
		List<Command> actualCommands = supplyStacks.extractCommands( input );
		assertThat( actualCommands ).hasSize( 1 );
		Command actualCommand = actualCommands.get( 0 );
		assertThat( actualCommand.getAmountOfCratesToMove() ).isEqualTo(
				expectedCommand.getAmountOfCratesToMove() );
		assertThat( actualCommand.getEntryStackIndex() ).isEqualTo(
				expectedCommand.getEntryStackIndex() );
		assertThat( actualCommand.getTargetStackIndex() ).isEqualTo(
				expectedCommand.getTargetStackIndex() );
	}

	@Test
	public void extractCommands_wronglyWordedCommandIsIgnored() {
		List<Command> actualCommands = supplyStacks.extractCommands( List.of("Moved 2 from 2 to 1") );
		assertThat( actualCommands ).hasSize( 0 );
	}

	@Test
	public void extractCommands_wronglyWordedCommandIsIgnoredButCorrectOneIsParsedProperly() {
		Command expectedCommand = new Command( 3,3,3 );
		List<Command> actualCommands = supplyStacks.extractCommands( List.of("Moved 2 from 2 to 1", "Move 3 from 3 to 3" ) );
		assertThat( actualCommands ).hasSize( 1 );
		Command actualCommand = actualCommands.get( 0 );
		assertThat( actualCommand.getAmountOfCratesToMove() ).isEqualTo(
				expectedCommand.getAmountOfCratesToMove() );
		assertThat( actualCommand.getEntryStackIndex() ).isEqualTo(
				expectedCommand.getEntryStackIndex() );
		assertThat( actualCommand.getTargetStackIndex() ).isEqualTo(
				expectedCommand.getTargetStackIndex() );
	}

	@Test
	public void extractCommands_trailingAndLeadingSpacesInCommandAreIngested() {
		Command expectedCommand = new Command( 3,3,3 );
		List<Command> actualCommands = supplyStacks.extractCommands( List.of("   Move 3 from 3 to 3   " ) );
		assertThat( actualCommands ).hasSize( 1 );
		Command actualCommand = actualCommands.get( 0 );
		assertThat( actualCommand.getAmountOfCratesToMove() ).isEqualTo(
				expectedCommand.getAmountOfCratesToMove() );
		assertThat( actualCommand.getEntryStackIndex() ).isEqualTo(
				expectedCommand.getEntryStackIndex() );
		assertThat( actualCommand.getTargetStackIndex() ).isEqualTo(
				expectedCommand.getTargetStackIndex() );
	}
}