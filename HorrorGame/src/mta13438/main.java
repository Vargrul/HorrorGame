package mta13438;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class main {
	public static void main(String[] args) {
		
		try {
			AL.create("OpenAL Soft", 44100, 60, false,true);
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Loader loader = new Loader();
   	 	loader.start();
	}
}

