public class Command {

	private final int entryStackIndex;
	private final int amountOfCratesToMove;
	private final int targetStackIndex;

	public Command( final int entryStackIndex, final int amountOfCratesToMove,
			final int targetStackIndex ) {
		this.entryStackIndex = entryStackIndex;
		this.amountOfCratesToMove = amountOfCratesToMove;
		this.targetStackIndex = targetStackIndex;
	}

	public int getEntryStackIndex() {
		return entryStackIndex;
	}

	public int getAmountOfCratesToMove() {
		return amountOfCratesToMove;
	}

	public int getTargetStackIndex() {
		return targetStackIndex;
	}
}
