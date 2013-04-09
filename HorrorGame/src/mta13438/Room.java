package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Room {

	float dx, dy, dz, sabins;
	MATERIALS material;
	Point pos;
	Point entrance;
	Point exit;
	List<Obs> obsList = new ArrayList<Obs>();
	
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
	
	public Room(Point pos, float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material, List<Obs> obsList){
		this.pos = pos;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.obsList = obsList;
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
	public List<Obs> getObsList() {
		return obsList;
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
	public void setObsList(List<Obs> obsList) {
		this.obsList.clear();
		this.obsList = obsList;
	}
	public void addObsList(Obs obs) {
		this.obsList.add(obs);
	} 
	
	public boolean isNearEntrance(){
		// Check Entrance X and Y with pos X and Y; Ignore Z value
		
		return false;
	}
	public boolean isNearExit(){
		// Check Exit X and Y with pos X and Y; Ignore Z value
		
		return false;
	}
	
	public void draw() {
	   	 glColor3f(1.0f, 1.0f, 1.0f);
	   	 glLineWidth(1.5f);
	   	 glBegin(GL_LINES);
	   		 //Bottom Line
	   		 glVertex2i((int)this.pos.getX(), (int)this.pos.getY());
	   		 glVertex2i((int)(this.pos.getX()+this.getDx()), (int)this.pos.getY());
	   		 //Left Line
	   		 glVertex2i((int)this.pos.getX(), (int)this.pos.getY());
	   		 glVertex2i((int)this.pos.getX(), (int)(this.pos.getY()+this.getDy()));
	   		 //Top Line
	   		 glVertex2i((int)this.pos.getX(), (int)(this.pos.getY()+this.getDy()));
	   		 glVertex2i((int)(this.pos.getX()+this.getDx()), (int)(this.pos.getY()+this.getDy()));
	   		 //Right Line
	   		 glVertex2i((int)(this.pos.getX()+this.getDx()), (int)(this.pos.getY()+this.getDy()));
	   		 glVertex2i((int)(this.pos.getX()+this.getDx()), (int)this.pos.getY());
	   	 glEnd();
	   	 
	   	 if(entrance != null || entrance.getX()!=0 && entrance.getY()!=0 && entrance.getZ()!=0){//Check if there are an entrance point, then draw RED circle
	   		 glColor3f(1.0f, 0f, 0f);
	   		 glBegin(GL_LINE_STRIP);
	   		 float f = 0.0f;
	   		 for(int i = 0; i<30;i++){
	   			 glVertex3f(entrance.getX()+(float)Math.cos(f), entrance.getY()+(float)Math.sin(f), 0);
	   			 f = (float) (f +(2*Math.PI/30));
	   		 }
	   		 glEnd();
	   	 }
	   	 
	   	 if(exit != null || exit.getX()!=0 && exit.getY()!=0 && exit.getZ()!=0){ //Check if there are an exit point, then draw ORANGE circle
	   		 glColor3f(1.0f, 0.6f, 0f);
	   		 glBegin(GL_LINE_STRIP);
	   		 float h = 0.0f;
	   		 for(int i = 0; i<30;i++){
	   			 glVertex3f(exit.getX()+(float)Math.cos(h), exit.getY()+(float)Math.sin(h), 0);
	   			 h = (float) (h +(2*Math.PI/30));
	   		 }
	   		 glEnd();
	   	 }
	   	 
	}

	
	public String toString(){ // not updated
		return "ROOM: \n Width = " + dx + ".\n Height = " +
				dz + ".\n Length = " + dy + ".\n Entrance = " 
				+ entrance + ".\n Exit = " + exit + ".\n Sabins = " +
				 sabins + ".\n";
		// When variable changes have been decided, changes method.
	} 
	
	
}
