import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeMapTest {

	private TreeMap treeMap;

	@BeforeEach
	public void before() {
		treeMap = new TreeMap();
	}

	@Test
	public void tallestTreeIsFoundIfSurroundedByShorterTrees_tallestTreeInCentre() {
		int[] row1 = { 2, 2, 2 };
		int[] row2 = { 2, 5, 2 };
		int[] row3 = { 2, 2, 2 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findTallTrees( map );
		assertThat( treeCoords ).hasSize( 1 );
		TreeCoord treeCoord = treeCoords.get( 0 );
		assertThat( treeCoord.getColumn() ).isEqualTo( 1 );
		assertThat( treeCoord.getRow() ).isEqualTo( 1 );
		assertThat( treeCoord.getValue() ).isEqualTo( 5 );
	}

	@Test
	public void tallestTreeIsFoundIfSurroundedByShorterTrees_tallestTreesInTheCorners() {
		int[] row1 = { 2, 1, 1 };
		int[] row2 = { 1, 1, 1 };
		int[] row3 = { 2, 1, 2 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findTallTrees( map );
		assertThat( treeCoords ).hasSize( 3 );
		TreeCoord treeCoord1 = treeCoords.get( 0 );
		TreeCoord treeCoord2 = treeCoords.get( 1 );
		TreeCoord treeCoord3 = treeCoords.get( 2 );

		assertThat( treeCoord1.getColumn() ).isEqualTo( 0 );
		assertThat( treeCoord1.getRow() ).isEqualTo( 0 );
		assertThat( treeCoord1.getValue() ).isEqualTo( 2 );

		assertThat( treeCoord2.getColumn() ).isEqualTo( 0 );
		assertThat( treeCoord2.getRow() ).isEqualTo( 2 );
		assertThat( treeCoord2.getValue() ).isEqualTo( 2 );

		assertThat( treeCoord3.getColumn() ).isEqualTo( 2 );
		assertThat( treeCoord3.getRow() ).isEqualTo( 2 );
		assertThat( treeCoord3.getValue() ).isEqualTo( 2 );
	}

	@Test
	public void visibleTrees_edgeTreesCanBeExtracted() {
		int[] row1 = { 2, 1, 1 };
		int[] row2 = { 1, 0, 1 };
		int[] row3 = { 2, 1, 2 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findVisibleTrees( map );
		assertThat( treeCoords ).hasSize( 8 );
		for(TreeCoord tc : treeCoords){
			assert tc.getValue() != 0;
		}
	}

	@Test
	public void visibleTrees_edgeTreesCanBeExtractedAndTheTallestTreeInTheMiddleIsIdentified() {
		int[] row1 = { 2, 1, 1 };
		int[] row2 = { 1, 3, 1 };
		int[] row3 = { 2, 1, 2 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findVisibleTrees( map );
		assertThat( treeCoords ).hasSize( 9 );
	}

	@Test
	public void tallestTreeIsFoundIfSurroundedByShorterTrees_tallestTreesOnSides() {
		int[] row1 = { 1, 5, 1 };
		int[] row2 = { 4, 1, 3 };
		int[] row3 = { 1, 2, 1 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findTallTrees( map );
		assertThat( treeCoords ).hasSize( 4 );
		TreeCoord treeCoord1 = treeCoords.get( 0 );
		TreeCoord treeCoord2 = treeCoords.get( 1 );
		TreeCoord treeCoord3 = treeCoords.get( 2 );
		TreeCoord treeCoord4 = treeCoords.get( 3 );

		assertThat( treeCoord1.getColumn() ).isEqualTo( 1 );
		assertThat( treeCoord1.getRow() ).isEqualTo( 0 );
		assertThat( treeCoord1.getValue() ).isEqualTo( 5 );

		assertThat( treeCoord2.getColumn() ).isEqualTo( 0 );
		assertThat( treeCoord2.getRow() ).isEqualTo( 1 );
		assertThat( treeCoord2.getValue() ).isEqualTo( 4 );

		assertThat( treeCoord3.getColumn() ).isEqualTo( 2 );
		assertThat( treeCoord3.getRow() ).isEqualTo( 1 );
		assertThat( treeCoord3.getValue() ).isEqualTo( 3 );

		assertThat( treeCoord4.getColumn() ).isEqualTo( 1 );
		assertThat( treeCoord4.getRow() ).isEqualTo( 2 );
		assertThat( treeCoord4.getValue() ).isEqualTo( 2 );
	}

	@Test
	public void tallestTreeIsFoundIfSurroundedByShorterTrees_negativeValuesCanBeProcessed() {
		int[] row1 = { -5, -5, -4 };
		int[] row2 = { -5, -1, -5 };
		int[] row3 = { -5, -5, -5 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findTallTrees( map );
		assertThat( treeCoords ).hasSize( 2 );
		TreeCoord treeCoord1 = treeCoords.get( 0 );
		TreeCoord treeCoord2 = treeCoords.get( 1 );

		assertThat( treeCoord1.getColumn() ).isEqualTo( 2 );
		assertThat( treeCoord1.getRow() ).isEqualTo( 0 );
		assertThat( treeCoord1.getValue() ).isEqualTo( -4 );

		assertThat( treeCoord2.getColumn() ).isEqualTo( 1 );
		assertThat( treeCoord2.getRow() ).isEqualTo( 1 );
		assertThat( treeCoord2.getValue() ).isEqualTo( -1 );

	}

	@Test
	public void noTallestTreeIfAllSurroundingTreesAreOfTheSameHeight() {
		int[] row1 = { 9, 9, 9 };
		int[] row2 = { 9, 9, 9 };
		int[] row3 = { 9, 9, 9 };
		int[][] map = { row1, row2, row3 };

		List<TreeCoord> treeCoords = treeMap.findTallTrees( map );
		assertThat( treeCoords ).hasSize( 0 );
	}

	@Test
	public void findingTallTrees_findFromFile() {
		List<TreeCoord> treeCoords = treeMap.findTallTreesFromFile( "test.txt" );
		assertThat( treeCoords ).hasSize( 7 );
	}

	@Test
	public void findingVisibleTrees_findFromFile() {
		List<TreeCoord> treeCoords = treeMap.findVisibleTreesFromFile( "test.txt" );
		assertThat( treeCoords ).hasSize( 21 );
	}


	@Test
	public void findingTallTrees_findFromMasterFile() {
		List<TreeCoord> treeCoords = treeMap.findVisibleTreesFromFile( "input.txt" );
		assertThat( treeCoords ).hasSize( 0 );
	}
}
