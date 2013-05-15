package mta13438;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class Point {
	private float x,y,z;
	
	//no args constructor
	public Point(){
		setX(0);
		setY(0);
		setZ(0);
	}
	
	//Constructor
	public Point(float x, float y,float z){
		setX(x);
		setY(y);
		setZ(z);
	}
	public void draw(){
		glColor3f(0.2f, 0.6f, 0.4f);
		glBegin(GL_LINE_STRIP); float f = 0.0f; for(int i = 0; i<30;i++){
			glVertex3f(getX()+(float)Math.cos(f),
					getY()+(float)Math.sin(f), 0); f = (float) (f +(2*Math.PI/30)); }
		glEnd();
	}

	//getters and setters
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	@Override
	public boolean equals(Object obj){
		if (obj == null) return false;
		if (!(obj instanceof Point)) return false;
		Point point = (Point)obj;
		if (!(getX() == point.getX())) return false;
		if (!(getY() == point.getY())) return false;
		if (!(getZ() == point.getZ())) return false;
		return true;
	}
	
	public int compareTo(Point point){
		if (point == null) return 1;
		if (this.equals(point)) return 0;
		if (getX() > point.getX()) return 1;
		if (getY() > point.getY()) return 1;
		if (getZ() > point.getZ()) return 1;
		if (getX() < point.getX()) return -1;
		if (getY() < point.getY()) return -1;
		if (getZ() < point.getZ()) return -1;
		return 0;
	}
}
