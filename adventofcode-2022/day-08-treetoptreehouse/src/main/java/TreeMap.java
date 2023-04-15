import java.util.ArrayList;
import java.util.List;

public class TreeMap {

	public List<TreeCoord> findTallTrees( final int[][] map ) {
		List<TreeCoord> results = new ArrayList<>();
		List<Integer> heightsOfSurroundingTrees = new ArrayList<>();
		for ( int r = 0; r < map.length; r++ ) {
			int[] currentRow = map[r];
			for ( int c = 0; c < currentRow.length; c++ ) {
				int currentTree = map[r][c];
				for ( Directions d : Directions.values() ) {
					try {
						heightsOfSurroundingTrees.add( map[r + d.getX()][c + d.getY()] );
					} catch ( ArrayIndexOutOfBoundsException e ) {
						e.printStackTrace();
					}
				}
				if ( ListHelper.checkIfIntIsGreaterThanValuesInIntList( heightsOfSurroundingTrees,
						currentTree ) ) {
					results.add( new TreeCoord( r, c, currentTree ) );
				}
				heightsOfSurroundingTrees.clear();
			}
		}
		return results;
	}
}
