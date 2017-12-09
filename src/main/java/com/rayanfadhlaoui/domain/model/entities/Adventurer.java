package com.rayanfadhlaoui.domain.model.entities;

import java.util.List;

import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.Instruction;

public class Adventurer {

	private final String name;
	private Direction direction;
	private List<Instruction> instructionList;

	public Adventurer(String name, Direction direction, List<Instruction> instructionList) {
		this.name = name;
		this.direction = direction;
		this.instructionList = instructionList;
	}

	public String getName() {
		return name;
	}

	public Direction getDirection() {
		return direction;
	}

	public List<Instruction> getInstructionList() {
		return instructionList;
	}

}
