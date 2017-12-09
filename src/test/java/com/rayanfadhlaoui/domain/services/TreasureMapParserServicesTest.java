package com.rayanfadhlaoui.domain.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.entities.Field;
import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.Plain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.model.pojo.direction.NorthDirection;
import com.rayanfadhlaoui.domain.model.pojo.instruction.Instruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.MoveForwardInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.MoveLeftInstruction;
import com.rayanfadhlaoui.domain.model.pojo.instruction.MoveRightInstruction;
import com.rayanfadhlaoui.domain.services.TreasureMapParserService;
import com.rayanfadhlaoui.domain.services.fileParser.TreasureMapParser;

public class TreasureMapParserServicesTest {

	private TreasureMapParser parserMock;
	private Map<Position, Integer> treasuresByPosition;
	private List<Position> mountainsPositionList;
	private TreasureMapParserService treasureMapService;
	private Map<Position, AdventurerData> adventurerDataByPosition;

	@Before
	public void setUp() throws FileNotFoundException {
		parserMock = Mockito.mock(TreasureMapParser.class);
		mountainsPositionList = new ArrayList<>();
		treasuresByPosition = new HashMap<>();
		adventurerDataByPosition = new HashMap<>();
		treasureMapService = new TreasureMapParserService(parserMock);
	}

	@Test
	public void testTreasureMapCreationOk() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(0, 4), 5);
		AdventurerData adventurerData = new AdventurerData("Indiana", "N", "AADGAA");
		adventurerDataByPosition.put(new Position(0,0), adventurerData);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition, adventurerDataByPosition);

		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);
		
		treasureMapService.generateTreasureMapAndAdventurer();
		TreasureMap treasureMap = treasureMapService.getTreasureMap();
		
		Field field = treasureMap.getFieldAt(new Position(1, 1));
		assertTrue(field instanceof Mountain);
		assertEquals(0, field.getNumberOfTreasures());
		
		field = treasureMap.getFieldAt(new Position(0, 4));
		assertTrue(field instanceof Plain);
		assertEquals(5, field.getNumberOfTreasures());
		
		Adventurer adventurer = treasureMapService.getAdventurerByPosition().get(new Position(0,0));
		assertTrue(adventurer.getDirection() instanceof NorthDirection);
		List<Instruction> instructionList = adventurer.getInstructionList();
		assertEquals(6, instructionList.size());
		assertTrue(instructionList.get(0) instanceof MoveForwardInstruction);
		assertTrue(instructionList.get(2) instanceof MoveRightInstruction);
		assertTrue(instructionList.get(3) instanceof MoveLeftInstruction);
	}

	@Test
	public void testTreasureMapCreationPutTreasureOnMountain() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(1, 1), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition, adventurerDataByPosition);
		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		treasureMapService.generateTreasureMapAndAdventurer();
		TreasureMap treasureMap = treasureMapService.getTreasureMap();
		Field field = treasureMap.getFieldAt(new Position(1, 1));
		assertTrue(field instanceof Mountain);
		assertEquals(0, field.getNumberOfTreasures());
	}

	@Test
	public void testCreation_MountainWrongPosition() throws Exception {
		mountainsPositionList.add(new Position(10, 10));
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition, adventurerDataByPosition);
		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		try {
			treasureMapService.generateTreasureMapAndAdventurer();
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}

	}

	@Test
	public void testCreation_TreasureWrongPosition() throws Exception {
		treasuresByPosition.put(new Position(10, 10), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition, adventurerDataByPosition);
		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		try {
			treasureMapService.generateTreasureMapAndAdventurer();
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}
	}

}
