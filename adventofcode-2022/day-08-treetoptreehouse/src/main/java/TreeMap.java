import java.util.List;

public class TreeMap {

	public List<TreeCoord> findTallTrees( final int[][] map ) {
		for(int r=0; r<map.length; r++){
			int[] currentRow = map[r];
			for(int c=0; c<currentRow.length; c++){
				System.out.println(map[r][c]);
			}
		}
		return null;
	}
}
