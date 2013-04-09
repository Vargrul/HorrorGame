package mta13438;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

// Need to include "AL.create();" when initializing

public class Sound {
	
	//Each sound have an IntBuffer to hold the sound and it's effects
	//and another IntBuffer to hold the position and s
	IntBuffer buffer = BufferUtils.createIntBuffer(1);
	IntBuffer source = BufferUtils.createIntBuffer(1);
	Point pos;
	
	//Figured out that we needed a constructor;
	public Sound(SOUNDS soundname, Point point, Float gain, Float pitch){
	
		AL10.alGenBuffers(buffer);
		
		//Set Position 
		pos = point;
		
		//Loads the wave file from this class's package in your classpath
		WaveData waveFile = null;
		try {
			waveFile = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar + soundname)));
		} catch (FileNotFoundException e) {
			System.out.println("File could not be loaded from Classpath");
			e.printStackTrace();
		}
		
		//Puts Sound data in buffer and disposes afterwards
		AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
		
		//Bind buffer and source
		AL10.alGenSources(source);
		
		AL10.alSourcei(source.get(0), AL10.AL_BUFFER,   buffer.get(0) );
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    pitch          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     gain          );
		AL10.alSource3f(source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
		//AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel     );
		//Velocity gives problems atm. 
		
		//Setup of Listener Values now happens in player.java class
		
		
	}// need killALData() and AL.destroy() before program close
	
	//Update function for updating position, pitch and gain
	public void update(Point point, float pitch, float gain){
		this.pos = point;
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    pitch          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     gain          );
		AL10.alSource3f (source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
	}
	public void reverb(){
		//Do reverb effect
	}
	public void play(){
		AL10.alSourcePlay(source);
	}
	public void stop(){
		AL10.alSourceStop(source);
	}
	public void pause(){
		AL10.alSourcePause(source);
	}
	public void loop(){
		AL10.alSourcei(source.get(0), AL10.AL_LOOPING,  AL10.AL_TRUE  );
		AL10.alSourcePlay(source);
	}
	public Point getPos(){
		return this.pos;
	}
	
	//Change the position of a sound object. 
	//Should be performed each time the object from the sound gets
	public void setPos(Point point){
		this.pos = point;
		AL10.alSource3f (source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
	}
	
	//Removes the source and buffer
	public void delete(){
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
}
