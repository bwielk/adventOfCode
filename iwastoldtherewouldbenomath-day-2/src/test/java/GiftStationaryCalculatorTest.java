import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiftStationaryCalculatorTest {

	GiftStationaryCalculator giftStationaryCalculator;

	@BeforeEach
	public void before() {
		giftStationaryCalculator = new GiftStationaryCalculator();
	}

	@Test
	public void canCalculateBasicMeasurementsOneByOneByOne() {
		int result = giftStationaryCalculator.calculateAreaForWrappingPaper( "simpleInput.txt" );
		int expectedResult = 7;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasicMeasurementTwoByThreeByFour() {
		int result = giftStationaryCalculator.calculateAreaForWrappingPaper( "bigBoxInput.txt" );
		int expectedResult = 58;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasedOnMixtureOfCorrectlyAndIncorrectlyFormattedMeasurements() {
		int result = giftStationaryCalculator.calculateAreaForWrappingPaper( "wrongInputs.txt" );
		int expectedResult = 1074;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void canCalculateBasicMeasurementTwoByThreeByFourAndOneByOneByTen() {
		int result = giftStationaryCalculator.calculateAreaForWrappingPaper( "bigBoxesInput.txt" );
		int expectedResult = 101;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void canIgnoreNonRectangularInputsFourMeasurementsInsteadOfThree() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1x1x1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Collections.emptyList() );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void canIgnoreNonRectangularInputsTwoMeasurementsInsteadOfThree() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Collections.emptyList() );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void canParseEntryAsLongAsTheEntryContainsThreeSeparateDigits() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1x1x" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 1 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void xDelimeterCanBeAsCapitalOrNonCapitalCharacter() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1X1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 1 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void canExcludeEntriesWithZerosInIt() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1x0" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 0 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void canHandleEmptyEntries() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Collections.emptyList() );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void canHandleTensTeensAndHundreds() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "11x30x200" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 11, 30, 200 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void onlyXDelimetersAreAcceptedOthersBreakAnEntry() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1x1z1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Collections.emptyList() );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void duplicatedXDelimetersAreNotAccepted() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1xx1xx1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 1 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void spacesBetweenDelimetersAreIgnored() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "1 x 1 x 1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 1 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void leadingZerosAreIgnoredAndResultInProcessedEntry() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "01x01x1" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 1, 1, 1 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void additionalIncorrectDelimeterIsIgnored() {
		List<Integer> result = giftStationaryCalculator.parseEntries( "2x3x4xz" );
		ArrayList<Integer> expectedResult = new ArrayList<>( Arrays.asList( 2, 3, 4 ) );
		assertThat( result ).containsAll( expectedResult );
	}

	@Test
	public void calculateTheAmountOfRibbonForRegularSizeBoxes() {
		int result = giftStationaryCalculator.calculateRibbonLengthForBoxes( "bigBoxesInput.txt" );
		int expectedResult = 48;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void calculateTheAmountOfRibbonForARegularSizeBox() {
		int result = giftStationaryCalculator.calculateRibbonLengthForBoxes( "bigBoxInput.txt" );
		int expectedResult = 34;
		assertThat( result ).isEqualTo( expectedResult );
	}

	@Test
	public void calculateTheAmountOfRibbonForRegularSizeBoxesMixedWithWrongInputs() {
		int result = giftStationaryCalculator.calculateRibbonLengthForBoxes( "wrongInputs.txt" );
		int expectedResult = 544;
		assertThat( result ).isEqualTo( expectedResult );
	}
}