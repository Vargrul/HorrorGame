package mta13438;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.lwjgl.util.WaveData;

// Need to include "AL.create();" when initializing

public class Sound {
	
	SOUNDS soundname;
	int buffer;
	int source;
	Point point;
	
	//Figured out that we needed a constructor;
	public Sound(){
		
		WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream("res" + File.separatorChar + soundname)));
        buffer = alGenBuffers();
        alBufferData(buffer, data.format, data.data, data.samplerate);
        data.dispose();
        source = alGenSources();
        alSourcei(source, AL_BUFFER, buffer);
		
	}
	
	
	
	public void reverb(){
		
	}
	public void play(){
		
	}
	public void stop(){
		
	}
	public void loop(){
		
	}
	public void delete(){
		
	}
	
	

}
