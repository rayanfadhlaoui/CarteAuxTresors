package com.rayanfadhlaoui.domain.model.entities;

import java.util.Iterator;
import java.util.List;

import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.Instruction;

public class Adventurer {

	private final String name;
	private Direction direction;
	private final List<Instruction> instructionList;
	private final Iterator<Instruction> iterator;

	public Adventurer(String name, Direction direction, List<Instruction> instructionList) {
		this.name = name;
		this.direction = direction;
		this.instructionList = instructionList;
		iterator = instructionList.iterator();
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

	public boolean explorationIsOver() {
		return !iterator.hasNext();
	}

	public Instruction getNextInstruction() {
		if (iterator.hasNext()) {
			return iterator.next();
		}
		throw new IndexOutOfBoundsException();
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

}
