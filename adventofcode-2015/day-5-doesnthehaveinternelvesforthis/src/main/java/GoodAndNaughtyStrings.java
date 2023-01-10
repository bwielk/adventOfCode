import java.util.List;

public class GoodAndNaughtyStrings {

	public int countGoodEntries( String schemaName, boolean applyAdvancedSearch ) {
		int goodEntries = 0;
		List<String> entries = parseInputFile( schemaName );
		for ( String entry : entries ) {
			if ( applyAdvancedSearch ) {
				if ( verifyEntryIsGoodWithAdvancedRules( entry ) ) {
					goodEntries++;
				}
			} else {
				if ( verifyEntryIsGoodWithBasicRules( entry ) ) {
					goodEntries++;
				}
			}
		}
		return goodEntries;
	}

	public boolean verifyEntryIsGoodWithBasicRules( String input ) {

		return ( checkIfContainsCharactersTwiceInARow( input )
				&& checkIfDoesNotContainsDisallowedStrings( input )
				&& checkIfContainsMinimumThreeVowels( input ) );
	}

	public boolean verifyEntryIsGoodWithAdvancedRules( String input ) {

		return ( checksIfContainsOneLetterIsSurroundedByTwoTheSameLetters( input )
				&& checkIfPairsOfCharactersAppearAtLeastTwiceInAWord( input ) );
	}

	public boolean checkIfContainsMinimumThreeVowels( String input ) {

		return input.toLowerCase()
				.replaceAll( "[^aeoui]", "" )
				.length() >= 3; //has at least 3 vowels
	}

	public boolean checkIfDoesNotContainsDisallowedStrings( String input ) {
		return !input.toLowerCase().matches( "^.*((ab)|(cd)|(pq)|(xy)).*$" );
	}

	public boolean checkIfContainsCharactersTwiceInARow( String input ) {
		String cleanedUpEntry = input.toLowerCase();
		for ( int i = 0; i < cleanedUpEntry.length() - 1; i++ ) {
			char firstCharacter = cleanedUpEntry.charAt( i );
			char secondCharacter = cleanedUpEntry.charAt( i + 1 );
			if ( Character.isAlphabetic( firstCharacter ) && Character.isAlphabetic(
					secondCharacter ) && ( firstCharacter == secondCharacter ) ) {
				return true;
			}
		}
		return false;
	}

	public boolean checkIfPairsOfCharactersAppearAtLeastTwiceInAWord( String input ) {
		String cleanedUpEntry = input.toLowerCase();
		for ( int i = 0; i < cleanedUpEntry.length() - 1; i++ ) {
			char firstCharacter = cleanedUpEntry.charAt( i );
			char secondCharacter = cleanedUpEntry.charAt( i + 1 );
			if ( Character.isAlphabetic( firstCharacter ) && Character.isAlphabetic(
					secondCharacter ) ) {
				String twoChars = cleanedUpEntry.substring( i, i + 2 );
				String leftoverString = cleanedUpEntry.substring( i + 2 );
				if ( leftoverString.contains( twoChars ) ) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checksIfContainsOneLetterIsSurroundedByTwoTheSameLetters( String input ) {
		String cleanedUpEntry = input.toLowerCase();
		for ( int i = 1; i < cleanedUpEntry.length() - 1; i++ ) {
			char leftCharacter = cleanedUpEntry.charAt( i - 1 );
			char rightCharacter = cleanedUpEntry.charAt( i + 1 );
			if ( Character.isAlphabetic( leftCharacter ) && Character.isAlphabetic(
					rightCharacter ) && ( leftCharacter == rightCharacter ) ) {
				return true;
			}
		}
		return false;
	}

	private List<String> parseInputFile( String schemaName ) {
		return FileReaderHelper.readFileAsLinesOfStrings( GoodAndNaughtyStrings.class, schemaName );
	}

	public static void main( String[] args ) {
		GoodAndNaughtyStrings goodAndNaughtyStrings = new GoodAndNaughtyStrings();
		System.out.println(goodAndNaughtyStrings.countGoodEntries( "input.txt", true ));
	}
}
