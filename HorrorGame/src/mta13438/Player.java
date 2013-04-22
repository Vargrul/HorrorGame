package mta13438;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

public class Player extends Entity {

	private int health;
	private boolean walking = false;

	//Extends no args constructor of Obs
	public Player() {
		super();
		setHealth(0);
		setListener();
	}

	//Extends constructor of Obs
	public Player(Point pos, float speed, float orientation, int health) {
		super(pos, speed, orientation);
		setHealth(health);
		setListener();
	}	

	//Getters and Setters
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isWalking(){
		return walking;
	}
	public void setWalking(boolean w){
		this.walking = w;
	}
	public void setListener(){ 

		float x, y;

		//Calculating the heading vector point from the orientation.
		x = (float) (Math.cos(getOrientation()));
		y = (float) (Math.sin(getOrientation()));

		//The values in this float buffer is in relation to the AL_POSITION.
		FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { x, y, 1, 0, 0, 1});
		listenerOri.flip();

		//Setting the alListener's position and orientation.
		AL10.alListener3f(AL10.AL_POSITION,   getPos().getX(), getPos().getY(), getPos().getZ());
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}

	public void foward(float delta, Level level, int currentRoom) {
		Float x,y,z,minX,maxX,minY,maxY;		

		minY = level.getRoomList().get(currentRoom).getPos().getY();
		maxY = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy();
		minX = level.getRoomList().get(currentRoom).getPos().getX();
		maxX = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx();

		walking = true;
		//Checking if the entity is inside the room boundery.
		if(getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) >= minX && getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) <= maxX){
			x = (float) ((getSpeed() * Math.cos(getOrientation()) * delta) + getPos().getX());
		}else{
			//Moving the payer 0.01f from the wall
			if(getSpeed() * Math.cos(getOrientation()) * delta > 0){
				x = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx() - 0.01f;
			}else{
				x = level.getRoomList().get(currentRoom).getPos().getX() + 0.01f;
			}
		}

		//Checking if the entity is inside the room boundery.
		if(getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) >= minY && getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) <= maxY){
			y = (float) ((getSpeed() * Math.sin(getOrientation()) * delta) + getPos().getY());
		}else{
			//Moving the payer 0.01f from the wall
			if(getSpeed() * Math.sin(getOrientation()) * delta > 0){
				y = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy() - 0.01f;
			}else{
				y = level.getRoomList().get(currentRoom).getPos().getY() + 0.01f;
			}
		}

		z = 0.0f + getPos().getZ();

		//Checking if the new X,Y,Z is equal to the old X,Y,Z. If so then object haven't be walking.
		if(getPos().equals(new Point(x,y,z)) == true){
			walking = false;
		}

		setPos(x, y, z);
	}

	public void backward(float delta, Level level, int currentRoom) {
		float x,y,z,minX,maxX,minY,maxY;
		List<Float> obsList = new ArrayList<Float>();

		minY = level.getRoomList().get(currentRoom).getPos().getY();
		maxY = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy();
		minX = level.getRoomList().get(currentRoom).getPos().getX();
		maxX = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx();

		for (int i = 0; i < obsList.size(); i++) {

		}
		walking = true;
		//Checking if the entity is inside the room boundery.
		if(getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta)) >= minX && getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta)) <= maxX){
			x = getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta));
		}else{
			//Moving the payer 0.01f from the wall
			if(getSpeed() * Math.cos(getOrientation()) * delta < 0){
				x = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx() - 0.01f;
			}else{
				x = level.getRoomList().get(currentRoom).getPos().getX() + 0.01f;
			}
		}

		//Checking if the entity is inside the room boundery.
		if(getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta)) >= minY && getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta)) <= maxY){
			y = getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta));
		}else{
			//Moving the payer 0.01f from the wall
			if(getSpeed() * Math.sin(getOrientation()) * delta < 0){
				y = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy() - 0.01f;
			}else{
				y = level.getRoomList().get(currentRoom).getPos().getY() + 0.01f;
			}
		}

		z = getPos().getZ() - 0.0f;

		//Checking if the new X,Y,Z is equal to the old X,Y,Z. If so then object haven't be walking.
		if(getPos().equals(new Point(x,y,z)) == true){
			walking = false;
		}

		setPos(x, y, z);
	}

	public boolean collisionCheck(Level level, int currentRoom) {
		Boolean returnBool = false;
		List<Float> obsLocations = new ArrayList<Float>();

		for (int i = 0; i < level.getRoomList().get(currentRoom).getObsList().size(); i++) {
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getX() + level.getRoomList().get(currentRoom).getObsList().get(i).getDx());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY());
			obsLocations.add(level.getRoomList().get(currentRoom).getObsList().get(i).getPos().getY() + level.getRoomList().get(currentRoom).getObsList().get(i).getDy());
		}

		for (int i = 0; i < obsLocations.size()/4; i++) {
			if(getPos().getX() >= obsLocations.get(i*4) && getPos().getX() <= obsLocations.get(i*4+1)){
				if(getPos().getY() >= obsLocations.get(i*4+2) && getPos().getY() <= obsLocations.get(i*4+3)){
					returnBool = true;
				}
			}
		}

		return returnBool;
	}

	public void kill(){
		//Death sequence and reposition
		//Play death sound
	}

	public void respawn(Level level){
		setPos(level.getSpawnPoint());
	}

	public void proxHeartbeat (Level level, int currentRoom){
		float maxDis = 20; //the limit from within which the heartbeat starts rising
		float multFac = 1.2f; //multiplication factor dependant on the numbers
		float difX, difY, difxy, playerPosx, playerPosy, bpm, bpmDis;
		float[] distances = new float[2];

		playerPosx = getPos().getX();
		playerPosy = getPos().getY();

		for (int i = 0; i < distances.length; i++) {
			distances[i] = 0;
		}

		for(int k = 0; k < level.getRoomList().get(currentRoom).getObsList().size(); k++){
			float objPosx, objPosy;

			objPosx = level.getRoomList().get(currentRoom).getObsList().get(k).getPos().getX();
			objPosy = level.getRoomList().get(currentRoom).getObsList().get(k).getPos().getY();


			if(objPosx > playerPosx)
				difX = Math.abs(objPosx - playerPosx);
			else
				difX = Math.abs(playerPosx - objPosx);

			if(objPosy > playerPosy)
				difY = Math.abs(objPosy - playerPosy);
			else
				difY = Math.abs(playerPosy - objPosy);

			difxy = (float) Math.sqrt(difX*difX+difY*difY);

			if(distances[0] > difxy){
				distances[1] = distances[0];
				distances[0] = difxy;
			}else{
				distances[1] = difxy;

			}
		}

		if(distances[0] < maxDis){
			bpmDis = maxDis - distances[0];
			bpm = 60 + bpmDis*multFac;
		}else{
			bpm = 60;
		}
		//should play the heartbeat file a number of times per second equals to bpm/60 or a number of times per minute equal to bpm.

	}


	@Override
	public String toString() {
		return "Player [health=" + getHealth() + ", speed=" + super.getSpeed() + ", orientation=" + super.getOrientation() + "]";
	}

}
