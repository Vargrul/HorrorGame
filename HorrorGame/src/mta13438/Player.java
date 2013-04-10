package mta13438;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.openal.AL10;

public class Player extends Entity {

	private int health;

	public Player() {
		super();
		setHealth(0);
		//setListener();
	}
	public Player(Point pos, float speed, float orientation, int health) {
		super(pos, speed, orientation);
		setHealth(health);
		//setListener();
	}	

	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setListener(){ // Since only one listener will/can exist at a time, this function 
		// initializes listener values, using the position values of the object.

		//Uses getters from the entity class
		AL10.alListener3f(AL10.AL_POSITION,   getPos().getX(), getPos().getY(),getPos().getZ());
		AL10.alListenerf(AL10.AL_VELOCITY,    getSpeed());
		AL10.alListenerf(AL10.AL_ORIENTATION, getOrientation());
		//Doesn't work yet
	}

	public Boolean foward(float delta, Level level, int currentRoom) {
		Float x,y,z,minX,maxX,minY,maxY;
		List<Float> obsLocations = new ArrayList<Float>();
		Boolean returnBool = false;

		minY = level.getRoomList().get(currentRoom).getPos().getY();
		maxY = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy();
		minX = level.getRoomList().get(currentRoom).getPos().getX();
		maxX = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx();
		
		for (int i = 0; i < level.getRoomList().get(currentRoom).getObsList().size(); i++) {
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() + level.getRoomList().get(currentRoom).getObsList().get(i).getDx());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() + level.getRoomList().get(currentRoom).getObsList().get(i).getDy());
		}
		
		//Checking if the entity is inside the room boundery.
		//if(){
			if(getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) >= minX && getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) <= maxX){
				x = (float) ((getSpeed() * Math.cos(getOrientation()) * delta) + getPos().getX());
			}else{
				x = 0.0f + getPos().getX();
			}
		//}

		//Checking if the entity is inside the room boundery.
		//if(){
			if(getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) >= minY && getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) <= maxY){
				y = (float) ((getSpeed() * Math.sin(getOrientation()) * delta) + getPos().getY());
			}else{
				y = 0.0f + getPos().getY();
			}
		//}

		z = 0.0f + getPos().getZ();

		setPos(x, y, z);
		return returnBool;
	}

	public void kill(){
		//Death sequence and reposition
		//Play death sound
	}

	@Override
	public String toString() {
		return "Player [health=" + getHealth() + ", speed=" + super.getSpeed() + ", orientation=" + super.getOrientation() + "]";
	}

}
