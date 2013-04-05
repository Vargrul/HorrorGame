package mta13438;

import org.lwjgl.input.Keyboard; // allows to call different keyboard events.
import org.lwjgl.input.Mouse;

public class Controls {

	Boolean back, forward, left, right, escape; //variables to call in order to control the state of the directions.
	
	public Controls() {
		this.back = false;
		this.forward = false;
		this.left = false;
		this.right = false;
		this.escape = false;		
	}

	public void takeInput(){

		while(Keyboard.next()){ //looping through the different controls

			if(Keyboard.getEventKeyState()){ //nested if statements checking to see if buttons are pressed

				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					back = true;
					System.out.println("got KEY_DOWN");
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_UP){
					forward = true;
					System.out.println("Got KEY_UP");
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					right = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					left = true;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					escape = true;
				}

			}else{ //nested Else If to check that buttons are not pressed
				if(Keyboard.getEventKey() == Keyboard.KEY_DOWN){
					back = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_UP){
					forward = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT){
					right = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_LEFT){
					left = false;
				}
				if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
					escape = false;
				}

			}
		}
	}

	public Boolean getBack() {
		return back;
	}

	public Boolean getForward() {
		return forward;
	}

	public Boolean getLeft() {
		return left;
	}

	public Boolean getRight() {
		return right;
	}

	public Boolean getEscape() {
		return escape;
	}
}
