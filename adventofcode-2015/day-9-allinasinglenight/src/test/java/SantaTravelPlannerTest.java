import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SantaTravelPlannerTest {
//
//	String testEntry =
//			"Dublin to London = 464\n"
//					+ "London to Belfast = 518\n"
//					+ "Dublin to Belfast = 141";

	private SantaTravelPlanner santaTravelPlanner;

	@BeforeEach
	public void before(){
		santaTravelPlanner = new SantaTravelPlanner();
	}

	@Test
	public void canParseASingleEntry(){
		String testEntry =
				"Dublin to London = 464";
		List<String> results = santaTravelPlanner.shortestDistance
				(santaTravelPlanner.createRelationEntities( testEntry ));
		assertThat(results).hasSize( 1 );
		assertThat(results.get( 0 )).isEqualTo( "Dublin -> London: 464" );
	}

	@Test
	public void canParseAnEntryWithTwoRelationsInItWithApparentLinks(){
		String testEntry =
				"Dublin to London = 464\n" +
				"London to Belfast = 518";
		List<String> results = santaTravelPlanner.shortestDistance
				(santaTravelPlanner.createRelationEntities( testEntry ));
		assertThat(results).hasSize( 1 );
		assertThat(results.get( 0 )).isEqualTo( "Dublin -> London -> Belfast: 982" );
	}
	//no duplicates
	//no duplicates with swapped from-to
	//only bilateral relations
	//multi-word names e.g. King's cross
	//empty file
	//single entry
	//2 line entry
}