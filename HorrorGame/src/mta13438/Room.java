package mta13438;

public class Room {

	float dx, dy, dz, sabins;
	MATERIALS material;
	Point pos;
	Point entrance;
	Point exit;
	
	public Room(){
		this.pos = new Point();
		this.dx = 10;
		this.dz = 10;
		this.dy = 10;
		this.entrance = new Point(0, dy/2, 0);
		this.exit = new Point(10, dy/2, 0);
		this.material = MATERIALS.ROCK;//Need to add default material
	}
	
	public Room(Point pos, float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material){
		this.pos = pos;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
	}
	public Point getPos(){
		return pos;
	}
	public float getDx(){
		return dx;
	}
	public float getDz(){
		return dz;
	}
	public float getDy(){
		return dy;
	}
	public Point getEntrance(){
		return entrance;
	}
	public Point getExit(){
		return exit;
	}
	public float getSabins(){
		return sabins;
	}
	public float getRoomSize(){
		return dz*dy;
	}
	public MATERIALS getMaterial(){
		return material;
	}
	
	
	public void setPos(Point pos){
		this.pos = pos;
	}
	public void setMaterial(MATERIALS material){
		this.material = material;
	}
	public void setDx(float dx){
		this.dx = dx;
	}
	public void setDz(float dz){
		this.dz = dz;
	}
	public void setDy(float dy){
		this.dy = dy;
	}
	public void setEntrance(Point entrance){
		this.entrance = entrance;
	}
	public void setExit(Point exit){
		this.exit = exit;
	}
	public void setSabins(float sabins){
		this.sabins = sabins;
	}
	public boolean isNearEntrance(){
		// Check Entrance X and Y with pos X and Y; Ignore Z value
		
		return false;
	}
	public boolean isNearExit(){
		// Check Exit X and Y with pos X and Y; Ignore Z value
		
		return false;
	}
	
	public String toString(){ // not updated
		return "ROOM: \n Width = " + dx + ".\n Height = " +
				dz + ".\n Length = " + dy + ".\n Entrance = " 
				+ entrance + ".\n Exit = " + exit + ".\n Sabins = " +
				 sabins + ".\n";
		// When variable changes have been decided, changes method.
	} 
	
	
}
