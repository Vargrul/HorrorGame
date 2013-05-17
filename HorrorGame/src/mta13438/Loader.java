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
	private static List<Point> path1 = new ArrayList<Point>();
	private static List<Point> path2 = new ArrayList<Point>();
	private static List<Point> path3 = new ArrayList<Point>();
	private static List<Point> path4 = new ArrayList<Point>();
	private static List<Point> path5 = new ArrayList<Point>();
	private static List<Point> path6 = new ArrayList<Point>();
	private static boolean testDone = false;
	private static double bestDistance = 0;
	private static double yourDistance = 0;
	private static ArrayList<Integer> soundNumbers = new ArrayList<Integer>();
	private static ArrayList<Integer> PlayOrder= new ArrayList<Integer>();
	private static boolean displayResult = false;
	private static double distance1, distance2, distance3, distance4, distance5, distance6;
	private static double yourD1, yourD2, yourD3, yourD4, yourD5, yourD6;
	
	private static Sound walkSound = new Sound(SOUNDS.FOOTSTEP_STONE, player.getPos(), true, 0.5f);
	private static Sound walkWaterSound = new Sound(SOUNDS.FOOTSTEP_WATER, player.getPos(), true, 0.5f);
	
	//Sound for Navigation testing
	private static Sound sound1 = new Sound(SOUNDS.DOORTEST, new Point(12, 92, 0), true, 10.0f);
	private static Sound sound2 = new Sound(SOUNDS.DOORTEST, new Point(52, 62, 0), true, 10.0f);
	private static Sound sound3 = new Sound(SOUNDS.DOORTEST, new Point(52, 92, 0), true, 10.0f);
	private static Sound sound4 = new Sound(SOUNDS.DOORTEST, new Point(92, 92, 0), true, 10.0f);
	private static Sound sound5 = new Sound(SOUNDS.DOORTEST, new Point(12, 47, 0), true, 10.0f);
	private static Sound sound6 = new Sound(SOUNDS.DOORTEST, new Point(92, 12, 0), true, 10.0f);
	private static Sound reachSound = new Sound(SOUNDS.REACH, new Point(0, 0, 0), false, 10.0f);
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
		PlayOrder.add(soundNr);
		currentSound.play();
		soundPlayed++;
		

		
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
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
								
							}
						break;
						case(2):
							if(soundNr == 2){
								//Stop the current sound
								currentSound.stop();
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
							}
						break;
						case(3):
							if(soundNr == 3){
								//Stop the current sound
								currentSound.stop();
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
							}
						break;
						case(4):
							if(soundNr == 4){
								//Stop the current sound
								currentSound.stop();
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
							}
						break;
						case(5):
							if(soundNr == 5){
								//Stop the current sound
								currentSound.stop();
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
							}
						break;
						case(6):
							if(soundNr == 6){
								//Stop the current sound
								currentSound.stop();
								reachSound.isPlaying = false;
								reachSound.update(player.getPos());
								//Remove the last sound from the sound pool
								if(soundNumbers.size()!=1){
									reachSound.play();
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
									PlayOrder.add(soundNr);
									//Play the new current sound
									currentSound.play();
									//Increase number of sounds played
									soundPlayed++;
								}
							}
						break;
						}
						
						if(soundPlayed==6){
							if(currentSound.isPlaying == false && displayResult == false ){
								reachSound.play();
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
		distance1 = getDistance(soundOrder.get(0).getPos(), soundOrder.get(1).getPos());
		bestDistance += getDistance(soundOrder.get(1).getPos(), soundOrder.get(2).getPos());
		distance2 = getDistance(soundOrder.get(1).getPos(), soundOrder.get(2).getPos());
		bestDistance += getDistance(soundOrder.get(2).getPos(), soundOrder.get(3).getPos());
		distance3 = getDistance(soundOrder.get(2).getPos(), soundOrder.get(3).getPos());
		bestDistance += getDistance(soundOrder.get(3).getPos(), soundOrder.get(4).getPos());
		distance4 = getDistance(soundOrder.get(3).getPos(), soundOrder.get(4).getPos());
		bestDistance += getDistance(soundOrder.get(4).getPos(), soundOrder.get(5).getPos());
		distance5 = getDistance(soundOrder.get(4).getPos(), soundOrder.get(5).getPos());
		bestDistance += getDistance(soundOrder.get(5).getPos(), soundOrder.get(0).getPos());
		distance6 = getDistance(soundOrder.get(5).getPos(), soundOrder.get(0).getPos());
		System.out.println("Best possible distance: " + bestDistance);
		System.out.println("Distance between sound #1 and sound #2 = "+distance1);
		System.out.println("Distance between sound #2 and sound #3 = "+distance2);
		System.out.println("Distance between sound #3 and sound #4 = "+distance3);
		System.out.println("Distance between sound #4 and sound #5 = "+distance5);
		System.out.println("Distance between sound #5 and sound #6 = "+distance4);
		System.out.println("Distance between sound #6 and sound #1 = "+distance6);
		
		//Distance for full path
		for(int i = 0;i<pathPoints.size()-1; i++){
			yourDistance += getDistance(pathPoints.get(i), pathPoints.get(i+1));
		}
		System.out.println("Your distance: " + yourDistance);
		//Distance for path1
		for(int i = 0;i<path1.size()-1; i++){
			yourD1 += getDistance(path1.get(i), path1.get(i+1));
		}
		
		
		//Distance for path2
		for(int i = 0;i<path2.size()-1; i++){
			yourD2 += getDistance(path2.get(i), path2.get(i+1));
		}
		
		
		//Distance for path3
		for(int i = 0;i<path3.size()-1; i++){
			yourD3 += getDistance(path3.get(i), path3.get(i+1));
		}
		
		
		//Distance for path4
		for(int i = 0;i<path4.size()-1; i++){
			yourD4 += getDistance(path4.get(i), path4.get(i+1));
		}
		
		
		//Distance for path5
		for(int i = 0;i<path5.size()-1; i++){
			yourD5 += getDistance(path5.get(i), path5.get(i+1));
		}
		
		
		//Distance for path6
		for(int i = 0;i<path6.size()-1; i++){
			yourD6 += getDistance(path6.get(i), path6.get(i+1));
		}
		System.out.println("Player distance between sound #1 and sound #2 = "+(yourD1+10));
		System.out.println("Player distance between sound #2 and sound #3 = "+(yourD2+10));
		System.out.println("Player distance between sound #3 and sound #4 = "+(yourD3+10));
		System.out.println("Player distance between sound #4 and sound #5 = "+(yourD4+10));
		System.out.println("Player distance between sound #5 and sound #6 = "+(yourD5+10));
		System.out.println("Player distance between sound #6 and sound #1 = "+(yourD6+10));
		
		
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
				
				switch(soundPlayed){
				case(1):
					path1.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				case(2):
					path2.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				case(3):
					path3.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				case(4):
					path4.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				case(5):
					path5.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				case(6):
					path6.add(new Point(player.getPos().getX(),player.getPos().getY(),player.getPos().getZ()));
					break;
				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
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
