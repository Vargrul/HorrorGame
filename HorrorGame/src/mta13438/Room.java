package mta13438;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Room {

	float dx, dy, dz, doorSize;
	MATERIALS material;
	Point pos;
	Point entrance;
	Point exit;
	List<Obs> obsList = new ArrayList<Obs>(); //The list of obstacles inside the room.
	float[] sabins = new float[6];
	float[] rt60 = new float[6];

	//No args constructor
	public Room(){
		this.pos = new Point();
		this.dx = 10;
		this.dz = 10;
		this.dy = 10;
		this.material = MATERIALS.ROCK;
		this.doorSize = 4;
		this.entrance = new Point(0, dy/2-doorSize/2, 0);
		this.exit = new Point(dx, dy/2-doorSize/2, 0);
		generateDoorObs();
		updateSabins();
	}
	//Constructor
	public Room(float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material){
		this.pos = new Point();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.doorSize= 4;
		generateDoorObs();
		updateSabins();
	}
	//Extended Constructor
	public Room(float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material, List<Obs> obsList){
		this.pos = new Point();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.obsList = obsList;
		this.doorSize = 4;
		generateDoorObs();
		updateSabins();
	}
	//Extended Constructor
	public Room(float dx, float dy, float dz, Point entrance, Point exit, MATERIALS material, List<Obs> obsList, float doorSize){
		this.pos = new Point();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
		this.entrance = entrance;
		this.exit = exit;
		this.material = material;
		this.obsList = obsList;
		this.doorSize = doorSize;
		generateDoorObs();
		updateSabins();
	}

	//Door generator
	public void generateDoorObs(){
		if (entrance.getX() == pos.getX()) {
			obsList.add(new Entrance(entrance,0.1f,doorSize,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (entrance.getX() == pos.getX() + dx) {
			obsList.add(new Entrance(new Point(entrance.getX() - 0.1f,entrance.getY(), entrance.getZ()),0.1f,doorSize,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (entrance.getY() == pos.getY()) {
			obsList.add(new Entrance(entrance,doorSize,0.1f,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (entrance.getY() == pos.getY() + dy) {
			obsList.add(new Entrance(new Point(entrance.getX(),entrance.getY() - 0.1f, entrance.getZ()),doorSize,0.1f,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}

		if (exit.getX() == pos.getX()) {
			obsList.add(new Exit(exit,0.1f,doorSize,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (exit.getX() == pos.getX() + dx) {
			obsList.add(new Exit(new Point(exit.getX() - 0.1f,exit.getY(), exit.getZ()),0.1f,doorSize,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (exit.getY() == pos.getY()) {
			obsList.add(new Exit(exit,doorSize,0.1f,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}else if (exit.getY() == pos.getY() + dy) {
			obsList.add(new Exit(new Point(exit.getX(),exit.getY() - 0.1f, exit.getZ()),doorSize,0.1f,10f,MATERIALS.DOOR___SOLID_WOOD_PANELS));
		}
	}

	//Getters
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
	public float[] getSabins(){
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
	public float[] getRt60() {
		return this.rt60;
	}

	//Setters
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
	public void setSabins(float[] sabins){
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
	public void setRt60(float[] rt60) {
		this.rt60 = rt60;
	}

	public void updateSabins() {
		float[] x = new float[6];

		for (int i = 0; i < x.length; i++) {
			x[i] = ((dx * dy) * 2 + (dx * dz) * 2 + (dy * dz) * 2) * material.getAc()[i];
		}

		for (int i = 0; i < obsList.size(); i++) {
			for (int j = 0; j < x.length; j++) {
				x[j] -= (obsList.get(i).getDx() * obsList.get(i).getDy()) * material.getAc()[j];
				x[j] += (obsList.get(i).getDx() * obsList.get(i).getDy()) * obsList.get(i).getMaterial().getAc()[j];
				x[j] += ((obsList.get(i).getDx() * obsList.get(i).getDz()) * 2) * obsList.get(i).getMaterial().getAc()[j];
				x[j] += ((obsList.get(i).getDy() * obsList.get(i).getDz()) * 2) * obsList.get(i).getMaterial().getAc()[j];
			}
		}

		setSabins(x);
		updateRt60();
	}
	public void updateRt60() {
		float[] x = new float[6];
		float c = 340.29f;
		float area;

		area = getDx() * getDy() * getDz();
		for (int i = 0; i < getObsList().size(); i++) {
			area -= getObsList().get(i).getDx() * getObsList().get(i).getDy() * getObsList().get(i).getDz();
		}

		for (int i = 0; i < rt60.length; i++) {
			x[i] = (float)(((4 * Math.log(Math.pow(10, 6))) / (c))*(area / getSabins()[i]));
		}

		setRt60(x);
	}
	//Draw function
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
