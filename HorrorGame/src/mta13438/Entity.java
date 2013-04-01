package mta13438;

public class Entity {

	private Point pos;
	private float speed;
	private float angle;
	
	public Entity() {
		setPos(0);
		setSpeed(1);
		setAngle(0);
	}
	
	public Entity(Point pos, float speed, float angle) {
		setPos(pos);
		setSpeed(speed);
		setAngle(angle);
	}

	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}

	public String toString() {
		return "Entity [speed=" + speed + ", angle=" + angle + "]";
	}

	public void turnLeft() {
		if(getAngle() >= 0){
			setAngle(360);
		}
		setAngle(getAngle()-(float)0.1);
	}
	
	public void turnRight(boolean turn) {
		if(getAngle() <= 360 ){
			setAngle(0);
		}
		setAngle(getAngle()+(float)0.1);
	}
	
	public void walk() {
		Point.X += getSpeed() * Math.cos(getAngle());
		Point.Y += getSpeed() * Math.sin(getAngle());
	}
	
	public void walkSound() {
		//Something to play the sound
	}
}
