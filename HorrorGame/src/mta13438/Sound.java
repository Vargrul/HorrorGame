package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

// Need to include "AL.create();" when initializing

public class Sound {
	
	//Each sound have an IntBuffer to hold the sound and it's effects
	//and another IntBuffer to hold the position and speed.
	IntBuffer buffer = BufferUtils.createIntBuffer(1);
	IntBuffer source = BufferUtils.createIntBuffer(1);
	Point pos;
	boolean isPlaying = false;
	boolean isSelected = false;
	
	//Figured out that we needed a constructor;
	public Sound(String soundname, Point point, boolean looping){
		AL10.alGenBuffers(buffer);
		
		//Set Position 
		pos = point;
		
		//Loads the wave file from this class's package in your classpath
		try {
			WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("assets" + File.separatorChar + "Footsteps.wav")));
			AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
			waveFile.dispose();
		} catch (FileNotFoundException e) {
			System.out.println("File could not be loaded from Classpath");
			e.printStackTrace();
		}
		
		
		
		//Generate a source
		AL10.alGenSources(source);
		
		//Bind buffer and source
		AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    1.0f          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     1.0f          );
		AL10.alSource3f(source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
		if(looping == true){
			AL10.alSourcei(source.get(0), AL10.AL_LOOPING,  AL10.AL_TRUE  );
		}
		
	}// need killALData() and AL.destroy() before program close
	
	public void play(){
		AL10.alSourcePlay(source);
	}
	public void draw(){
		glColor3f(1.0f, 0.0f, 0.0f);
		glPointSize(20);
		glBegin(GL_POINTS);
		glVertex2i(0,0);
		glEnd();
	}
	public void stop(){
		AL10.alSourceStop(source);
		isPlaying = false;
	}
	public void pause(){
		AL10.alSourcePause(source);
		isPlaying = false;
	}
	public Point getPos(){
		return this.pos;
	}
	public boolean checkSelect(){
		return isSelected;
	}
	public boolean playingCheck(){
		return isPlaying;
	}
	//Removes the source and buffer
	public void delete(){
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
	
	@Override
	public String toString() {
		return "Sound at: " + getPos().getX() + "," + getPos().getY();
	}
}
