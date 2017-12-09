package com.rayanfadhlaoui.domain.model.pojo;

public class AdventurerData {

	private final String name;
	private final String direction;
	private final String intructions;

	public AdventurerData(String name, String direction, String intructions) {
		this.name = name;
		this.direction = direction;
		this.intructions = intructions;
	}

	public String getName() {
		return name;
	}

	public String getDirection() {
		return direction;
	}

	public String getInstructions() {
		return intructions;
	}

}
