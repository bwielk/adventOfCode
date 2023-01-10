class CalorieCountingTest {

	public static void main( String[] args ) {
		CalorieCounting calorieCounting = new CalorieCounting();
		System.out.println(calorieCounting.findHighestCalorieIntake( "input.txt" ));
		System.out.println(calorieCounting.findHighestCalorieIntakeForTopThreeElves( "input.txt" ));
	}

}