package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Room {

	float dx, dy, dz, sabins, doorSize;
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
		this.doorSize = 1;
		generateDoorObs();
	}

	public Room(Point pos, float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material){
		this.pos = pos;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.doorSize= 1;
		generateDoorObs();
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
		this.doorSize = 1;
		generateDoorObs();
	}

	public Room(Point pos, float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material, List<Obs> obsList, float doorSize){
		this.pos = pos;
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.obsList = obsList;
		this.doorSize = doorSize;
		generateDoorObs();
	}

	private void generateDoorObs(){
		if (entrance.getX() == pos.getX()) {
			obsList.add(new Door(entrance,0.01f,doorSize,10f,0));
		}else if (entrance.getX() == pos.getX() + dx) {
			obsList.add(new Door(new Point(entrance.getX() - 0.01f,entrance.getY(), entrance.getZ()),0.01f,doorSize,10f,0));
		}else if (entrance.getY() == pos.getY()) {
			obsList.add(new Door(entrance,doorSize,0.01f,10f,0));
		}else if (entrance.getY() == pos.getY() + dy) {
			obsList.add(new Door(new Point(entrance.getX(),entrance.getY() - 0.01f, entrance.getZ()),0.01f,doorSize,10f,0));
		}
		
		if (exit.getX() == pos.getX()) {
			obsList.add(new Door(exit,0.01f,doorSize,10f,0));
		}else if (exit.getX() == pos.getX() + dx) {
			obsList.add(new Door(new Point(exit.getX() - 0.01f,exit.getY(), exit.getZ()),0.01f,doorSize,10f,0));
		}else if (exit.getY() == pos.getY()) {
			obsList.add(new Door(exit,doorSize,0.01f,10f,0));
		}else if (exit.getY() == pos.getY() + dy) {
			obsList.add(new Door(new Point(exit.getX(),exit.getY() - 0.01f, exit.getZ()),0.01f,doorSize,10f,0));
		}
		 System.out.println(obsList);
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
	public float getDoorSize() {
		return doorSize;
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
	public void setDoorSize(float doorSize) {
		this.doorSize = doorSize;
	}

	public void draw() {
		glColor3f(1.0f, 1.0f, 1.0f);
		glLineWidth(0.5f);
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
	}


	public String toString(){ // not updated
		return "ROOM: \n Width = " + dx + ".\n Height = " +
		dz + ".\n Length = " + dy + ".\n Entrance = " 
		+ entrance + ".\n Exit = " + exit + ".\n Sabins = " +
		sabins + ".\n";
		// When variable changes have been decided, changes method.
	} 


}
