package mta13438;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard; // allows to call different keyboard events.

public class Controls {

	Boolean keyBack, keyForward, keyLeft, keyRight, keyEscape, keyEnter; //variables to call in order to control the state of the directions.
	
	public Controls() {
		this.keyBack = false;
		this.keyForward = false;
		this.keyLeft = false;
		this.keyRight = false;
		this.keyEscape = false;	
		this.keyEnter = false;
	}

	public void takeInput(){

		while(Keyboard.next()){ //looping through the different controls
			if(Keyboard.getEventKeyState()){ //nested if statements checking to see if buttons are pressed

				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					this.keyBack = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_UP){
					this.keyForward = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					this.keyRight = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					this.keyLeft = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					this.keyEscape = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
					this.keyEnter = true;
				}

			}else{ //nested Else If to check that buttons are not pressed
				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					this.keyBack = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_UP){
					this.keyForward = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					this.keyRight = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					this.keyLeft = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					this.keyEscape = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN){
					this.keyEnter = false;
				}

			}
		}
	}

	public Boolean getKEY_DOWN() {
		return this.keyBack;
	}
	
	public void setKEY_DOWN(boolean state) {
		this.keyBack = state;
	}

	public Boolean getKEY_UP() {
		return this.keyForward;
	}
	
	public void setKEY_UP(boolean state){
		this.keyForward = state;
	}

	public Boolean getKEY_LEFT() {
		return this.keyLeft;
	}
	public void setKEY_LEFT(boolean state){
		this.keyLeft = state;
	}

	public Boolean getKEY_RIGHT() {
		return this.keyRight;
	}
	public void setKEY_RIGHT(boolean state){
		this.keyRight = state;
	}

	public Boolean getKEY_ESCAPE() {
		return this.keyEscape;
	}
	
	public Boolean getKEY_ENTER() {
		return this.keyEnter;
	}
	
	public void setKEY_ENTER(boolean state) {
		this.keyEnter = state;
	}
	public void clearBuffer(){
		
	}
}



