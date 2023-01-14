public enum RPSResult {

	WIN(6),
	DRAW(3),
	LOST(0);

	private Integer point;

	RPSResult(Integer point){
		this.point = point;
	}

	public Integer getPoint() {
		return point;
	}
}
