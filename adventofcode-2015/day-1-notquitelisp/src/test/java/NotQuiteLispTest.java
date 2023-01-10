import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotQuiteLispTest {

	NotQuiteLisp notQuiteLisp;

	@BeforeEach
	public void before(){
		notQuiteLisp = new NotQuiteLisp();
	}

	@Test()
	public void emptyInputFileIsNotProcessed(){
		Exception e = assertThrows( IllegalArgumentException.class,
				() -> notQuiteLisp.calculateFinalFloor( "emptyInput.txt" ));
		assertThat(e.getMessage()).isEqualTo( ErrorContent.INVALID_ROW_NUMBER );
	}

	@Test()
	public void twoLineInputFileIsNotProcessed(){
		Exception e = assertThrows( IllegalArgumentException.class,
				() -> notQuiteLisp.calculateFinalFloor( "twoLineInput.txt" ));
		assertThat(e.getMessage()).isEqualTo( ErrorContent.INVALID_ROW_NUMBER );
	}

	@Test()
	public void nullPointerExceptionThrownWhenFileDoesNotExist(){
		Exception e = assertThrows( NullPointerException.class,
				() -> notQuiteLisp.calculateFinalFloor( "doesNotExist.txt" ));
		assertThat(e).isInstanceOf( NullPointerException.class );
	}

	@Test()
	public void singleLineInputIsCorrectlyProcessed(){
		int actualResult = notQuiteLisp.calculateFinalFloor("singleLineInput.txt");
		int expectedResult = 1;
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@Test()
	public void singleLineInputWithVariousCharactersIsStillProcessed(){
		int actualResult = notQuiteLisp.calculateFinalFloor("inputWithVariousChars.txt");
		int expectedResult = -2;
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@Test()
	public void singleLineInputWithoutBracketsInItIsStillProcessed(){
		int actualResult = notQuiteLisp.calculateFinalFloor("inputWithoutBrackets.txt");
		int expectedResult = 0;
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@Test()
	public void singleLineInputAsCsvIsStillProcessed(){
		int actualResult = notQuiteLisp.calculateFinalFloor("inputAsCsv.csv");
		int expectedResult = -4;
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@Test()
	public void exampleFileIsCorrectlyProcessed(){
		int actualResult = notQuiteLisp.calculateFinalFloor("input.txt");
		int expectedResult = 280;
		assertThat( actualResult ).isEqualTo( expectedResult );
	}

	@Test()
	public void basementIsReachedAtSpecificIndex(){
		List<BasementRecord> records = notQuiteLisp.investigateReachingBasement("reachingBasement.txt");
		assertThat( records ).hasSize( 2 );
		BasementRecord firstRecord = records.get( 0 );
		BasementRecord secondRecord = records.get( 1 );
		assertThat( firstRecord.getIndex()).isEqualTo( 4 );
		assertThat( firstRecord.getValue()).isEqualTo( -1 );
		assertThat( secondRecord.getIndex()).isEqualTo( 5 );
		assertThat( secondRecord.getValue()).isEqualTo( -2 );
	}

	@Test
	public void notReachingBasementIsProcessedCorrectly(){
		List<BasementRecord> records = notQuiteLisp.investigateReachingBasement("singleLineInput.txt");
		assertThat( records ).isEmpty();
	}

	@Test
	public void inputWithVariousCharsStillCanBeProcessedAndIdentifyBasementEntries(){
		List<BasementRecord> records = notQuiteLisp.investigateReachingBasement("inputWithVariousChars.txt");
		assertThat( records ).hasSize( 2 );
		assertThat( records.get( 1 ).getIndex()).isEqualTo( 18 );
		assertThat( records.get( 1 ).getValue()).isEqualTo( -2 );
	}

	@Test()
	public void emptyBasementInputFileIsNotProcessed(){
		Exception e = assertThrows( IllegalArgumentException.class,
				() -> notQuiteLisp.investigateReachingBasement( "emptyInput.txt" ));
		assertThat(e.getMessage()).isEqualTo( ErrorContent.INVALID_ROW_NUMBER );
	}

	@Test()
	public void twoLineBasementInputFileIsNotProcessed(){
		Exception e = assertThrows( IllegalArgumentException.class,
				() -> notQuiteLisp.investigateReachingBasement( "twoLineInput.txt" ));
		assertThat(e.getMessage()).isEqualTo( ErrorContent.INVALID_ROW_NUMBER );
	}

	@Test()
	public void nullPointerExceptionThrownWhenBasementFileDoesNotExist(){
		Exception e = assertThrows( NullPointerException.class,
				() -> notQuiteLisp.investigateReachingBasement( "doesNotExist.txt" ));
		assertThat(e).isInstanceOf( NullPointerException.class );
	}

	@Test()
	public void singleLineBasementInputWithoutBracketsInItIsStillProcessed(){
		List<BasementRecord> result = notQuiteLisp.investigateReachingBasement("inputWithoutBrackets.txt");
		assertThat( result ).isEmpty();
	}

	@Test()
	public void exampleBasementFileIsCorrectlyProcessedIdentifiesReachingBasementFirstTime(){
		List<BasementRecord> result = notQuiteLisp.investigateReachingBasement("input.txt");
		BasementRecord firstTimeReachingBasement = result.get( 0 );
		assertThat( firstTimeReachingBasement.getIndex() ).isEqualTo( 1796 );
		assertThat( firstTimeReachingBasement.getValue() ).isEqualTo( -1 );
	}
}