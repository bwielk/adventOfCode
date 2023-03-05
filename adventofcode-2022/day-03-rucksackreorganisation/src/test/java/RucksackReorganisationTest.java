import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RucksackReorganisationTest {

	private RucksackReorganisation rucksackReorganisation;
	public static final String TEST_INPUT = "vJrwpWtwJgWrhcsFMMfFFhFp";
	public static final String TEST_INPUT_ODD_LENGTH = "vJrwpWtwJgWrhcsFMMfFFhFpj";

	@BeforeEach
	public void before() {
		rucksackReorganisation = new RucksackReorganisation();
	}

	@Test
	public void rucksackInputCanBeSplitInTwoCompartmentsIfInputIsEven() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( TEST_INPUT );
		assertThat( compartments.get( 0 ) ).isEqualTo( "vJrwpWtwJgWr" );
		assertThat( compartments.get( 1 ) ).isEqualTo( "hcsFMMfFFhFp" );
	}

	@Test
	public void rucksackInputCannotBeSplitInTwoCompartmentsIfInputIsOdd() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments(
				TEST_INPUT_ODD_LENGTH );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void rucksackInputOfEmptyStringReturnsEmptyArrayOfCompartments() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "" );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void rucksackInputOfJustSpacesIsTreatedAsEmptyString() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "      " );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void spacesAreStrippedAndProcessingContinuesIfLengthIsEven() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "a d S B  s  a" );
		assertThat( compartments.get( 0 ) ).isEqualTo( "adS" );
		assertThat( compartments.get( 1 ) ).isEqualTo( "Bsa" );
	}

	@Test
	public void spacesAreStrippedAndEntryIsIgnoredIfLengthIsOdd() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "a d S B    a" );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void trailingAndLeadingSpacesAreStrippedAndProcessingContinuesIfLegthIsEven() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "  adSBsa  " );
		assertThat( compartments.get( 0 ) ).isEqualTo( "adS" );
		assertThat( compartments.get( 1 ) ).isEqualTo( "Bsa" );
	}

	@Test
	public void trailingAndLeadingSpacesAreStrippedAndEntryIsIgnoredIfLegthIsOdd() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "  adSBa  " );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void presenceOfNonAlphabeticalCharactersMakesEntryIgnored() {
		List<String> compartments = rucksackReorganisation.defineTwoCompartments( "123abc,ł" );
		assertThat( compartments ).isEmpty();
	}

	@Test
	public void commonItemsCanBeIdentifiedOnlyWhenCasesMatch() {
		List<String> input = List.of( "abcd", "aBCD" );
		List<Character> foundSharedChars = rucksackReorganisation.findSharedElements( input );
		assertThat( foundSharedChars.size() ).isEqualTo( 1 );
		assertThat( foundSharedChars.get( 0 ).charValue() ).isEqualTo( 'a' );
	}

	@Test
	public void commonItemsCanBeIdentifiedJustOnce() {
		List<String> input = List.of( "abcd", "aaBCD" );
		List<Character> foundSharedChars = rucksackReorganisation.findSharedElements( input );
		assertThat( foundSharedChars.size() ).isEqualTo( 1 );
		assertThat( foundSharedChars.get( 0 ).charValue() ).isEqualTo( 'a' );
	}

	@Test
	public void theSameCharactersButOfDifferentCaseAreNotIdentifiedAsMatches() {
		List<String> input = List.of( "abcd", "ABCD" );
		List<Character> foundSharedChars = rucksackReorganisation.findSharedElements( input );
		assertThat( foundSharedChars ).isEmpty();
	}

	@Test
	public void pointsAreDefinedForLowerCaseAcceptableCharacters() {
		List<String> input = List.of( "pvts", "ptvsa" );
		//16 + 22 + 20 + 19 = 77
		List<Character> foundSharedChars = rucksackReorganisation.findSharedElements( input );
		int calculateTotal = rucksackReorganisation.calculatePointsForSharedCharacters(
				foundSharedChars );
		assertThat( calculateTotal ).isEqualTo( 77 );
	}

	@Test
	public void pointsAreDefinedForUpperCaseAcceptableCharacters() {
		List<String> input = List.of( "LPa", "LcP" );
		//38 + 42 = 80
		List<Character> foundSharedChars = rucksackReorganisation.findSharedElements( input );
		int calculateTotal = rucksackReorganisation.calculatePointsForSharedCharacters(
				foundSharedChars );
		assertThat( calculateTotal ).isEqualTo( 80 );
	}

	@Test
	public void reorganisationAgainstRealFileWorks() {
		int totalPoints = rucksackReorganisation.runReorganisation( "input.txt" );
		assertThat( totalPoints ).isEqualTo( 7850 );
	}
}