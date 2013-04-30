package mta13438;

public enum MATERIALS {
	//Add materials here
	MARBLE_AND_GLAZED_TILES(0.01f,0.01f,0.01f,0.01f,0.02f,0.02f),
	BRICK(0.03f,0.03f,0.03f,0.04f,0.05f,0.07f),
	WATER(0.008f,0.008f,0.013f,0.015f,0.02f,0.025f),
	ROCK(0.01f,0.02f,0.04f,0.06f,0.08f,0.1f);
	
	//Absorption value
	private final float ac_125Hz;
	private final float ac_250Hz;
	private final float ac_500Hz;
	private final float ac_1kHz;
	private final float ac_2kHz;
	private final float ac_4kHz;
	
	//Constructor
	MATERIALS(Float a, Float b, Float c, Float d, Float e, Float f){
		this.ac_125Hz = a;
		this.ac_250Hz = b;
		this.ac_500Hz = c;
		this.ac_1kHz = d;
		this.ac_2kHz = e;
		this.ac_4kHz = f;
	}

	public float getAc_125Hz() {
		return ac_125Hz;
	}
	public float getAc_250Hz() {
		return ac_250Hz;
	}
	public float getAc_500Hz() {
		return ac_500Hz;
	}
	public float getAc_1kHz() {
		return ac_1kHz;
	}
	public float getAc_2kHz() {
		return ac_2kHz;
	}
	public float getAc_4kHz() {
		return ac_4kHz;
	}	
}
