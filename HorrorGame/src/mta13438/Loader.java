package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.Display;

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
	
	//ArrayList for sounds
	private static ArrayList<Sound> sounds = new ArrayList<Sound>();
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
	
	
	public void start() {
		DebugInterface.Initialize(800, 600); // Width and Length of display
		Menu mainMenu = new Menu();
		getDelta();
		lastFPS = getTime();
		System.out.println("This is LoFi-Test Program!");
	}
	// Loads the tutoral level. Rooms and obstacles are added to the level.
	private static void loadSounds() {
		
		//Initializing the sounds
		testSound = new Sound("Footsteps", new Point(), true);
		sound1 = new Sound("Footsteps", new Point(), false);
		sound2 = new Sound("Footsteps", new Point(), false);
		sound3 = new Sound("Footsteps", new Point(), false);
		sound4 = new Sound("Footsteps", new Point(), false);
		sound5 = new Sound("Footsteps", new Point(), false);
		sound6 = new Sound("Footsteps", new Point(), false);
		sound7 = new Sound("Footsteps", new Point(), false);
		sound8 = new Sound("Footsteps", new Point(), false);
		sound9 = new Sound("Footsteps", new Point(), false);
		sound10 = new Sound("Footsteps", new Point(), false);
		
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
		
		//Set Listener values
		setListener();
		
	}
	// Initiates the tutorial level
	public static void initialize(){
		Display.destroy();
		loadSounds();
		//0,0 is now in the middle of the screen 
		DebugInterface.Initialize(600, 600); // Width and Length of display
		DebugInterface.InitOpenGL(600, 600); // Width and Length inside the display (Scaling of perspective here)
	}
	// Renders the tutorial level. 
	public static void render(){
		
		//Check for keyboard input
		input();
		
		//Draw listener
		glColor3f(0.0f, 0.0f, 1.0f);
		glPointSize(25);
		glBegin(GL_POINTS);
			glVertex2f(0,0);
		glEnd();
				
		//Draw Nose/Direction of listener
		glPointSize(25);
		glBegin(GL_POINTS);
			glVertex2f(0,1);
		glEnd();
		
		//Testing
		if(testSound.playingCheck()==false){
			testSound.play();
			play = true;
		}
				
		testSound.draw();
		updateFPS();
	}

	private static void setListener() {
		//FloatBuffers holds values
		FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind(); //Position of the listener.
		FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind(); //Velocity of the listener.
		FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind(); //Orientation of the listener.
		
		//Setting values
		AL10.alListener(AL10.AL_POSITION,    listenerPos);
		AL10.alListener(AL10.AL_VELOCITY,    listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
		
	}
	public static void input() {

		controls.takeInput();	

		delta = getDelta();

		if(controls.getKEY_UP()){
			//Up Event
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
}
