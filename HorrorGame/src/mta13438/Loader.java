package mta13438;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCcontext;
import org.lwjgl.openal.ALCdevice;
import org.lwjgl.openal.EFX10;
import org.lwjgl.opengl.Display;

public class Loader {

	private static float speedOfSound = 340.29f;

	static Level tutorialLevel = new Level(new ArrayList<Room>(), 0, 0, 0);
	private static Controls controls = new Controls();
	private static Player player = new Player(new Point(21,315,10),0.2f,0.01f,10);
	private static Point playerPos = new Point(0,0,0);
	private static long lastFrame;
	private static int delta = getDelta();
	private static long lastFPS;
	private static int fps;
	private static int currentRoom;
	private static int tempCurrentRoom = -1;
	private static boolean renderRoom = false;
	private static boolean collision = false;
	private static boolean playStartSequence = true;
	private static boolean dI = false;
	private static boolean takeInput = true;
	private static boolean playSounds = true;
	private static long startTime;
	private static long time;
	private static int counter;
	private static Event scareEvent = new Event(new Point(100, 0, 10), 20, 90, 0, MATERIALS.WATER);
	private static Entity guard = new Entity(new Point(25,315,10),0.2f,(float)Math.PI);
	private static double distance = 0;
	private static boolean pOutput = false;

	private static Sound guardVoice = new Sound(SOUNDS.GUARD, player.getPos(), false, true, 10.0f);
	private static Sound playerVoice = new Sound(SOUNDS.PLAYERVOICE, player.getPos(), false, true, 10.0f);
	private static Sound openDoorSound = new Sound(SOUNDS.GODOOR,new Point (0,0,0), false, true);
	private static Sound trapDeathSound = new Sound(SOUNDS.TRAP_DEATH,new Point (0,0,0), false, true, 0.5f);
	private static Sound monsterDeathSound = new Sound(SOUNDS.MONSTER_DEATH,new Point (0,0,0), false, true, 0.5f);
	private static Sound winMusic = new Sound(SOUNDS.MENU_MUSIC,new Point (230,320,0), true, true, 10000.0f);

	private static Sound walkSound = new Sound(SOUNDS.FOOTSTEP_STONE, player.getPos(), true, true);
	private static Sound walkWaterSound = new Sound(SOUNDS.FOOTSTEP_WATER, player.getPos(), true, true);
	private static boolean playing = false;

	private static List<Point> pathPoints = new ArrayList<Point>();

	final static int effectSlot = EFX10.alGenAuxiliaryEffectSlots();
	final static int reverbEffect = EFX10.alGenEffects();

	public void start() {
		DebugInterface.Initialize(800, 600); // Width and Length of display
		Menu mainMenu = new Menu();
		getDelta();
		lastFPS = getTime();
	}

