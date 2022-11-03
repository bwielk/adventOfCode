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
	public void canCalculateBasedOnMixtureOfCorrectlyAndIncorrectlyFormattedMeasurements(){
		int result = wrappingPaperCalculator.calculateFromFile("wrongInputs.txt");
		int expectedResult = 958;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasicMeasurementTwoByThreeByFourAndOneByOneByTen(){
		int result = wrappingPaperCalculator.calculateFromFile("bigBoxesInput.txt");
		int expectedResult = 101;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canIgnoreNonRectangularInputsFourMeasurementsInsteadOfThree(){
		int result = wrappingPaperCalculator.parseEntry("1x1x1x1");
		int expectedResult = 0;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canIgnoreNonRectangularInputsTwoMeasurementsInsteadOfThree(){
		int result = wrappingPaperCalculator.parseEntry("1x1");
		int expectedResult = 0;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canParseEntryAsLongAsTheEntryContainsThreeSeparateDigits(){
		int result = wrappingPaperCalculator.parseEntry("1x1x1x");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void xDelimeterCanBeAsCapitalOrNonCapitalCharacter(){
		int result = wrappingPaperCalculator.parseEntry("1x1X1");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canExcludeEntriesWithZerosInIt(){
		int result = wrappingPaperCalculator.parseEntry("1x1x0");
		int expectedResult = 0;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canHandleEmptyEntries(){
		int result = wrappingPaperCalculator.parseEntry("");
		int expectedResult = 0;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void canHandleTensTeensAndHundreds(){
		int result = wrappingPaperCalculator.parseEntry("11x30x200");
		int expectedResult = 17390;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void onlyXDelimetersAreAcceptedOthersBreakAnEntry(){
		int result = wrappingPaperCalculator.parseEntry("1x1z1");
		int expectedResult = 0;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void duplicatedXDelimetersAreNotAccepted(){
		int result = wrappingPaperCalculator.parseEntry("1xx1xx1");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void spacesBetweenDelimetersAreIgnored(){
		int result = wrappingPaperCalculator.parseEntry("1 x 1 x 1");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void leadingZerosAreIgnoredAndResultInProcessedEntry(){
		int result = wrappingPaperCalculator.parseEntry("01x01x1");
		int expectedResult = 7;
		assertThat(result).isEqualTo( expectedResult );
	}

	@Test
	public void additionalIncorrectDelimeterIsIgnored(){
		int result = wrappingPaperCalculator.parseEntry("2x3x4xz");
		int expectedResult = 58;
		assertThat(result).isEqualTo( expectedResult );
	}
}