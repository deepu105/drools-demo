package com.technorage.demo.facts;
public class Alarm {

	private Room room;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "Alarm [room=" + room + "]";
	}
}