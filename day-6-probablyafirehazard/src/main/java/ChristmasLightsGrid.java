import java.util.ArrayList;
import java.util.List;

public class ChristmasLightsGrid {

	public boolean verifyEntryMatchesSentenceModel( String entry ) {
		return entry.matches( "^(?=.*(turn on|turn off|toggle)+( )+(.)+(through)).*$" );
	}

	public List<ChristmasLight> parseEntryForChristmasLightCoords(String entry){
		String cleanedUp = entry.trim().replaceAll( "(turn on|turn off|toggle|through )", "" );
		String[] splitCoordsAsString = cleanedUp.trim().split( " " );
		String[] firstChristmasLightCoords = splitCoordsAsString[0].split( "," );
		String[] secondChristmasLightCoords = splitCoordsAsString[1].split( "," );
		List<ChristmasLight> lights = new ArrayList<>();
		int christmasLight1x = Integer.parseInt( firstChristmasLightCoords[0]);
		int christmasLight1y = Integer.parseInt( firstChristmasLightCoords[1]);
		int christmasLight2x = Integer.parseInt( secondChristmasLightCoords[0]);
		int christmasLight2y = Integer.parseInt( secondChristmasLightCoords[1]);
		if(christmasLight1x <= christmasLight2x && christmasLight1y <= christmasLight2y){
			lights.add( new ChristmasLight(christmasLight1x, christmasLight1y));
			lights.add( new ChristmasLight(christmasLight2x, christmasLight2y));
		}else{
			throw new IllegalStateException("Bad coordinates - start light must have "
					+ "lower coords values than the end one!");
		}
		return lights;
	}




}
