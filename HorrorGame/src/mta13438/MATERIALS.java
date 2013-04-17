package mta13438;

public enum MATERIALS {
	//Add materials here
	ROCK(1f);
	
	//Absorption value
	private final float Abval;
	
	//Constructor
	MATERIALS(Float sb){
		this.Abval = sb;
	}
	
	//gets the absorption value
	public float getSabins(){
		return Abval;
	}
	
}