	private static void loadTutorialLevel() {
		//Creates all the room and obs needed for the tutorial level.
		tutorialLevel.addRoomList(new Room(10, 20, 20, new Point(0,5,10), new Point(10, 15, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(60, 50, 30, new Point(0,25,10), new Point(60, 5, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(150, 90, 60, new Point(0,45,10), new Point(150, 65, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(110, 110, 40, new Point(0,55,10), new Point(90, 0, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(70, 70, 40, new Point(35,70,10), new Point(35,0, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(50, 80, 40, new Point(25,80,10), new Point(25, 0, 10), MATERIALS.ROCK));
		tutorialLevel.addRoomList(new Room(30, 20, 40, new Point(15,20,10), new Point(15, 20, 10), MATERIALS.ROCK));
		tutorialLevel.getRoomList().get(2).addObsList(new Water(new Point(20, 20, 0), 20, 50, 0, MATERIALS.WATER));
		tutorialLevel.getRoomList().get(2).addObsList(scareEvent);
		tutorialLevel.getRoomList().get(3).addObsList(new EnvironmentObs(new Point(40, 70, 0),SOUNDS.RAT,false,true, 5.0f));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(0, 0, 0),SOUNDS.RAT,false,true, 5.0f));
		tutorialLevel.getRoomList().get(5).addObsList(new EnvironmentObs(new Point(5, 20, 0),SOUNDS.RAT,false,true, 5.0f));
		tutorialLevel.getRoomList().get(2).addObsList(new EnvironmentObs(new Point(40, 80, 0),SOUNDS.WATERDROP2,true,true, 0.1f));
		tutorialLevel.getRoomList().get(2).addObsList(new EnvironmentObs(new Point(110, 30, 0),SOUNDS.WATERDROP1,true,true, 0.1f));
		tutorialLevel.getRoomList().get(5).addObsList(new Monster(new Point(20, 20, 10), 20, 20, 0, MATERIALS.ROCK,SOUNDS.MONSTER1));
		tutorialLevel.getRoomList().get(3).addObsList(new Monster(new Point(40, 25, 10), 20, 20, 0, MATERIALS.ROCK,SOUNDS.MONSTER2));
		tutorialLevel.getRoomList().get(4).addObsList(new Trap(new Point(20, 20, 10), 30, 30, 0, MATERIALS.ROCK));
		tutorialLevel.autoLevelGenerator(new Point(10,300,0));
		System.out.println("Loaded level.");
	}

	public static void playTutorialLevel(){
		if(dI == true){
			Display.destroy();
		}

		loadTutorialLevel();
		initializeReverb();

		if(dI == true){
			DebugInterface.Initialize(800, 600); // Width and Length of display
		}

		DebugInterface.InitOpenGL(500,500); // Width and Length inside the display (Scaling of perspective here)
	}

	public static void renderTutorialLevel(){	
		//The intro sequence.
		intro_Sequence();

		//Takes and reacts to inputs.
		if(takeInput)	input();

		//Draws what is needed and not needed.
		player.setListener();
		player.draw();
		tutorialLevel.draw();
		drawAtFinish();
		for(int j = 0; j<pathPoints.size(); j++){
			pathPoints.get(j).draw();
		}


		//updates the Reverb and checks for collisions.
		updateReverb();
		collisionCheck();

		//Plays the sounds that needs to be played
		soundPlayPrRoom();
		playDeathSounds();
		playRoomEnterSound();
		walkCheck(player);

		//And last updates the FPS cunter.
		updateFPS();
	}
	
	public static void intro_Sequence() {
		if(playStartSequence == true){
			if(time < (startTime+25800)){
				takeInput = false;
				playSounds = false;
				if(counter == 0){
					startTime = getTime();
					counter++;
				}
				guard.draw();
				guardVoice.update(guard.getPos());
				guardVoice.play();
				playerVoice.play();
			} else if (time >= (startTime+25800) && time < (startTime+29600)){
				guard.backward(0.7f);
				guard.turnRight(0.2f);
				guard.draw();
				guardVoice.update(guard.getPos());
			} else if (time >= (startTime+39500) && time < (startTime+48000)){
				takeInput = true;
				player.setSpeed(0.0f);
			} else if (time >= (startTime+50000) && time < (startTime+56000)){
				playSounds = true;
			} else if (time >= (startTime+56000)){
				playStartSequence = false;
				takeInput = true;
				player.setSpeed(0.2f);
				counter = 0;
			}
			time = getTime();
		}
	}
	public static void input() {

		controls.takeInput();	

		currentRoom = tutorialLevel.getCurrentRoom(player.getPos());
		delta = getDelta();

		if(controls.getKEY_UP()){
			player.foward(delta/10, tutorialLevel, currentRoom);
		}
		if(controls.getKEY_DOWN()){
			player.backward(delta/10, tutorialLevel, currentRoom);
		}
		if(controls.getKEY_LEFT()){
			player.turnLeft(delta/10);
		}
		if(controls.getKEY_RIGHT()){
			player.turnRight(delta/10);
		}
	}
	public static void collisionCheck() {
		collision = player.collisionCheck(tutorialLevel, currentRoom);

		if(collision){
			for (int i = 0; i < tutorialLevel.getRoomList().get(currentRoom).getObsList().size(); i++) {
				if(player.getPos().getX() > tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() && 
						player.getPos().getX() < tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() + tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getDx()){
					if(player.getPos().getY() > tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() &&
							player.getPos().getY() < tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() + tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getDy()){
						tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).collision(player, tutorialLevel, currentRoom);
					}
				}
			}
		}
	}
	public static void soundPlayPrRoom() {
		// Plays all the sounds from objects in the current room.
		for (int i = 0; i < tutorialLevel.getRoomList().size(); i++){
			if(tutorialLevel.getRoomList().get(i) != tutorialLevel.getRoomList().get(currentRoom)){
				for(int j = 0; j < tutorialLevel.getRoomList().get(i).getObsList().size(); j++){
					tutorialLevel.getRoomList().get(i).getObsList().get(j).getLoopSound().stop();
				}
			} 
		}
		if(playSounds == true){
			for (int i = 0; i < tutorialLevel.getRoomList().get(currentRoom).getObsList().size(); i++) {
				if (tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getEmitSound() == true && currentRoom > 0 && currentRoom < 6){
					tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getLoopSound().play();
				}
			}
		} else {
			for (int i = 0; i < tutorialLevel.getRoomList().size(); i++){	
				for(int j = 0; j < tutorialLevel.getRoomList().get(i).getObsList().size(); j++){
					tutorialLevel.getRoomList().get(i).getObsList().get(j).getLoopSound().stop();
				} 
			}
		}

		//Scare event in room 2
		if(currentRoom == 2){
			if(scareEvent.isTrigger() == true && scareEvent.isActive() == true){
				scareEvent.getScareSound().update(new Point(scareEvent.getPos().getX(),scareEvent.getPos().getY() + scareEvent.getDy(),0));
				scareEvent.getScareSound().play();
				scareEvent.setActive(false);
			}
		} else {
			scareEvent.getScareSound().stop();
		}
	}
	public static void playDeathSounds() {
		// play trap death sound
		if(tutorialLevel.getTrapDeath() == true){
			if(counter == 0){
				startTime = getTime();
				counter++;
			}
			time = getTime();
			if(time < startTime +5000){
				playSounds = false;
				takeInput = false;
				trapDeathSound.update(player.getPos());
				trapDeathSound.play();
			} else if(time <= startTime + 5500){
				trapDeathSound.stop();
				player.setSpeed(0.0f);
			} else if(time > startTime + 5500){
				counter = 0;
				player.setSpeed(0.2f);
				tutorialLevel.setTrapDeath(false);
				tutorialLevel.setGoThroughDoor(true);
				player.kill(tutorialLevel);
			}
		}
		//play monster death sound
		if(tutorialLevel.getMonsterDeath() == true){
			if(counter == 0){
				startTime = getTime();
				counter++;
			}
			time = getTime();
			if(time < startTime +5000){
				playSounds = false;
				takeInput = false;
				monsterDeathSound.update(player.getPos());
				monsterDeathSound.play();
			} else if(time <= startTime + 5500){
				monsterDeathSound.stop();
				player.setSpeed(0.0f);
			} else if(time > startTime + 5500){
				counter = 0;
				player.setSpeed(0.2f);
				tutorialLevel.setMonsterDeath(false);
				tutorialLevel.setGoThroughDoor(true);
				player.kill(tutorialLevel);
			}
		}
	}
	public static void playRoomEnterSound() {
		// play close door sound when entering new room
		if(tutorialLevel.getGoThroughDoor() == true){
			if(counter == 0){
				startTime = getTime();
				counter++;
			}
			time = getTime();
			if(time < startTime +5000){
				playSounds = false;
				takeInput = false;
				openDoorSound.update(player.getPos());
				openDoorSound.play();
			} else if(time <= startTime + 5500){
				openDoorSound.stop();
				takeInput = true;
				playSounds = true;
				player.setSpeed(0.0f);
			} else if(time > startTime + 5500){
				counter = 0;
				player.setSpeed(0.2f);
				tutorialLevel.setGoThroughDoor(false);
			}
		}
	}
	public static void drawAtFinish() {
		if(currentRoom == 6){
			winMusic.play();
			getDistance();
			if(counter == 0){
				Display.destroy();
				DebugInterface.Initialize(800, 600); // Width and Length of display
				DebugInterface.InitOpenGL(500,500); // Width and Length inside the display (Scaling of perspective here)
				counter++;
			}
		}
	}

	public static void initializeReverb() {
		EFX10.alEffecti(reverbEffect, EFX10.AL_EFFECT_TYPE, EFX10.AL_EFFECT_REVERB);
		AL10.alListenerf(EFX10.AL_METERS_PER_UNIT, 0.10f);
		EFX10.alEffectf(reverbEffect, EFX10.AL_REVERB_GAIN, 0.32f);
		EFX10.alAuxiliaryEffectSloti(effectSlot, EFX10.AL_EFFECTSLOT_EFFECT, reverbEffect);

		for (int i = 0; i < tutorialLevel.getRoomList().get(currentRoom).obsList.size(); i++) {
			tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getLoopSound().loadReverb(effectSlot);
		}

		guardVoice.loadReverb(effectSlot);
		walkSound.loadReverb(effectSlot);
		playerVoice.loadReverb(effectSlot);
		walkWaterSound.loadReverb(effectSlot);
		openDoorSound.loadReverb(effectSlot);
		trapDeathSound.loadReverb(effectSlot);
		monsterDeathSound.loadReverb(effectSlot);
	}
	public static void updateReverb() {

		if(tempCurrentRoom != currentRoom){
			tempCurrentRoom = currentRoom;
			float decayTime, HFRatio, er;
			float temp = 0;
			float[] rt60 = tutorialLevel.getRoomList().get(currentRoom).getRt60();

			for (int i = 0; i < rt60.length; i++) {
				temp += rt60[i];
			}
			decayTime = temp / rt60.length;
			decayTime = decayTime / 100;

			temp = (rt60[0] + rt60[1]) / 2;
			HFRatio = ((rt60[4] + rt60[5]) / 2) / temp;
			HFRatio = HFRatio + 1;

			temp = tutorialLevel.getRoomList().get(currentRoom).getDx() / 2;
			if(tutorialLevel.getRoomList().get(currentRoom).getDy()/2 < temp){
				temp = tutorialLevel.getRoomList().get(currentRoom).getDy()/2;
			}
			er = (temp / 10) / speedOfSound;

			EFX10.alEffectf(reverbEffect, EFX10.AL_REVERB_DECAY_TIME, decayTime);
			EFX10.alEffectf(reverbEffect, EFX10.AL_REVERB_DECAY_HFRATIO, HFRatio);
			EFX10.alEffectf(reverbEffect, EFX10.AL_REVERB_REFLECTIONS_DELAY, er);
			//EFX10.alEffectf(reverbEffect, EFX10.AL_REVERB_LATE_REVERB_DELAY, 0.1f);
			EFX10.alAuxiliaryEffectSloti(effectSlot, EFX10.AL_EFFECTSLOT_EFFECT, reverbEffect);

			for (int i = 0; i < tutorialLevel.getRoomList().get(currentRoom).obsList.size(); i++) {
				tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getLoopSound().loadReverb(effectSlot);
			}
		}
	}

	public static int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS = getTime();
		}
		fps++;
	}
	public static void walkCheck(Player player){
		if(player.isWalking() == true){
			if(player.isInWater()==true){
				walkSound.stop();
				walkWaterSound.update(new Point(player.getPos().getX(),player.getPos().getY(),0));
				pathPoints.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
				if(walkWaterSound.isPlaying == false){
					walkWaterSound.play();
				}
				//stop normal walk
				//start water walk
			}
			else{
				walkWaterSound.stop();
				walkSound.update(new Point(player.getPos().getX(),player.getPos().getY(),0));
				pathPoints.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
				walkSound.play();
				//stop water walk
				//Play normal walk
			}	
		} else {
			walkWaterSound.stop();
			walkSound.stop();
		}
		//Setting the Walking and inWater bools to true to check
		player.setWalking(false);
		player.setInWater(false);
	}
	
	private static double calDistance(Point firstS, Point secondS) {
		double a = Math.abs((firstS.getX()-secondS.getX()));
		double b = Math.abs((firstS.getY()-secondS.getY()));
		double distance = Math.sqrt((Math.pow(a, 2)+(Math.pow(b, 2))));
		return distance;
	}
	
	private static void getDistance(){
		//Distance for full path
		if(pOutput == false){
			for(int i = 0;i<pathPoints.size()-1; i++){
				distance += calDistance(pathPoints.get(i), pathPoints.get(i+1));
			}
			pOutput = true;
			System.out.println("Distance: " + distance);
		}
	}
}
