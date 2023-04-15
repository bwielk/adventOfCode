public enum FileSystemActions {

	MOVE_TO_DIR("$ cd "),
	MOVE_OUT_LEVEL_UP("$ cd .."),
	MOVE_TO_OUTERMOST("$ cd /"),
	LIST_FILES_AND_DIRS("$ ls");

	String command;

	FileSystemActions(String command){
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
