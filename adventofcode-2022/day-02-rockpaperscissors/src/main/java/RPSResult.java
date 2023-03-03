public enum RPSResult {

	WIN(6, 'Z'),
	DRAW(3, 'X'),
	LOST(0, 'Y');

	private Integer point;
	private char resultCode;

	RPSResult(Integer point, Character resultCode){
		this.point = point;
		this.resultCode = resultCode;
	}

	public Integer getPoint() {
		return point;
	}

	public Character getResultCode() {
		return resultCode;
	}
}
