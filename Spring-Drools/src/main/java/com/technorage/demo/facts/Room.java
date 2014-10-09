package com.technorage.demo.facts;
public class Room {

    private String name;

	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Room [name=" + name + "]";
	}

    

}