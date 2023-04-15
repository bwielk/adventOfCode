public class Command {

	private final int amountOfCratesToMove;
	private final int entryStackIndex;
	private final int targetStackIndex;

	public Command( final int amountOfCratesToMove, final int entryStackIndex,
			final int targetStackIndex ) {
		this.amountOfCratesToMove = amountOfCratesToMove;
		this.entryStackIndex = entryStackIndex;
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
