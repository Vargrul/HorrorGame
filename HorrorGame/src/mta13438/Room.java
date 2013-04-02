package mta13438;

public class Room {

	float width, length, height, entrance, exit, sabins;
	// Points for entrance and exit
	// Need something to indicate what walls are made of.
	// Maybe something to indicate what the floor is made of.
	// I am thinking Enums.
	
	public Room(){
		// Guess we need a standard setup
		// for the rooms when created with
		// a no args constructor.
	}
	
	public Room(float width, float length, float height, float entrance, float exit){
		this.width = width;
		this.length = length;
		this.height = height;
		this.entrance = entrance;
		this.exit = exit;
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
	public float getEntrance(){
		return entrance;
	}
	public float getExit(){
		return exit;
	}
	public float getSabins(){
		return sabins;
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
	public void setEntrance(float entrance){
		this.entrance = entrance;
	}
	public void setExit(float exit){
		this.exit = exit;
	}
	public void setSabins(float sabins){
		this.sabins = sabins;
	}
	
	public String toString(){
		return "ROOM: \n Width = " + width + ".\n Height = " +
				height + ".\n Length = " + length + ".\n Entrance = " 
				+ entrance + ".\n Exit = " + exit + ".\n Sabins = " +
				 sabins + ".\n";
		// When variable changes have been decided, changes method.
	} 
	
	
}
