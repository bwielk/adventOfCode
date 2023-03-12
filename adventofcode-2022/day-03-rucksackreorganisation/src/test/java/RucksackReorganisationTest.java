import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

	//TODO: implement the test and the logic of this part of the feature - lower priority due to the input
	@Test
	@Disabled
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

	@Test
	public void findsNothingIfNoCommonCharacterInAGroupOfThreeEntries() {
		List<String> input = List.of( "abcd", "efgh", "ijk" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 0 );
	}

	@Test
	public void returnsEmptyArrayIfCommonsCharactersOnlyInTwoEntriesOutOfAGroupOfThreeEntries() {
		List<String> input = List.of( "abcd", "efgh", "aijk" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 0 );
	}

	@Test
	public void findsSingleUppercaseCommonCharacterInAGroupOfThreeEntries() {
		List<String> input = List.of( "abcZd", "wpZ", "ZD" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 1 );
		assertThat( foundChars.get( 0 ) ).isEqualTo( 'Z' );
	}

	@Test
	public void findsSingleLowercaseCommonCharacterInAGroupOfThreeEntries() {
		List<String> input = List.of( "abcd", "wpa", "Za" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 1 );
		assertThat( foundChars.get( 0 ) ).isEqualTo( 'a' );
	}

	@Test
	public void findsMultipleLowercaseAndUppercaseCommonCharactersInAGroupOfThreeEntries() {
		List<String> input = List.of( "abcdZ", "wpa", "Za", "Zzz" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 2 );
		assertThat( foundChars ).containsExactlyInAnyOrder( 'a', 'Z' );
	}

	@Test
	public void duplicatedInstancesOfCharsAreIgnored() {
		List<String> input = List.of( "abcdZ", "wpa", "Za", "Zzz" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				input );
		assertThat( foundChars.size() ).isEqualTo( 2 );
		assertThat( foundChars ).containsExactlyInAnyOrder( 'a', 'Z' );
	}

	@Test
	public void noEntriesResultsInNoResults() {
		List<String> entries = Collections.emptyList();
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 0 );
	}

	@Test
	public void onlyOneEntryResultsInNoResultsSinceThereIsNothingToCompareAgainst() {
		List<String> entries = List.of( "ababa" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 0 );
	}

	@Test
	public void twoEntriesWithASingleCommonCharacterIdentified() {
		List<String> entries = List.of( "ababa", "zzzzza" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 1 );
		assertThat( foundChars.get( 0 ) ).isEqualTo( 'a' );
	}

	@Test
	public void twoEntriesWithMultipleCommonCharacters() {
		List<String> entries = List.of( "ababca", "zzzzzab" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 2 );
		assertThat( foundChars ).containsExactlyInAnyOrder( 'a', 'b' );
	}

	@Test
	public void fiveEntriesWithMultipleCommonCharactersMixtureOfUpperAndLowercases() {
		List<String> entries = List.of( "ababcaZ", "zzzzZzab", "Zavvb", "Zzba", "Zllllab" );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 3 );
		assertThat( foundChars ).containsExactlyInAnyOrder( 'a', 'b', 'Z' );
	}

	@Test
	public void threeEntriesWithOneOfThemBeingAnEmptyStringOrSpaceBreaksProcessing() {
		List<String> entries = List.of( "ababca", "zzzzzab", "", " " );
		List<Character> foundChars = rucksackReorganisation.findCommonCharacterInASeriesOfEntries(
				entries );
		assertThat( foundChars.size() ).isEqualTo( 0 );
	}

	@Test
	public void findCommonCharactersInAChunkOfEntries() {
		List<String> entries = List.of( "ababcaZ", "zzzzZzab", "Zavvb" );
		List<Character> expectedCharacters = List.of( 'Z', 'a', 'b' );
		int expectedPoints = rucksackReorganisation.calculatePointsForSharedCharacters(
				expectedCharacters );
		int totalPoints = rucksackReorganisation.checkCommonCharsInChunksOfUpToThreeGroups(
				entries );
		assertThat( totalPoints ).isEqualTo( expectedPoints );
	}

	@Test
	public void findCommonCharactersInEachSetOf3Groups() {
		List<String> entries = List.of( "aZ", "bZ", "zZ", "fA", "fc", "b f" );
		List<Character> expectedCharacters = List.of( 'Z', 'f' );
		int expectedPoints = rucksackReorganisation.calculatePointsForSharedCharacters(
				expectedCharacters );
		int totalPoints = rucksackReorganisation.checkCommonCharsInChunksOfUpToThreeGroups(
				entries );
		assertThat( totalPoints ).isEqualTo( expectedPoints );
	}

	@Test
	public void theSizeOfInputThatIsNotDividableBy3IsRejected() {
		List<String> entries = List.of( "aZ", "bZ", "zZ", "fA", "fc", "b f", "" );
		assertThrows( IllegalArgumentException.class,
				() -> rucksackReorganisation.checkCommonCharsInChunksOfUpToThreeGroups( entries ) );
	}

	@Test
	public void theSizeOfInputThatIsLessThan3IsRejected() {
		List<String> entries = List.of( "aZ", "bZ", "zZ", "fA", "fc" );
		assertThrows( IllegalArgumentException.class,
				() -> rucksackReorganisation.checkCommonCharsInChunksOfUpToThreeGroups( entries ) );
	}

	@Test
	public void identificationOfCommonCharsThroughGroupsOfEntriesAgainstRealFileWorks() {
		int totalPoints = rucksackReorganisation.runIdentificationOfCommonChars( "input.txt" );
		assertThat( totalPoints ).isEqualTo( 2581 );
	}
}