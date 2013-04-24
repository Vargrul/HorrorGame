package mta13438;

public  enum SOUNDS {
	//Add sounds and path 
	HEARTBEAT("assets/heartbeat.wav"), 
	FOOTSTEPS("assets/footsteps.wav"),
	BREATHING("assets/breathing.wav"),
	TRAPSOUND("assets/trapsound.wav"),
	TRAPKILLSOUND("assets/trapkillsound.wav"),
	LOFISHORT("assets/1000Hz_1sec.wav");
	
	//desc for description
	private final String desc;
	
	//Constructor
	SOUNDS(String description){
		desc = description;
	}
	//Returns path
	public String getPath(){
		return desc;
	}
}
