package mta13438;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {

	private List<Room> roomList = new ArrayList<Room>();
	private float dx;
	private float dz;
	private float dy;
	private Point spawnPoint = new Point();

	public Level() {
		this.roomList = null;
		this.dx = 0;
		this.dz = 0;
		this.dy = 0;
		updateLevelSize();
	}

	public Level(List<Room> roomList, float dx, float dz, float dy) {
		this.roomList.addAll(roomList);
		this.dx = dx;
		this.dz = dz;
		this.dy = dy;
		updateLevelSize();
	}

	public List<Room> getRoomList() {
		return Collections.unmodifiableList(roomList);
	}
	public void setRoomList(List<Room> roomList) {
		this.roomList.clear();
		this.roomList.addAll(roomList);
	}
	public void addRoomList(Room room){
		this.roomList.add(room);
	}
	public void autoLevelGenerator(Point startPoint){
		for (int i = 0; i < getRoomList().size(); i++){
			if (i == 0){
				this.roomList.get(i).setPos(new Point(startPoint.getX(),startPoint.getY(),0));
				this.roomList.get(i).setEntrance(new Point (this.roomList.get(i).getEntrance().getX()+startPoint.getX(),this.roomList.get(i).getEntrance().getY()+startPoint.getY(),0));
				this.roomList.get(i).setExit(new Point (this.roomList.get(i).getExit().getX()+startPoint.getX(),this.roomList.get(i).getExit().getY()+startPoint.getY(),0));
				this.roomList.get(i).generateDoorObs();
			} else {
				float entranceY = this.roomList.get(i).entrance.getY();
				float entranceX = this.roomList.get(i).entrance.getX();
				float previousExitY = this.roomList.get(i-1).exit.getY();
				float previousExitX = this.roomList.get(i-1).exit.getX();
				this.roomList.get(i).setPos(new Point(previousExitX-entranceX,previousExitY-entranceY,0));
				this.roomList.get(i).setEntrance(new Point (previousExitX,previousExitY,0));
				this.roomList.get(i).setExit(new Point (this.roomList.get(i).getExit().getX()+this.roomList.get(i).getPos().getX(),this.roomList.get(i).getExit().getY()+this.roomList.get(i).getPos().getY(),0));
				this.roomList.get(i).generateDoorObs();
			}
		}
	}
	public float getDx() {
		return dx;
	}
	public void setDx(float dx) {
		this.dx = dx;
	}
	public float getDz() {
		return dz;
	}
	public void setDz(float dz) {
		this.dz = dz;
	}
	public float getDy() {
		return dy;
	}
	public void setDy(float dy) {
		this.dy = dy;
	}
	
	public int getCurrentRoom(Point playerPos) {
		Integer currentRoom = 0;
		for (int i = 0; i < getRoomList().size(); i++) {		
			if(playerPos.getX() >= getRoomList().get(i).getPos().getX() && playerPos.getX() <= (getRoomList().get(i).getPos().getX() + getRoomList().get(i).getDx()) && playerPos.getY() >= getRoomList().get(i).getPos().getY() && playerPos.getY() <= (getRoomList().get(i).getPos().getY() + getRoomList().get(i).getDy())){
				currentRoom = i;
			}			
		}
		return currentRoom;
	}
	
	public void Draw(){
	   	 for(Room room : this.roomList){
	   		 room.draw();
	   	 }
	}
	
	public void updateSpawnPoint(Player player, Level level) {
		Point playerPos = player.getPos();
		spawnPoint = level.getRoomList().get(getCurrentRoom(playerPos)).entrance;
	}
	
	public Point getSpawnPoint() {
		return this.spawnPoint;
	}

	public String toString() {
		return "Level [roomList=" + roomList + ", dx=" + dx + ", dz="
				+ dz + ", dy=" + dy + "]";
	}

	public void updateLevelSize() {
		if(roomList.isEmpty() == false){
			float dxp = 0;
			float dzp = 0;
			float dyp = 0;
			float dxm = 0;
			float dzm = 0;
			float dym = 0;

			for (int i = 0; i < roomList.size(); i++) {
				if(roomList.get(i).getPos().getX() > 0 && roomList.get(i).getPos().getY() > 0 && roomList.get(i).getPos().getZ() > 0){
					if(dxp < roomList.get(i).getPos().getX() + roomList.get(i).getDx()){
						dxp = roomList.get(i).getPos().getX() + roomList.get(i).getDx();
					}
					if(dyp < roomList.get(i).getPos().getY() + roomList.get(i).getDy()){
						dyp = roomList.get(i).getPos().getY() + roomList.get(i).getDy();
					}
					if(dzp < roomList.get(i).getPos().getZ() + roomList.get(i).getDz()){
						dzp = roomList.get(i).getPos().getZ() + roomList.get(i).getDz();
					}
				}else{
					if(dxm > roomList.get(i).getPos().getX()){
						dxm = roomList.get(i).getPos().getX();
					}
					if(dym > roomList.get(i).getPos().getY()){
						dym = roomList.get(i).getPos().getY();
					}
					if(dzm > roomList.get(i).getPos().getZ()){
						dzm = roomList.get(i).getPos().getZ();
					}
				}
			}		

			setDx(dxp+Math.abs(dxm));
			setDz(dzp+Math.abs(dzm));
			setDy(dyp+Math.abs(dym));
		}
	}
}
