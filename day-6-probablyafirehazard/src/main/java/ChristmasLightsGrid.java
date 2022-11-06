public class ChristmasLightsGrid {

	public boolean verifyEntryMatchesSentenceModel( String entry ) {
		return entry.matches( "^(?=.*(turn on|turn off|toggle)+( )+(.)+(through)).*$" );
	}
}
