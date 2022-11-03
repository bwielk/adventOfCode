import static org.assertj.core.api.Assertions.assertThat;

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
}