package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Entity {

	private Point pos;
	private float speed;
	private float orientation;

	public Entity() {
		setPos(new Point(0,0,0));
		setSpeed(1);
		setOrientation(0);
	}

	public Entity(Point pos, float speed, float orientation) {
		setPos(pos);
		setSpeed(speed);
		setOrientation(orientation);
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public void setPos(float x, float y, float z) {
		this.pos.setX(x);
		this.pos.setY(y);
		this.pos.setZ(z);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getOrientation() {
		return orientation;
	}

	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}

	public String toString() {
		return "Entity [speed=" + speed + ", orientation=" + orientation + "]";
	}

	public void turnLeft(float delta) {
		setOrientation(getOrientation() + (0.1f * delta));
		if (getOrientation() > 2*Math.PI) {
			setOrientation(0);
		}
	}

	public void turnRight(float delta) {
		setOrientation(getOrientation() - (0.1f * delta));
		if (getOrientation() < 0) {
			setOrientation((float)(2*Math.PI));
		}
	}

	public void foward(float delta, Level level, int currentRoom) {
		float x,y,z,minX,maxX,minY,maxY;
		
		minY = level.getRoomList().get(currentRoom).getPos().getY();
		maxY = level.getRoomList().get(currentRoom).getPos().getY() + level.getRoomList().get(currentRoom).getDy();
		minX = level.getRoomList().get(currentRoom).getPos().getX();
		maxX = level.getRoomList().get(currentRoom).getPos().getX() + level.getRoomList().get(currentRoom).getDx();

		//Checking if the entity is inside the room boundery.
		if(getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) >= minX && getPos().getX() + ((getSpeed() * Math.cos(getOrientation())) * delta) <= maxX){
			x = (float) ((getSpeed() * Math.cos(getOrientation()) * delta) + getPos().getX());
		}else x = 0.0f + getPos().getX();

		//Checking if the entity is inside the room boundery.
		if(getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) >= minY && getPos().getY() + ((getSpeed() * Math.sin(getOrientation())) * delta) <= maxY){
			y = (float) ((getSpeed() * Math.sin(getOrientation()) * delta) + getPos().getY());
		}else y = 0.0f + getPos().getY();

		z = 0.0f + getPos().getZ();

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

		//Checking if the entity is inside the room boundery.
		if(getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta)) >= minX && getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta)) <= maxX){
			//Makes the entity move in the x direction
			x = getPos().getX() - (float) ((getSpeed() * Math.cos(getOrientation()) * delta));
		}else x = getPos().getX() - 0.0f;

		//Checking if the entity is inside the room boundery.
		if(getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta)) >= minY && getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta)) <= maxY){
			//Makes the entity move in the x direction
			y = getPos().getY() - (float) ((getSpeed() * Math.sin(getOrientation()) * delta));
		}else y =getPos().getY() - 0.0f;

		z = getPos().getZ() - 0.0f;

		setPos(x, y, z);
	}

	public void walkSound() {
		// Something to play the sound
	}

	public void draw() {
		
		/**glBegin(GL_TRIANGLES);
		glColor3f(0f, 0f, 1.0f);
		glVertex3f(getPos().getX() + 0, getPos().getY() + 2.5f, 0);
        glVertex3f(getPos().getX() + 5, getPos().getY() + 0, 0);
        glVertex3f(getPos().getX() + 0, getPos().getY() - 2.5f, 0);
        **//**
		System.out.println(Math.cos(getOrientation()) * 2);
        glVertex3f((float) (getPos().getX() + Math.cos(getOrientation()) * 0), (float) (getPos().getY() + Math.sin(getOrientation()) * 2), 0);
        glVertex3f((float) (getPos().getX() + Math.cos(getOrientation()) * 2), (float) (getPos().getY() + Math.sin(getOrientation()) * 0), 0);
        glVertex3f((float) (getPos().getX() + Math.cos(getOrientation()) * 0), (float) (getPos().getY() + Math.sin(getOrientation()) * -2), 0);
        glEnd();**/

		
		 //If a circle is preferred instead of a point. 
		glColor3f(1.0f, 0f, 0f);
		glBegin(GL_LINE_STRIP); float f = 0.0f; for(int i = 0; i<30;i++){
		glVertex3f(pos.getX()+(float)Math.cos(f),
		pos.getY()+(float)Math.sin(f), 0); f = (float) (f +(2*Math.PI/30)); }
		glEnd();
		 
	}
}
