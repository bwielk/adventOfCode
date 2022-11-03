import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SantaMapTest {

	SantaMap santaMap;

	@BeforeEach
	public void before(){
		santaMap = new SantaMap();
	}

	@Test
	public void santaVisitsTwoHouses(){
		int result = santaMap.countHouses(">");
		int expectedResults = 2;
		assertThat(result).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsFourHouses(){
		int results = santaMap.countHouses( "^>v<" );
		int expectedResults = 4;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimes(){
		int results = santaMap.countHouses( "^v^v^v^v^v" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithSpacesInBetween(){
		int results = santaMap.countHouses( "^ v ^ v ^ v    ^v^v" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithInvalidCharactersInTheBeginningAndEndOfEntry(){
		int results = santaMap.countHouses( ".^v^v.!£" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesMultipleTimesEntriesWithInvalidCharactersInsideTheEntry(){
		int results = santaMap.countHouses( "^v.^v" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessAnEmptyEntry(){
		int results = santaMap.countHouses( "" );
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessAnEntryConsistingOfNoDirectionsAtAll(){
		int results = santaMap.countHouses( "nothing1!" );
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}


	@Test
	public void santaVisitsTwoHousesWithLeadingAndTrailingSpaces(){
		int results = santaMap.countHouses( " ^v^v " );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaVisitsTwoHousesWithCapitalVs(){
		int results = santaMap.countHouses( "^V^v" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void santaDoesNotProcessANull(){
		assertThrows(NullPointerException.class, () -> santaMap.countHouses( null ));
	}

	@Test
	public void processingOfTheSantaMapSimpleInputWithOneRowOfDirections(){
		int results = santaMap.runCountingHousesFromSchema( "simpleInput.txt" );
		int expectedResults = 2;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfTheSantaMapSimpleInputWithManyRowsOfDirections(){
		Exception e = assertThrows( IllegalStateException.class,
				() -> santaMap.runCountingHousesFromSchema( "multipleLineInput.txt" ));
		assertThat( e.getMessage() ).isEqualTo( ErrorMessages.TOO_MANY_LINES );
	}

	@Test
	public void processingOfTheSantaMapSimpleEmptyInputFileWithEmptyLines(){
		int results = santaMap.runCountingHousesFromSchema( "emptyFile.txt" );
		int expectedResults = 0;
		assertThat( results ).isEqualTo( expectedResults );
	}

	@Test
	public void processingOfTheSantaMapActualMap(){
		int results = santaMap.runCountingHousesFromSchema( "input.txt" );
		int expectedResults = 2565;
		assertThat( results ).isEqualTo( expectedResults );
	}
}