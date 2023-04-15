import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TuningTroubleTest {

	private TuningTrouble tuningTrouble;
	public static final int PACKET_INTERVAL = 4;
	public static final int MESSAGE_INTERVAL = 14;

	@BeforeEach
	public void before() {
		tuningTrouble = new TuningTrouble();
	}

	private static Stream<Arguments> findIndexOfAMarker() {
		return Stream.of(
				Arguments.of( "mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, PACKET_INTERVAL ),
				Arguments.of( "mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19, MESSAGE_INTERVAL ),
				Arguments.of( "bvwbjplbgvbhsrlpgdmjqwftvncz", 5, PACKET_INTERVAL ),
				Arguments.of( "bvwbjplbgvbhsrlpgdmjqwftvncz", 23, MESSAGE_INTERVAL ),
				Arguments.of( "nppdvjthqldpwncqszvftbrmjlhg", 6, PACKET_INTERVAL ),
				Arguments.of( "nppdvjthqldpwncqszvftbrmjlhg", 23, MESSAGE_INTERVAL ),
				Arguments.of( "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, PACKET_INTERVAL ),
				Arguments.of( "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29, MESSAGE_INTERVAL ),
				Arguments.of( "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, PACKET_INTERVAL ),
				Arguments.of( "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26, MESSAGE_INTERVAL ),
				Arguments.of( "bvwk", 4, PACKET_INTERVAL ),
				Arguments.of( "1234", 4, PACKET_INTERVAL ),// numeric characters only
				Arguments.of( "a2bc", 4, PACKET_INTERVAL ),// mixture of alphabetic and numeric characters
				Arguments.of( "a!.[", 4, PACKET_INTERVAL));// mixture of alphabetic and non-alphabetic characters
	}

	@ParameterizedTest
	@MethodSource("findIndexOfAMarker")
	public void markerIndexCanBeFound_regularInputs( String input,  int expectedResult, int markerInterval) {
		int result = tuningTrouble.findSignalIndex( input, markerInterval );
		assertThat( result ).isEqualTo( expectedResult );
	}

	private static Stream<Arguments> unacceptableInputLengths() {
		return Stream.of( Arguments.of( "mjq" ), Arguments.of( "" ), Arguments.of( "    " ) );
	}

	@ParameterizedTest
	@MethodSource("unacceptableInputLengths")
	public void inputLengthMustBeGreaterOrEqualThanIntervalOf4( String input ) {
		int result = tuningTrouble.findSignalIndex( input, PACKET_INTERVAL );
		assertThat( result ).isEqualTo( -1 );
	}

	@Test
	public void markerIndexCanBeFound_duplicatedSetsOfUniqueCharacter() {
		int result = tuningTrouble.findSignalIndex( "linklink", PACKET_INTERVAL );
		assertThat( result ).isEqualTo( 4 );
	}

	@Test
	public void markerIndexCanBeFound_spacesAreDroppedAndNotTreatedAsUniqueCharacter() {
		int result = tuningTrouble.findSignalIndex( "mna mna ", PACKET_INTERVAL );
		assertThat( result ).isEqualTo( -1 );
	}

	@Test
	public void markerIndexCanBeFound_noCaseSensitivity() {
		int result = tuningTrouble.findSignalIndex( "mnaM", PACKET_INTERVAL );
		assertThat( result ).isEqualTo( -1 );
	}

	private static Stream<Arguments> trailingAndLeadingSpaces() {
		return Stream.of( Arguments.of( " abc ", -1 ), Arguments.of( " abcd ", 4 ) );
	}

	@ParameterizedTest
	@MethodSource("trailingAndLeadingSpaces")
	public void markerIndexCanBeFound_trailingAndLeadingSpacesAreDropped( String input,
			int expectedResult ) {
		int result = tuningTrouble.findSignalIndex( input, PACKET_INTERVAL );
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void filesCanBeProcessedAndFindSignalIndexes() {
		List<Integer> expected = List.of( -1, 4, -1, 7 );
		assertThat( tuningTrouble.findSignalIndexFromFile(
				"test_multipleLines.txt", PACKET_INTERVAL ) ).containsExactlyElementsOf( expected );
	}

	@Test
	public void masterFileIsProcessedCorrectly_packetInterval() {
		List<Integer> result = tuningTrouble.findSignalIndexFromFile( "input.txt", PACKET_INTERVAL );
		assertThat( result ).hasSize( 1 );
		assertThat( result.get( 0 ) ).isEqualTo( 1850 );
	}

	@Test
	public void masterFileIsProcessedCorrectly_messageInterval() {
		List<Integer> result = tuningTrouble.findSignalIndexFromFile( "input.txt", MESSAGE_INTERVAL );
		assertThat( result ).hasSize( 1 );
		assertThat( result.get( 0 ) ).isEqualTo( 2823 );
	}
}