package mta13438;

import static org.lwjgl.opengl.GL11.*;

public class Entity {

	private Point pos;
	private float speed;
	private float orientation;

	public Entity() {
		setPos(null);
		setSpeed(1);
		setAngle(0);
	}

	public Entity(Point pos, float speed, float orientation) {
		setPos(pos);
		setSpeed(speed);
		setAngle(orientation);
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

	public void setAngle(float orientation) {
		this.orientation = orientation;
	}

	public String toString() {
		return "Entity [speed=" + speed + ", orientation=" + orientation + "]";
	}

	public void turnLeft() {
		if (getOrientation() >= 0) {
			setAngle(360);
		}
		setAngle(getOrientation() - (float) 0.1);
	}

	public void turnRight(boolean turn) {
		if (getOrientation() <= 360) {
			setAngle(0);
		}
		setAngle(getOrientation() + (float) 0.1);
	}

	public void foward() {
		setPos(+(float) (getSpeed() * Math.cos(getOrientation())),
				+(float) (getSpeed() * Math.sin(getOrientation())), 0);
	}

	public void backward() {
		setPos(-(float) (getSpeed() * Math.cos(getOrientation())),
				-(float) (getSpeed() * Math.sin(getOrientation())), 0);
	}

	public void walkSound() {
		// Something to play the sound
	}

	public void draw(Point pos) {
		glColor3f(0f, 0f, 1.0f);
		glPointSize(5);
		glBegin(GL_POINTS);
		glVertex2f(pos.getX(), pos.getY());
		glEnd();

		/*
		 * If a circle is preferred instead of a point. glColor3f(1.0f, 0f, 0f);
		 * glBegin(GL_LINE_STRIP); float f = 0.0f; for(int i = 0; i<30;i++){
		 * glVertex3f(pos.getX()+(float)Math.cos(f),
		 * pos.getY()+(float)Math.sin(f), 0); f = (float) (f +(2*Math.PI/30)); }
		 * glEnd();
		 */
	}
}
