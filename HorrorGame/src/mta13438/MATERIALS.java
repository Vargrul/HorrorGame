package mta13438;

public enum MATERIALS {
	//Add materials here
	DOOR___SOLID_WOOD_PANELS(0.1f,0.07f,0.05f,0.04f,0.04f,0.04f),
	MARBLE_AND_GLAZED_TILES(0.01f,0.01f,0.01f,0.01f,0.02f,0.02f),
	BRICK(0.03f,0.03f,0.03f,0.04f,0.05f,0.07f),
	WATER(0.008f,0.008f,0.013f,0.015f,0.02f,0.025f),
	ROCK(0.01f,0.02f,0.04f,0.06f,0.08f,0.1f);
	
	//Absorption value
	private final float[] ac = new float[6];
	
	//Constructor
	private MATERIALS(Float a, Float b, Float c, Float d, Float e, Float f){
		this.ac[0] = a;
		this.ac[1] = b;
		this.ac[2] = c;
		this.ac[3] = d;
		this.ac[4] = e;
		this.ac[5] = f;
	}

	public float[] getAc() {
		return ac;
	}
}
