import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}