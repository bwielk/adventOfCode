import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TreeMap {

	public List<TreeCoord> findTallTreesFromFile(String fileName){
		List<String> input = FileReaderHelper.readFileAsLinesOfStrings( TreeMap.class, fileName );
		int mapHeight = input.size();
		int maxMapWidth = ListHelper.findLongestStringInList(input);
		int[][] mapOfTrees = new int[mapHeight][maxMapWidth];
		for(String s : input){
			if(s.chars().allMatch( Character::isDigit )){
				mapOfTrees[input.indexOf( s )] = Stream.of(s.split(""))
						.mapToInt(Integer::parseInt)
						.toArray();
			}
		}
		return findTallTrees( mapOfTrees );
	}

	public List<TreeCoord> findVisibleTreesFromFile(String fileName){
		List<String> input = FileReaderHelper.readFileAsLinesOfStrings( TreeMap.class, fileName );
		int mapHeight = input.size();
		int maxMapWidth = ListHelper.findLongestStringInList(input);
		int[][] mapOfTrees = new int[mapHeight][maxMapWidth];
		for(String s : input){
			if(s.chars().allMatch( Character::isDigit )){
				mapOfTrees[input.indexOf( s )] = Stream.of(s.split(""))
						.mapToInt(Integer::parseInt)
						.toArray();
			}
		}
		return findVisibleTrees( mapOfTrees );
	}

	public List<TreeCoord> findVisibleTrees( final int[][] map ) {
		List<TreeCoord> results = new ArrayList<>();
		List<Integer> heightsOfSurroundingTrees = new ArrayList<>();
		//add trees on the edge
		int treeHeight = -1;
		for(int r = 0; r<map.length; r++){
			int[] currentRow = map[r];
			for(int c = 0; c< currentRow.length; c++){
				treeHeight = map[r][c];
				if( r == 0 || c == currentRow.length - 1 || r == map.length - 1 || c == 0 ){
					results.add( new TreeCoord( r, c, treeHeight) );
				}else{
					for ( Directions d : Directions.values() ) {
							heightsOfSurroundingTrees.add( map[r + d.getX()][c + d.getY()] );
						}
					}
					if ( ListHelper.checkIfIntIsGreaterThanAtLeastOneIntInList( heightsOfSurroundingTrees,
							treeHeight ) ) {
						results.add( new TreeCoord( r, c, treeHeight ) );
					}
					heightsOfSurroundingTrees.clear();
				}
			}
		return results;
	}

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
