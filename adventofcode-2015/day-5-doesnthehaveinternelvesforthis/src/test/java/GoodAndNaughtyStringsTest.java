import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GoodAndNaughtyStringsTest {

	GoodAndNaughtyStrings goodAndNaughtyStrings;

	@BeforeEach
	public void before() {
		goodAndNaughtyStrings = new GoodAndNaughtyStrings();
	}

	private static Stream<Arguments> inputsWithMinimumThreeVowels() {
		return Stream.of( Arguments.of( "aaa", true ), Arguments.of( "AAAb", true ),
				Arguments.of( "abcdefghi", true ), Arguments.of( "eif", false ), //two vowels
				Arguments.of( "eff", false ), //one vowel
				Arguments.of( "fff", false ) );
	}

	private static Stream<Arguments> inputsWithDisallowedStrings() {
		return Stream.of( Arguments.of( "haegwjzuvuyypxyu", false ), //single instance
				Arguments.of( "hacdwjzuvuyypxyu", false ), //double instance
				Arguments.of( "Xy", false ),
				Arguments.of( "hawjzuvuyypu", true ),//without a disallowed string
				Arguments.of( "xay", true ), //characters of the disallowed string are present
				Arguments.of( "xyaxy", false ), //duplicate at the beginning and end
				Arguments.of( "xycdabaa", false ) //multiple disallowed strings
		);
	}

	private static Stream<Arguments> consecutiveAlphabeticalDuplicates() {
		return Stream.of( Arguments.of( "all", true ), //single instance
				Arguments.of( "accommodation paid in full", true ), //double instance in a sentence
				Arguments.of( "alL  ", true ), // caps
				Arguments.of( "Pond", false ), //without a consecutive duplication
				Arguments.of( "pa11y!!", false ), //contains non-alphabetical duplication
				Arguments.of( "XxoyY", true ), //a pair of consecutive duplication
				Arguments.of( "aa  aa", true ) // the same consecutive duplications
		);
	}

	private static Stream<Arguments> characterSurroundedByTwoTheSameCharacters() {
		return Stream.of( Arguments.of( "axa", true ), //simple pair
				Arguments.of( "aaa", true ), // all characters are the same
				Arguments.of( "aaaa  ", true ), // overlaps
				Arguments.of( "Paraguay", true ), // a full word
				Arguments.of( "exception", false ), // no pair
				Arguments.of( "Xoxo", true ), // a pair of caps and non-caps are accepted
				Arguments.of( "3o3i", false ), // digits are ignored
				Arguments.of( "!o!i", false ), // non-alphanumerics are ignored
				Arguments.of( "this is a dog", false ), // spaces as chars are ignored
				Arguments.of( "is i", false ), // a space is not stripped hence no pair between "s"
				Arguments.of( "paap", false ) // a pair separated with multiple chars
		);
	}

	private static Stream<Arguments> pairsOfCharactersAppearAtLeastTwiceInAWord() {
		return Stream.of( Arguments.of( "xyxy", true ), //simple pair
				Arguments.of( "aabcdefgaa", true ), // all characters are the same
				Arguments.of( "aaa  ", false ) // overlaps are wrong
		);
	}

	private static Stream<Arguments> inputsForOverallVerification() {
		return Stream.of( Arguments.of( "jchzalrnumimnmhp", false ),
				Arguments.of( "haegwjzuvuyypxyu", false ),
				Arguments.of( "dvszwmarrgswjxmb", false ) );
	}

	private static Stream<Arguments> inputsForOverallVerificationWithAdvancedRules() {
		return Stream.of( Arguments.of( "qjhvhtzxzqqjkmpb", true ),
				Arguments.of( "xxyxx", true ),
				Arguments.of( "ieodomkazucvgmuy", false ),
				Arguments.of( "uurcxstgmygtbstg", false ) );
	}

	private static Stream<Arguments> filesToParse() {
		return Stream.of( Arguments.of( "simpleEntry.txt", 1 ),
				Arguments.of( "input.txt", 236 ),
				Arguments.of( "emptyFile.txt", 0 ) );
	}

	private static Stream<Arguments> filesToParseAdvancedSearch() {
		return Stream.of( Arguments.of( "simpleEntryAdvanced.txt", 1 ),
				Arguments.of( "input.txt", 51 ),
				Arguments.of( "emptyFile.txt", 0 ) );
	}

	@ParameterizedTest
	@MethodSource("inputsWithMinimumThreeVowels")
	public void inputsWithAtLeastThreeVowelsAreRecognised( String input, boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.checkIfContainsMinimumThreeVowels( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("inputsWithDisallowedStrings")
	public void inputWithDisallowedStringsIsDetected( String input, boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.checkIfDoesNotContainsDisallowedStrings(
				input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("consecutiveAlphabeticalDuplicates")
	public void inputWithConsecutiveAlphabeticalDuplicatesIsDetected( String input,
			boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.checkIfContainsCharactersTwiceInARow( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}


	@ParameterizedTest
	@MethodSource("characterSurroundedByTwoTheSameCharacters")
	public void inputWithACharacterSurroundedByTwoTheSameCharactersIsCorrectlyDetected( String input,
			boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.checksIfContainsOneLetterIsSurroundedByTwoTheSameLetters( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("pairsOfCharactersAppearAtLeastTwiceInAWord")
	public void inputWithPairsOfCharactersAppearAtLeastTwiceInAWordIsCorrectlyDetected( String input,
			boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.checkIfPairsOfCharactersAppearAtLeastTwiceInAWord( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("inputsForOverallVerification")
	public void inputsGetVerifiedCorrectly( String input, boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.verifyEntryIsGoodWithBasicRules( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("inputsForOverallVerificationWithAdvancedRules")
	public void inputsGetVerifiedCorrectlyWithAdvancedSearch( String input, boolean expectedResult ) {
		boolean actualResult = goodAndNaughtyStrings.verifyEntryIsGoodWithAdvancedRules( input );
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@ParameterizedTest
	@MethodSource("filesToParse")
	public void inputsGetVerifiedCorrectly( String schemaName, int result ) {
		int numberOfFoundGoodEntries = goodAndNaughtyStrings.countGoodEntries( schemaName, false );
		assertThat( numberOfFoundGoodEntries ).isEqualTo( result );
	}

	@ParameterizedTest
	@MethodSource("filesToParseAdvancedSearch")
	public void inputsGetVerifiedCorrectlyWithAdvancedRules( String schemaName, int result ) {
		int numberOfFoundGoodEntries = goodAndNaughtyStrings.countGoodEntries( schemaName, true );
		assertThat( numberOfFoundGoodEntries ).isEqualTo( result );
	}
}
