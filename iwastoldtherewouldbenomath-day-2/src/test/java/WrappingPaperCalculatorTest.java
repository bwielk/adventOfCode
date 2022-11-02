import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WrappingPaperCalculatorTest {

	WrappingPaperCalculator wrappingPaperCalculator;

	@BeforeEach
	public void before(){
		wrappingPaperCalculator = new WrappingPaperCalculator();
	}

	@Test
	public void canCalculateBasicMeasurementsOneByOneByOne(){
		int result = wrappingPaperCalculator.calculateFromFile("simpleInput.txt");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasicMeasurementTwoByThreeByFour(){
		int result = wrappingPaperCalculator.calculateFromFile("bigBoxInput.txt");
		int expectedResult = 58;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasicMeasurementTwoByThreeByFourAndOneByOneByTen(){
		int result = wrappingPaperCalculator.calculateFromFile("bigBoxesInput.txt");
		int expectedResult = 101;
		assertThat(result).isEqualTo( expectedResult );
	}


}