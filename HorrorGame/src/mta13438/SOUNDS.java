package mta13438;

public  enum SOUNDS {
	//Add sounds and path /HorrorGame/src/assest/Music/MenuBackground_1.wav
	MENU_MUSIC("assets/Music/MenuBackground_1.wav"),
	DOOR("assets/soundEffects/Door_sound.wav"),
	FOOTSTEP_STONE("assets/soundEffects/footstep_stone.wav"),
	FOOTSTEP_WATER("assets/soundEffects/footstep_water.wav"),
	GUILLOTINE_TRAP("assets/soundEffects/Guillotine_trap.wav"),
	MONSTER1("assets/soundEffects/monster1.wav"),
	MONSTER2("assets/soundEffects/monster2.wav"),
	RAT("assets/soundEffects/Rat.wav"),
	ROTER_TRAP("assets/soundEffects/Roter_trap.wav"),
	WATERDROP1("assets/soundEffects/waterdrop1.wav"),
	WATERDROP2("assets/soundEffects/waterdrop2.wav"),
	SCARE("assets/soundEffects/scare.wav"),
	GUARD("assets/soundEffects/Guard.wav"),
	PLAYERVOICE("assets/soundEffects/Protagonist.wav"),
	TRAP_DEATH("assets/soundEffects/Trap_death.wav"),
	MONSTER_DEATH("assets/soundEffects/Monster_death.wav"),
	GODOOR("assets/soundEffects/Godoor.wav")
	;
	
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
