package mta13438;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex2i;

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
	private static Sound testSound;
	
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
		
		testSound = new Sound("test", new Point(), true);
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
		
		//Set Listener values
		setListener();
		
		//Testing
		if(play == false){
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
