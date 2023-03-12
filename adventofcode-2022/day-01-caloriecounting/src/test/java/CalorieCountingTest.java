class CalorieCountingTest {

	public static void main( String[] args ) {
		CalorieCounting calorieCounting = new CalorieCounting();
		System.out.println( calorieCounting.runFindHighestCalorieIntake( "input.txt" ) );
		System.out.println(
				calorieCounting.runFindHighestCalorieIntakeForTopThreeElves( "input.txt" ) );
		//67633
		//199628
	}

}