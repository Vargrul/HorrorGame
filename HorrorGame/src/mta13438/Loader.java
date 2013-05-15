package mta13438;

import java.util.ArrayList;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.EFX10;
import org.lwjgl.opengl.Display;

public class Loader {

	static Level tutorialLevel = new Level(new ArrayList<Room>(), 0, 0, 0);
	private static Controls controls = new Controls();
	private static Player player = new Player(new Point(55,350,10),0.2f,0.01f,10);
	private static long lastFrame;
	private static int delta = getDelta();
	private static long lastFPS;
	private static int fps;
	private static int currentRoom;
	private static int tempCurrentRoom = -1;
	private static boolean renderRoom = false;
	private static boolean collision = false;
	private static long time = 0;
	private static int steps = 0;
	private static boolean enterPressed = false;
	private static int keyCD = 0;
	private static int[] xArray;
	private static int[] yArray;
	private static ArrayList testSounds = new ArrayList<Sound>(); 
	private static int randomNum;
	private static int soundNr;
	private static int soundPlayed = 0;
	private static boolean testDone = false;
	
	private static Sound walkSound = new Sound(SOUNDS.FOOTSTEP_STONE, player.getPos(), true, 0.5f);
	private static Sound walkWaterSound = new Sound(SOUNDS.FOOTSTEP_WATER, player.getPos(), true, 0.5f);
	
