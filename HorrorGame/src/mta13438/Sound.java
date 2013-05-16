package mta13438;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.rtf.RTFEditorKit;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.util.WaveData;
import org.lwjgl.openal.EFX10;

// Need to include "AL.create();" when initializing

public class Sound {
	
	//Each sound have an IntBuffer to hold the sound and it's effects
	//and another IntBuffer to hold the position and speed.
	IntBuffer buffer = BufferUtils.createIntBuffer(1);
	IntBuffer source = BufferUtils.createIntBuffer(1);
	Point pos;
	boolean isPlaying = false;
	SOUNDS soundname;
	boolean looping;
	Float gain;
	Float pitch;
	long reverbDT;
	List<Sound> reverbList = new ArrayList<Sound>();
	long reverbLastTime = 0;
	long reverbStart = 0;
	
	public Sound(SOUNDS soundname, Point point, boolean looping){
		this.soundname = soundname;
		pos = point;
		this.looping = looping;
		this.gain = 1.0f;
		this.pitch = 1.0f;
		reverbDT = getTime();
		
		create();
	}
	
	public Sound(SOUNDS soundname, Point point, boolean looping, float gain, float pitch){
		this.soundname = soundname;
		pos = point;
		this.looping = looping;
		this.gain = gain;
		this.pitch = pitch;
		reverbDT = getTime();
		
		create();

	}
	
	private void create() {
		
		AL10.alGenBuffers(buffer);
		
		//Loads the wave file from this class's package in your classpath
		try {
			WaveData waveFile = WaveData.create(new BufferedInputStream(new FileInputStream(soundname.getPath())));
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
		AL10.alSourcef(source.get(0), AL10.AL_PITCH,    this.pitch          );
		AL10.alSourcef(source.get(0), AL10.AL_GAIN,     this.gain			);
		AL10.alSource3f(source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
		if(looping == true){
			AL10.alSourcei(source.get(0), AL10.AL_LOOPING,  AL10.AL_TRUE  );
		}
		
		// need killALData() and AL.destroy() before program close
	}
	
	public Point getPos(){
		return this.pos;
	}
	public SOUNDS getSoundname() {
		return soundname;
	}
	public Float getGain() {
		return gain;
	}
	public Float getPitch() {
		return pitch;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public void setSoundname(SOUNDS soundname) {
		this.soundname = soundname;
	}
	public void setGain(Float gain) {
		this.gain = gain;
	}
	public void setPitch(Float pitch) {
		this.pitch = pitch;
	}

	public void update(Point point){
		this.pos = point;
		AL10.alSource3f (source.get(0), AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
	}
	public void reverb(Room room, Player player){
		
		/*float meanRt60 = 0;
		float delay = 0;
		final float speedOfSound = 340.29f * 1000;		
		
		if(reverbStart < 1){
			reverbStart = getTime();
		}
		
		for (int i = 0; i < room.rt60.length; i++) {
			meanRt60 += room.rt60[i];
		}
		meanRt60 = meanRt60 / room.rt60.length;
		meanRt60 = meanRt60 * 10;
		
		//-----------------Test Code (commented out), not fully implemented.-----------------//
		//Calculation to the closest wall.
		if(player.getPos().getX() < room.getPos().getX() + (room.getDx() / 2) && player.getPos().getY() < room.getPos().getY() + (room.getDy() / 2)){
			float x =  Math.abs(player.getPos().getX() - room.getPos().getX());
			x = x / 10;
			float y = Math.abs(player.getPos().getY() - room.getPos().getY());
			y = y / 10;
			delay = (float) Math.sqrt(x*x+y*y) / speedOfSound;
		}
		else if(player.getPos().getX() > room.getPos().getX() + (room.getDx() / 2) && player.getPos().getY() < room.getPos().getY() + (room.getDy() / 2)){
			float x =  Math.abs(player.getPos().getX() - (room.getPos().getX() + room.getDx()));
			x = x / 10;
			float y = Math.abs(player.getPos().getY() - room.getPos().getY());
			y = y / 10;
			delay = (float) Math.sqrt(x*x+y*y) / speedOfSound;
		}
		else if(player.getPos().getX() < room.getPos().getX() + (room.getDx() / 2) && player.getPos().getY() > room.getPos().getY() + (room.getDy() / 2)){
			float x =  Math.abs(player.getPos().getX() - room.getPos().getX());
			x = x / 10;
			float y = Math.abs(player.getPos().getY() - (room.getPos().getY() + room.getDy()));
			y = y / 10;
			delay = (float) Math.sqrt(x*x+y*y) / speedOfSound;
		}
		else{
			float x =  Math.abs(player.getPos().getX() - (room.getPos().getX() + room.getDx()));
			x = x / 10;
			float y = Math.abs(player.getPos().getY() - (room.getPos().getY() + room.getDy()));
			y = y / 10;
			delay = (float) Math.sqrt(x*x+y*y) / speedOfSound;
		}
		float x = Math.abs(room.getDx() / 2);
		float y = Math.abs(room.getDy() / 2);
		delay = (float) Math.sqrt(x*x+y*y) / speedOfSound;
		delay = delay * 1000000;
		System.out.println(meanRt60);
		reverbDT = getTime() - reverbLastTime;
		
		
		if(reverbDT > delay){
			this.reverbLastTime = getTime();
			
			float gain = ((Math.abs(getTime() - reverbStart)) - meanRt60) / meanRt60;		
			System.out.println(gain);
			gain = (float) Math.log(Math.abs(gain));
			
			System.out.println(gain);
			
			
			reverbList.add(new Sound(this.soundname, this.pos, false, gain, this.pitch));
			reverbList.get(reverbList.size()-1).play();
		}
		
		if(getTime() - reverbStart > meanRt60){
			reverbStart = getTime();
		}*/
			
		
	}
	public void play(){
		if(isPlaying == false){
			AL10.alSourcePlay(source);
			isPlaying = true;
		}
	}
	public void stop(){
		AL10.alSourceStop(source);
		isPlaying = false;
	}
	public void pause(){
		AL10.alSourcePause(source);
		isPlaying = false;
	}

	public void updateGain(float gain) {
		AL10.alSourcef(source.get(0), AL10.AL_GAIN, gain);
	}
	
	//Removes the source and buffer
	public void delete(){
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
	
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	@Override
	public String toString() {
		return "Sound at: " + getPos().getX() + "," + getPos().getY();
	}
}
