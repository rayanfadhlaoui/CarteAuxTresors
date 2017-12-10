package com.rayanfadhlaoui.domain.model.utils.convertor;

import java.util.ArrayList;
import java.util.List;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.model.pojo.direction.EastDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.NorthDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.SouthDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.WestDirection;
import com.rayanfadhlaoui.domain.model.pojo.instruction.Instruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.MoveForwardInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.TurnLeftInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.TurnRightInstruction;

public class AdventurerConvertor {

	private static final char MOVE_FORWARD = 'A';
	private static final char MOVE_RIGHT = 'D';
	private static final char MOVE_LEFT = 'G';
	private static final String NORTH = "N";
	private static final String SOUTH = "S";
	private static final String EAST = "E";
	private static final String WEST = "O";

	public static Adventurer convert(AdventurerData adventurerData) {
		String name = adventurerData.getName();
		Direction direction = convertCharacToDirection(adventurerData.getDirection());
		List<Instruction> instructionList = convertToInstructionList(adventurerData.getInstructions());
		return new Adventurer(name, direction, instructionList);
	}

	private static List<Instruction> convertToInstructionList(final String instructions) {
		List<Instruction> instructionList = new ArrayList<>();
		for (int i = 0; i < instructions.length(); i++) {
	        char instruction = instructions.charAt(i);
	        switch (instruction) {
			case MOVE_FORWARD:
				instructionList.add(new MoveForwardInstruction());
				break;
			case MOVE_RIGHT:
				instructionList.add(new TurnRightInstruction());
				break;
			case MOVE_LEFT:
				instructionList.add(new TurnLeftInstruction());
				break;
			default:
				throw new IllegalArgumentException();
			}
	    }
	    return instructionList;
	}

	private static Direction convertCharacToDirection(String direction) {
		switch (direction) {
		case NORTH:
			return new NorthDirection();
		case SOUTH:
			return new SouthDirection();
		case EAST:
			return new EastDirection();
		case WEST:
			return new WestDirection();
		default:
			throw new IllegalArgumentException();
		}
	}
	
}
