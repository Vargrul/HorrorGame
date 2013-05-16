package mta13438;

import java.util.ArrayList;
import java.util.List;

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
	private static ArrayList<Sound> testSounds = new ArrayList<Sound>(); 
	private static ArrayList<Sound> soundOrder = new ArrayList<Sound>(); 
	private static ArrayList visualPoints = new ArrayList<Trap>();
	private static int randomNum;
	private static int soundNr;
	private static int soundPlayed = 0;
	private static List<Point> pathPoints = new ArrayList<Point>();
	private static boolean testDone = false;
	private static double bestDistance = 0;
	private static double yourDistance = 0;
	private static ArrayList<Integer> soundNumbers = new ArrayList<Integer>();
	private static boolean displayResult = false;
	
	private static Sound walkSound = new Sound(SOUNDS.FOOTSTEP_STONE, player.getPos(), true, 0.5f);
	private static Sound walkWaterSound = new Sound(SOUNDS.FOOTSTEP_WATER, player.getPos(), true, 0.5f);
	
	//Sound for Navigation testing
	private static Sound sound1 = new Sound(SOUNDS.DOORTEST, new Point(10, 90, 0), true, 10.0f);
	private static Sound sound2 = new Sound(SOUNDS.DOORTEST, new Point(50, 60, 0), true, 10.0f);
	private static Sound sound3 = new Sound(SOUNDS.DOORTEST, new Point(50, 90, 0), true, 10.0f);
	private static Sound sound4 = new Sound(SOUNDS.DOORTEST, new Point(90, 90, 0), true, 10.0f);
	private static Sound sound5 = new Sound(SOUNDS.DOORTEST, new Point(10, 45, 0), true, 10.0f);
	private static Sound sound6 = new Sound(SOUNDS.DOORTEST, new Point(90, 10, 0), true, 10.0f);
	private static Sound currentSound;
	
	//For visual representation
	private static Trap trap1 = new Trap(new Point(10, 90, 0),5,5);
	private static Trap trap2 = new Trap(new Point(50, 60, 0),5,5);
	private static Trap trap3 = new Trap(new Point(50, 90, 0),5,5);
	private static Trap trap4 = new Trap(new Point(90, 90, 0),5,5);
	private static Trap trap5 = new Trap(new Point(10, 45, 0),5,5);
	private static Trap trap6 = new Trap(new Point(90, 10, 0),5,5);


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
		
		tutorialLevel.getRoomList().get(0).addObsList(trap1);
		tutorialLevel.getRoomList().get(0).addObsList(trap2);
		tutorialLevel.getRoomList().get(0).addObsList(trap3);
		tutorialLevel.getRoomList().get(0).addObsList(trap4);
		tutorialLevel.getRoomList().get(0).addObsList(trap5);
		tutorialLevel.getRoomList().get(0).addObsList(trap6);
		
		//Add sound numbers to arrayList
		soundNumbers.add(1);
		soundNumbers.add(2);
		soundNumbers.add(3);
		soundNumbers.add(4);
		soundNumbers.add(5);
		soundNumbers.add(6);
		
		//Adding sounds to ArrayList
		testSounds.add(sound1);
		testSounds.add(sound2);
		testSounds.add(sound3);
		testSounds.add(sound4);
		testSounds.add(sound5);
		testSounds.add(sound6);
		
		soundOrder.clear();
		
		//Choose(Random) first sound to be played
		randomNum = 0 + (int)(Math.random()*soundNumbers.size());
		soundNr = soundNumbers.get(randomNum);
		
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
		
		System.out.println("Playing Sound " + soundNr + ".");
		currentSound = (Sound) testSounds.get(soundNr-1);
		soundOrder.add(currentSound);
		currentSound.play();
		soundPlayed++;
		System.out.println("Number of sounds left: " + soundNumbers.size());
		

		
	}
	private static double getDistance(Point firstS, Point secondS) {
		double a = Math.abs((firstS.getX()-secondS.getX()));
		double b = Math.abs((firstS.getY()-secondS.getY()));
		double distance = Math.sqrt((Math.pow(a, 2)+(Math.pow(b, 2))));
		return distance;
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
		
		if(testDone == false){
			input();
		}
		
		
		
		

		collision = player.collisionCheck(tutorialLevel, currentRoom);

		if(collision){
			for (int i = 0; i < tutorialLevel.getRoomList().get(currentRoom).getObsList().size(); i++) {
				if(player.getPos().getX() > tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() && 
						player.getPos().getX() < tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() + tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getDx()){
					if(player.getPos().getY() > tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() &&
							player.getPos().getY() < tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() + tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).getDy()){
						tutorialLevel.getRoomList().get(currentRoom).getObsList().get(i).collision(player, tutorialLevel, currentRoom);
						
						
						if(soundPlayed<7){
						switch(i){
						case(1):
							if(soundNr == 1){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
								
							}
						break;
						case(2):
							if(soundNr == 2){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
							}
						break;
						case(3):
							if(soundNr == 3){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
							}
						break;
						case(4):
							if(soundNr == 4){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
							}
						break;
						case(5):
							if(soundNr == 5){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
							}
						break;
						case(6):
							if(soundNr == 6){
								//Stop the current sound
								currentSound.stop();
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									soundNumbers.remove(randomNum);
									//Randomize next sound
									randomNum = 0 + (int)(Math.random()*soundNumbers.size());
									soundNr = soundNumbers.get(randomNum);
									//Print Message
									System.out.println("Collision. Playing Sound " + soundNr + ".");
									//Set the new current sound to the randomized number from the pool
									currentSound = getNewSound();
									//Add the new current sound to the order arraylist
									soundOrder.add(currentSound);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
									System.out.println("Number of sounds left: " + soundNumbers.size());
								}
							}
						break;
						}
						
						if(soundPlayed==6){
							if(currentSound.isPlaying == false && displayResult == false ){
								displayResult = true;
								testDone = true;
								currentSound.stop();
								System.out.println("Test is Done");
								getFinalDistance();
								getFinalTime();
							}
						}
						
						}
					
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
		
		
		
			for(int j = 0; j<pathPoints.size(); j++){
			pathPoints.get(j).draw();
			
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

	private static Sound getNewSound() {
		if(soundNr ==1){
			return sound1;
		}
		if(soundNr ==2){
			return sound2;
		}
		if(soundNr ==3){
			return sound3;
		}
		if(soundNr ==4){
			return sound4;
		}
		if(soundNr ==5){
			return sound5;
		}
		if(soundNr ==6){
			return sound6;
		}
		return currentSound;
	}
	private static void getFinalTime() {
		System.out.println("Final time: " + time/61);
	}
	
	private static void getFinalDistance() {
		
		bestDistance += getDistance(soundOrder.get(0).getPos(), soundOrder.get(1).getPos());
		bestDistance += getDistance(soundOrder.get(1).getPos(), soundOrder.get(2).getPos());
		bestDistance += getDistance(soundOrder.get(2).getPos(), soundOrder.get(3).getPos());
		bestDistance += getDistance(soundOrder.get(3).getPos(), soundOrder.get(4).getPos());
		bestDistance += getDistance(soundOrder.get(4).getPos(), soundOrder.get(5).getPos());
		bestDistance += getDistance(soundOrder.get(5).getPos(), soundOrder.get(0).getPos());
		System.out.println("Best possible distance: " + bestDistance);
		
		for(int i = 0;i<pathPoints.size()-1; i++){
			yourDistance += getDistance(pathPoints.get(i), pathPoints.get(i+1));
		}
		
		System.out.println("Your distance: " + yourDistance);
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
			System.out.println("Test is over");
			System.out.println("That was 6 sound played");
			
			getFinalTime();
				
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
}
