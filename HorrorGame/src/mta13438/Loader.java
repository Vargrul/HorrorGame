package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Loader {

	static Level tutorialLevel = new Level(new ArrayList<Room>(), 0, 0, 0);
	private static Controls controls = new Controls();
	private static Player player = new Player(new Point(140,310,10),0.5f,0.01f,10);
	private static long lastFrame;
	private static int delta = getDelta();
	private static long lastFPS;
	private static int fps;
	private static int currentRoom;
	private static boolean renderRoom = false;
	private static boolean collision = false;
	private static int soundNumber = 0;
	private static boolean shortSound = true;
	
	//ArrayList for sounds
	private static ArrayList<Sound> sounds = new ArrayList<Sound>();
	
	private static ArrayList<Point> soundsPos = new ArrayList<Point>();
	//Sounds
	private static Sound testSound;
	private static Sound sound1;
	private static Sound sound2;
	private static Sound sound3;
	private static Sound sound4;
	private static Sound sound5;
	private static Sound sound6;
	private static Sound sound7;
	private static Sound sound8;
	private static Sound sound9;
	private static Sound sound10;
	
	private static boolean play = false;
	static Random generator = new Random();
	
	
	public void start() {
		DebugInterface.Initialize(800, 600); // Width and Length of display
		Menu mainMenu = new Menu();
		getDelta();
		lastFPS = getTime();
		System.out.println("This is LoFi-Test Program!");
	}
	// Loads the tutoral level. Rooms and obstacles are added to the level.
	private static void loadSounds() {
		//Sets the position of the sounds in a circle around the center.
		float f = 0.0f;
		for (int i = 0; i < 359; i++){
			soundsPos.add(new Point(50 + (float)Math.cos(f)*10,50 + (float)Math.sin(f)*10,0));
			f = (float) (f+(2*Math.PI/359));
			//System.out.println(f);
		}
		//Initializing the sounds
		testSound = new Sound("Footsteps", new Point(50,60,0), false);
		sound1 = new Sound("Footsteps", soundsPos.get(0), false);
		sound2 = new Sound("Footsteps", soundsPos.get(90), false);
		sound3 = new Sound("Footsteps", soundsPos.get(180), false);
		sound4 = new Sound("Footsteps", soundsPos.get(270), false);
		sound5 = new Sound("Footsteps", soundsPos.get(15), false);
		sound6 = new Sound("Footsteps", soundsPos.get(45), false);
		sound7 = new Sound("Footsteps", soundsPos.get(115), false);
		sound8 = new Sound("Footsteps", soundsPos.get(200), false);
		sound9 = new Sound("Footsteps", soundsPos.get(240), false);
		sound10 = new Sound("Footsteps",soundsPos.get(300), false);
		
		//Putting all sounds into the arrayList
		sounds.add(sound1);
		sounds.add(sound2);
		sounds.add(sound3);
		sounds.add(sound4);
		sounds.add(sound5);
		sounds.add(sound6);
		sounds.add(sound7);
		sounds.add(sound8);
		sounds.add(sound9);
		sounds.add(sound10);
		
		//Sets the position of the sounds in a circle around the center.
		/*float f = 0.0f;
		for (int i = 0; i < 359; i++){
			soundsPos.add(new Point(50 + (float)Math.cos(f)*20,50 + (float)Math.sin(f)*20,0));
			f = (float) (f+(2*Math.PI/359));
			System.out.println(f);
		}
		sounds.get(0).setPos(soundsPos.get(0));
		sounds.get(1).setPos(soundsPos.get(90));
		sounds.get(2).setPos(soundsPos.get(180));
		sounds.get(3).setPos(soundsPos.get(270));
		sounds.get(4).setPos(soundsPos.get(10));
		sounds.get(5).setPos(soundsPos.get(30));
		sounds.get(6).setPos(soundsPos.get(60));
		sounds.get(7).setPos(soundsPos.get(200));
		sounds.get(8).setPos(soundsPos.get(220));
		sounds.get(9).setPos(soundsPos.get(300));*/
		
		//Set Listener values
		setListener();
	}
	// Initiates the tutorial level
	public static void initialize(){
		Display.destroy();
		loadSounds();
		//0,0 is now in the middle of the screen 
		initDisplay(800, 800);
		// Width and Length inside the display (Scaling of perspective here)
		initOpenGL(100,100);
	}
	// Renders the tutorial level. 
	public static void render(){
		
		//Check for keyboard input
		input();
		
		//Draw listener
		glColor3f(0.0f, 0.0f, 1.0f);
		glPointSize(25);
		glBegin(GL_POINTS);
			glVertex2f(50,50);
		glEnd();
				
		//Draw Nose/Direction of listener
		glPointSize(10);
		glBegin(GL_POINTS);
			glVertex2f(0,0.05f);
		glEnd();
		
		//Testing
		/*if(testSound.playingCheck()==false){
			testSound.play();
			play = true;
		}*/
		for (int i = 0; i < 10; i++){
		sounds.get(i).draw();
		if(sounds.get(i).isSelected == true && sounds.get(i).playingCheck() == false){
			sounds.get(i).play();
			play = true;
		}
		}
		//testSound.draw();
		updateFPS();
	}
	
	private static void nextSound(){
		for(int i = 0; i < sounds.size(); i++){
			sounds.get(i).isSelected = false;
		}
		int randomIndex = generator.nextInt( 10 );
		if(soundNumber == 0){
			if(testSound.playingCheck()==false){
				testSound.play();
				play = true;
				soundNumber++;
				System.out.println("Test Sound");
			}
		}else if(soundNumber <= 10){
			if(sounds.get(randomIndex).hasBeenPlayed == false){
				sounds.get(randomIndex).isSelected = true;
				sounds.get(randomIndex).hasBeenPlayed = true;
				System.out.println(soundNumber + ": sound" +(randomIndex+1));
				soundNumber++;
				
			} else {
				nextSound();
			}
		} else {
			System.out.println("End of test");
		}
	}

	private static void setListener() {
		//FloatBuffers holds values
		FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind(); //Position of the listener.
		FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind(); //Velocity of the listener.
		FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind(); //Orientation of the listener.
		
		//Setting values
		AL10.alListener3f(AL10.AL_POSITION,    50,50,0);
		AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
		
	}
	public static void input() {

		controls.takeInput();	

		delta = getDelta();

		if(controls.getKEY_UP()){
			//Up Event
			if(soundNumber == 0 && shortSound == true){
				System.out.println("Long sound selected");
				shortSound = false;
				controls.setKEY_UP(false);
			} else if (soundNumber == 0 && shortSound == false){
				System.out.println("Short sound selected");
				shortSound = true;
				controls.setKEY_UP(false);
			}
		}
		if(controls.getKEY_DOWN()){
			//Down Event
		}
		if(controls.getKEY_LEFT()){
			//Left Event
		}
		if(controls.getKEY_RIGHT()){
			//Right Event
		}
		if(controls.getKEY_ENTER()){
			nextSound();
			controls.setKEY_ENTER(false);
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
	public static void initOpenGL(int x, int y){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, x, 0, y, 1, -1); //Sets number of units from bottom to top and left to right.
		glMatrixMode(GL_MODELVIEW);
	}
	public static void initDisplay(int X, int Y){
		try {
			Display.setDisplayMode(new DisplayMode(X, Y)); //DisplayMode
			Display.setTitle("MTA13438, P4"); //Title
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS = getTime();
		}
		fps++;
	}
}
