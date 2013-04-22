package mta13438;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DebugInterface {

	//Length and Width for the Display
	public static int DisplayLength;
	public static int DisplayWidth;
	public static int WidthUnits;
	public static int LengthUnits;

	//Takes in the wished Length and Width for the Display
	public static void Initialize(int Length, int Width){
		DisplayLength = Length;
		DisplayWidth = Width;
		try {
			Display.setDisplayMode(new DisplayMode(DisplayLength, DisplayWidth)); //DisplayMode
			Display.setTitle("MTA13438, P4"); //Title
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void InitOpenGL(int width, int length){
		WidthUnits = width;
		LengthUnits = length;
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(WidthUnits/2, WidthUnits/2, LengthUnits/2, LengthUnits/2, 1, -1); //Sets number of units from bottom to top and left to right.
		glMatrixMode(GL_MODELVIEW);
		
	}
	public static void InitOpenAL(){
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.out.println("Failed to initialize OpenAL.");
		}
	}

	public static void Terminate(){
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
}