public class UsefulRegex {

	/***
	 * ^ asserts position at start of a line
	 * Match a single character present in the list below [\d ]
	 * * matches the previous token between zero and unlimited times, as many times as possible, giving back as needed (greedy)
	 * \d matches a digit (equivalent to [0-9])
	 *   matches the character   with index 3210 (2016 or 408) literally (case sensitive)
	 * $ asserts position at the end of a line
	 * Global pattern flags
	 * g modifier: global. All matches (don't return after first match)
	 * m modifier: multi line. Causes ^ and $ to match the begin/end of each line (not only begin/end of string)
	 */

	public static final String ONLY_NUMBERS_WITH_SPACES = "[0-9 ]+";

	/***
	 * Match a single character present in the list below [\[\]]
	 * \[ matches the character [ with index 9110 (5B16 or 1338) literally (case sensitive)
	 * \] matches the character ] with index 9310 (5D16 or 1358) literally (case sensitive)
	 * Global pattern flags
	 * g modifier: global. All matches (don't return after first match)
	 * m modifier: multi line. Causes ^ and $ to match the begin/end of each line (not only begin/end of string)
	 */

	public static final String ONLY_SQUARE_BRACKETS = "[\\[\\]]";
}
