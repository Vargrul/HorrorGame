package mta13438;

public class Room {

	float width, length, height, sabins;
	MATERIALS material;
	Point pos;
	Point entrance;
	Point exit;
	
	public Room(){
		this.pos = new Point();
		this.width = 10;
		this.height = 10;
		this.length = 10;
		this.entrance = new Point(0, length/2, 0);
		this.exit = new Point(10, length/2, 0);
		this.material = MATERIALS.ROCK;//Need to add default material
	}
	
	public Room(Point pos, float width, float length, float height, Point entrance, Point exit, MATERIALS material){
		this.pos = pos;
		this.width = width;
		this.length = length;
		this.height = height;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
	}
	public Point getPos(){
		return pos;
	}
	public float getWidth(){
		return width;
	}
	public float getHeight(){
		return height;
	}
	public float getLength(){
		return length;
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
		return height*length;
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
	public void setWidth(float width){
		this.width = width;
	}
	public void setHeight(float height){
		this.height = height;
	}
	public void setLength(float length){
		this.length = length;
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
		return "ROOM: \n Width = " + width + ".\n Height = " +
				height + ".\n Length = " + length + ".\n Entrance = " 
				+ entrance + ".\n Exit = " + exit + ".\n Sabins = " +
				 sabins + ".\n";
		// When variable changes have been decided, changes method.
	} 
	
	
}
