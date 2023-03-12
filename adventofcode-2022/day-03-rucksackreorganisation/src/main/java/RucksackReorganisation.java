import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RucksackReorganisation {

	public int runReorganisation( String file ) {
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings(
				RucksackReorganisation.class, file );
		int total = 0;
		for ( String e : entries ) {
			total += calculatePointsForCommonCharacters(
					findCommonCharacters( defineTwoCompartments( e ) ) );
		}
		return total;
	}

	public int runIdentificationOfCommonCharacters( String file ) {
		List<String> entries = FileReaderHelper.readFileAsLinesOfStrings(
				RucksackReorganisation.class, file );
		return checkCommonCharactersInChunksOfUpToThreeEntries( entries );
	}

	public int checkCommonCharactersInChunksOfUpToThreeEntries( List<String> entries ) {
		int total = 0;
		if ( entries.size() >= 3 && entries.size() % 3 == 0 ) {
			for ( int i = 0; i < entries.size(); i++ ) {
				List<String> group;
				if ( entries.size() > 1 ) {
					group = entries.subList( i, i + 3 );
					List<Character> identifiedCharacters = findCommonCharactersInASeriesOfEntries(
							group );
					total += calculatePointsForCommonCharacters( identifiedCharacters );
					i += 2;
				} else {
					group = entries.subList( i, entries.size() );
					List<Character> identifiedCharacters = findCommonCharactersInASeriesOfEntries(
							group );
					total += calculatePointsForCommonCharacters( identifiedCharacters );
				}
			}
		} else {
			throw new IllegalArgumentException(
					"The amount of entries must be greater than 3 and be dividable by 3" );
		}
		return total;
	}

	public List<String> defineTwoCompartments( String input ) {
		input = input.replace( " ", "" );
		List<String> result = new ArrayList<>();
		if ( input.length() % 2 != 0 || input.isEmpty() ) {
			return result;
		} else {
			int splitIndex = input.length() / 2;
			result.add( input.substring( 0, splitIndex ) );
			result.add( input.substring( splitIndex ) );
		}
		return result;
	}

	public List<Character> findCommonCharacters( final List<String> input ) {
		List<Character> result = new ArrayList<>();
		for ( Character c1 : input.get( 0 ).toCharArray() ) {
			for ( Character c2 : input.get( 1 ).toCharArray() ) {
				if ( c1 == c2 ) {
					if ( !result.contains( c1 ) ) {
						result.add( c1 );
					}
				}
			}
		}
		return result;
	}

	public int calculatePointsForCommonCharacters( List<Character> listOfCharacters ) {
		int total = 0;
		for ( Character c : listOfCharacters ) {
			if ( Character.isLowerCase( c ) ) {
				//based on the ASCII character values - a == 97 and z == 122
				total += c - 96;
			} else {
				//based on the ASCII character values - A == 65 and Z == 90
				total += c - 38;
			}
		}
		return total;
	}

	public List<Character> findCommonCharactersInASeriesOfEntries( List<String> entries ) {
		Map<Character, Integer> tracker = new HashMap<>();
		List<Character> results = new ArrayList<>();
		List<String> sanitisedEntries = entries.stream()
				.map( StringHelper::removeDuplicateChars )
				.collect( Collectors.toList() );
		if ( sanitisedEntries.size() > 1 ) {
			for ( String entry : sanitisedEntries ) {//go through the list of strings
				for ( int i = 0; i < entry.length(); i++ ) {//go through characters of the current string
					char currentChar = entry.charAt( i );
					for ( int n = 0; n < sanitisedEntries.size(); n++ ) {//begin looping through strings except your current one
						String comparedString = sanitisedEntries.get( n );
						if ( sanitisedEntries.indexOf( entry ) != n ) {
							for ( int c = 0; c < comparedString.length(); c++ ) {//start comparing chars of the string to compare with ones of the compared one
								if ( currentChar == comparedString.charAt( c ) ) {
									if ( !tracker.containsKey( currentChar ) ) {
										tracker.put( currentChar, 1 );
									} else {
										tracker.put( currentChar, tracker.get( currentChar ) + 1 );
									}
								}
							}
						}
					}
				}
			}
		}
		for ( Map.Entry<Character, Integer> en : tracker.entrySet() ) {
			if ( en.getValue() >= entries.size() ) {
				results.add( en.getKey() );
			}
		}
		return results;
	}
}