	//Sound for Navigation testing
	private static Sound sound1 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(10, 90, 0), true, 10.0f);
	private static Sound sound2 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(50, 60, 0), true, 10.0f);
	private static Sound sound3 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(50, 90, 0), true, 10.0f);
	private static Sound sound4 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(90, 90, 0), true, 10.0f);
	private static Sound sound5 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(10, 45, 0), true, 10.0f);
	private static Sound sound6 = new Sound(SOUNDS.FOOTSTEP_WATER, new Point(90, 10, 0), true, 10.0f);
	private static Sound currentSound;


	public void start() {
		DebugInterface.Initialize(800, 600); // Width and Length of display
		Menu mainMenu = new Menu();
		getDelta();
		lastFPS = getTime();
	}
	// Loads the tutoral level. Rooms and obstacles are added to the level.
	private static void loadTutorialLevel() {
		//Room
		tutorialLevel.addRoomList(new Room(100, 100, 20, new Point(0,15,0), new Point(110,5,0), MATERIALS.ROCK));
		//Visual position for Sounds
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(10, 90, 0),SOUNDS.WATERDROP1,true,false));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(50, 60, 0),SOUNDS.WATERDROP1,true,false));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(50, 90, 0),SOUNDS.WATERDROP1,true,false));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(90, 90, 0),SOUNDS.WATERDROP1,true,false));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(10, 45, 0),SOUNDS.WATERDROP1,true,false));
		tutorialLevel.getRoomList().get(0).addObsList(new EnvironmentObs(new Point(90, 10, 0),SOUNDS.WATERDROP1,true,false));
		
		//Locate memory for arrays
		xArray = new int[6];
		yArray = new int[6];
		
		//Adding sounds to ArrayList
		testSounds.add(sound1);
		testSounds.add(sound2);
		testSounds.add(sound3);
		testSounds.add(sound4);
		testSounds.add(sound5);
		testSounds.add(sound6);
		
		//Choose(Random) first sound to be played
		randomNum = 0 + (int)(Math.random()*6);
		soundNr = randomNum+1;
		
		
		switch(soundNr){
		case(1): System.out.println("Player start at pos 6");
		player.setPos(90, 10, 0);
		break;
		case(2): System.out.println("Player start at pos 1");
		player.setPos(10, 90, 0);
		break;
		case(3): System.out.println("Player start at pos 2");
		player.setPos(50, 60, 0);
		break;
		case(4): System.out.println("Player start at pos 3");
		player.setPos(50, 90, 0);
		break;
		case(5): System.out.println("Player start at pos 4");
		player.setPos(90, 90, 0);
		break;
		case(6): System.out.println("Player start at pos 5");
		player.setPos(10, 45, 0);
		break;
		}
		
		System.out.println("Sound " + soundNr + " Playing");
		currentSound = (Sound) testSounds.get(soundNr-1);
		currentSound.play();
		soundPlayed++;
		

		
	}
	// Initiates the tutorial level
	public static void playTutorialLevel(){
		Display.destroy();
		loadTutorialLevel();
		DebugInterface.Initialize(800, 600); // Width and Length of display
		DebugInterface.InitOpenGL(100,100); // Width and Length inside the display (Scaling of perspective here)
	}
	// Renders the tutorial level. 
	public static void renderTutorialLevel(){	
		
		input();
		
		
		

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
		
		
		
		
		
		
		
		
		
		player.setListener();

		//Draw the Tutorial Levels rooms
		tutorialLevel.Draw();

		//Draw the obs of every room
		for (int i = 0; i < tutorialLevel.getRoomList().size(); i++) {
			for (int j = 0; j < tutorialLevel.getRoomList().get(i).getObsList().size(); j++) {
				tutorialLevel.getRoomList().get(i).getObsList().get(j).draw();
			}
		}

		//Draw the player
		player.draw();
		updateFPS();
		walkCheck(player);
		
		if(enterPressed == true){
			keyCD++;
			if(keyCD>50){
				enterPressed = false;
				keyCD = 0;
			}
		}
		
		
		time++;
	}

	private static void getFinalTime() {
		System.out.println("Final time: " + time/61);
	}
	
	private static void getFinalSteps() {
		System.out.println("Total number of steps: " + steps/30);
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
		if(controls.getKEY_ENTER() && !enterPressed){
			enterPressed = true;
			currentSound.stop();
			
			if(soundPlayed>5){
				System.out.println("That was 6 sound played");
				testDone = true;
				System.out.println("Sound 1: X=" + xArray[0] +", Y=" + yArray[0]);
				System.out.println("Sound 2: X=" + xArray[1] +", Y=" + yArray[1]);
				System.out.println("Sound 3: X=" + xArray[2] +", Y=" + yArray[2]);
				System.out.println("Sound 4: X=" + xArray[3] +", Y=" + yArray[3]);
				System.out.println("Sound 5: X=" + xArray[4] +", Y=" + yArray[4]);
				System.out.println("Sound 6: X=" + xArray[5] +", Y=" + yArray[5]);
				getFinalSteps();
				getFinalTime();
				
			}
			
			if(testDone == false){
			
			soundNr++;
			if(soundNr >= 7){
				soundNr = 1;
			}
			
			
			switch(soundNr){
			case(1): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[5] = (int) player.getPos().getX();
			yArray[5] = (int) player.getPos().getY();
			break;
			case(2): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[0] = (int) player.getPos().getX();
			yArray[0] = (int) player.getPos().getY();
			break;
			case(3): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[1] = (int) player.getPos().getX();
			yArray[1] = (int) player.getPos().getY();
			break;
			case(4): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[2] = (int) player.getPos().getX();
			yArray[2] = (int) player.getPos().getY();
			break;
			case(5): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[3] = (int) player.getPos().getX();
			yArray[3] = (int) player.getPos().getY();
			break;
			case(6): 
			System.out.println("Sound " + soundNr +" Playing");
			currentSound = (Sound) testSounds.get(soundNr-1);
			xArray[4] = (int) player.getPos().getX();
			yArray[4] = (int) player.getPos().getY();
			break;
			
			}
			
			currentSound.play();
			soundPlayed++;
			
			if(soundPlayed>6){
				currentSound.stop();
			}
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
				walkWaterSound.update(player.getPos());
				steps++;
				if(walkWaterSound.isPlaying == false){
					walkWaterSound.play();
				}
				//stop normal walk
				//start water walk
			}
			else{
				walkWaterSound.stop();
				walkSound.update(player.getPos());
				steps++;
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
}
