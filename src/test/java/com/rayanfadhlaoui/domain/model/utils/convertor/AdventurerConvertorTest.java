package com.rayanfadhlaoui.domain.model.utils.convertor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.direction.EastDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.NorthDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.SouthDirection;
import com.rayanfadhlaoui.domain.model.pojo.direction.WestDirection;
import com.rayanfadhlaoui.domain.model.pojo.instruction.Instruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.MoveForwardInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.TurnLeftInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.TurnRightInstruction;

public class AdventurerConvertorTest {
	
	@Test
	public void testConvertionNorth() {
		AdventurerData adventurerData = new AdventurerData("Indiana", "N", "AADGAA");
		Adventurer adventurer = AdventurerConvertor.convert(adventurerData);
		assertTrue(adventurer.getDirection() instanceof NorthDirection);
		List<Instruction> instructionList = adventurer.getInstructionList();
		assertEquals(6, instructionList.size());
		assertTrue(instructionList.get(0) instanceof MoveForwardInstruction);
		assertTrue(instructionList.get(2) instanceof TurnRightInstruction);
		assertTrue(instructionList.get(3) instanceof TurnLeftInstruction);
	}
	
	@Test
	public void testConvertionSouth() {
		AdventurerData adventurerData = new AdventurerData("Indiana", "S", "AAGDAA");
		Adventurer adventurer = AdventurerConvertor.convert(adventurerData);
		assertTrue(adventurer.getDirection() instanceof SouthDirection);
		List<Instruction> instructionList = adventurer.getInstructionList();
		assertEquals(6, instructionList.size());
		assertTrue(instructionList.get(0) instanceof MoveForwardInstruction);
		assertTrue(instructionList.get(2) instanceof TurnLeftInstruction);
		assertTrue(instructionList.get(3) instanceof TurnRightInstruction);
	}
	
	@Test
	public void testConvertionEast() {
		AdventurerData adventurerData = new AdventurerData("Indiana", "E", "AADGAA");
		Adventurer adventurer = AdventurerConvertor.convert(adventurerData);
		assertTrue(adventurer.getDirection() instanceof EastDirection);
	}
	
	@Test
	public void testConvertionWest() {
		AdventurerData adventurerData = new AdventurerData("Indiana", "O", "AADGAA");
		Adventurer adventurer = AdventurerConvertor.convert(adventurerData);
		assertTrue(adventurer.getDirection() instanceof WestDirection);
	}
}
