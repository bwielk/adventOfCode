import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SantaMapTest {

	SantaMap santaMap;

	@BeforeEach
	public void before() {
		santaMap = new SantaMap();
	}

	@Test
	public void santaVisitsTwoHouses() {
		int result = santaMap.countHouses( ">", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( result ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsFourHouses() {
		int results = santaMap.countHouses( "^>v<", RunningMode.SANTA_ONLY );
		int expectedResults = 4;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimes() {
		int results = santaMap.countHouses( "^v^v^v^v^v", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithSpacesInBetween() {
		int results = santaMap.countHouses( "^ v ^ v ^ v    ^v^v", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithInvalidCharactersInTheBeginningAndEndOfEntry() {
		int results = santaMap.countHouses( ".^v^v.!£", RunningMode.SANTA_ONLY);
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithInvalidCharactersInsideTheEntry() {
		int results = santaMap.countHouses( "^v.^v", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessAnEmptyEntry() {
		int results = santaMap.countHouses( "", RunningMode.SANTA_ONLY);
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessAnEntryConsistingOfNoDirectionsAtAll() {
		int results = santaMap.countHouses( "nothing1!", RunningMode.SANTA_ONLY );
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesWithLeadingAndTrailingSpaces() {
		int results = santaMap.countHouses( " ^v^v ", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesWithCapitalVs() {
		int results = santaMap.countHouses( "^V^v", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessANull() {
		assertThrows( NullPointerException.class, () -> santaMap.countHouses( null , RunningMode.SANTA_ONLY) );
	}

	@Test
	public void processingOfTheSantaMapSimpleInputWithOneRowOfDirections() {
		int results = santaMap.runCountingHousesFromSchema( "simpleInput.txt", RunningMode.SANTA_ONLY );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfTheSantaMapSimpleInputWithManyRowsOfDirections() {
		Exception e = assertThrows( IllegalStateException.class,
				() -> santaMap.runCountingHousesFromSchema( "multipleLineInput.txt", RunningMode.SANTA_ONLY ) );
		assertThat( e.getMessage() ).isEqualTo( ErrorMessages.TOO_MANY_LINES );
	}

	@Test
	public void processingOfTheSantaMapSimpleEmptyInputFileWithEmptyLines() {
		int results = santaMap.runCountingHousesFromSchema( "emptyFile.txt", RunningMode.SANTA_ONLY );
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfTheSantaMapActualMap() {
		int results = santaMap.runCountingHousesFromSchema( "input.txt", RunningMode.SANTA_ONLY );
		int expectedResults = 2565;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfASimpleEntryWithRoboSanta() {
		int results = santaMap.countHouses( "^v", RunningMode.WITH_ROBO_SANTA );
		int expectedResults = 3;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfAnEntryWithRoboSantaMeetingBackAtTheSamePoint() {
		int results = santaMap.countHouses( "^>v<", RunningMode.WITH_ROBO_SANTA );
		int expectedResults = 3;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfAnEntryWithRoboSantaGoingDifferentDirections() {
		int results = santaMap.countHouses( "^v^v^v^v^v", RunningMode.WITH_ROBO_SANTA );
		int expectedResults = 11;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void readingAnActualMapInRoboSantaMode() {
		int results = santaMap.runCountingHousesFromSchema( "input.txt", RunningMode.WITH_ROBO_SANTA );
		int expectedResults = 2639;
		assertThat( results ).isEqualTo( expectedResults );
	}
}