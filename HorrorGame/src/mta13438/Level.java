package mta13438;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level {
	
	private List<Room> roomList = new ArrayList<Room>();
	private float width;
	private float height;
	private float length;
	
	public Level() {
		this.roomList = null;
		this.width = 0;
		this.height = 0;
		this.length = 0;
	}

	public Level(List<Room> roomList, float width, float height, float length) {
		this.roomList.addAll(roomList);
		this.width = width;
		this.height = height;
		this.length = length;
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
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}

	public String toString() {
		return "Level [roomList=" + roomList + ", width=" + width + ", height="
				+ height + ", length=" + length + "]";
	}
	
}
