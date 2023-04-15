import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreeMapTest {

	private TreeMap treeMap;

	@BeforeEach
	public void before(){
		treeMap = new TreeMap();
	}

	@Test
	public void tallestTreeIsFoundIfSurroundedByShorterTrees(){
		int[] row1 = {9,2,9};
		int[] row2 = {1,5,3};
		int[] row3 = {9,2,9};
		int[][] map = {row1, row2, row3};

		List<TreeCoord> treeCoords = treeMap.findTallTrees(map);
		assertThat(treeCoords).hasSize( 1 );
		TreeCoord treeCoord = treeCoords.get( 0 );
		assertThat( treeCoord.getColumn() ).isEqualTo( 1 );
		assertThat( treeCoord.getRow() ).isEqualTo( 1 );
		assertThat( treeCoord.getValue() ).isEqualTo( 5 );
	}

}