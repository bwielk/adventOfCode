public class ChristmasLightsManagementReadings {

	private int lightsLit = 0;
	private int lightsBrightness = 0;
	private final String schemaName;

	public ChristmasLightsManagementReadings( final int lightsLit, final int lightsBrightness,
			final String schemaName ) {
		this.lightsLit = lightsLit;
		this.lightsBrightness = lightsBrightness;
		this.schemaName = schemaName;
	}

	public int getLightsLit() {
		return lightsLit;
	}

	public int getLightsBrightness() {
		return lightsBrightness;
	}

	public String getSchemaName() {
		return schemaName;
	}
}
