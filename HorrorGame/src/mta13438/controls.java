package mta13438;

import org.lwjgl.input.Keyboard; // allows to call different keyboard events.
import org.lwjgl.input.Mouse;

public class controls {

	public void takeInput(){
		int back = 0, forward = 0, left = 0, right = 0, escape = 0; //variables to call in order to control the state of the directions.
		
		while(Keyboard.next()){ //looping through the different controls
			
		if(Keyboard.getEventKeyState()){ //nested if statements checking to see if buttons are pressed
		
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
			back = 1;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_UP){
			forward = 1;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
			right = 1;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
			left = 1;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
			escape = 1;
			}

		}else{ //nested Else If to check that buttons are not pressed
			if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
			back = 0;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_UP){
			forward = 0;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
			right = 0;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
			left = 0;
			}
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
			escape = 0;
			}
			
		}
	}
	
	}
}
