import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent( "stackscontainemptyvaluesinbetweencrates.txt" );
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

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent( "stackscontainingvariouschartypes.txt" );
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

		List<Stack<Character>> actualStacks = supplyStacks.generateStacksFromContent( "columnnominatorcontainsrandomsingledigitnumbers.txt" );
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
}