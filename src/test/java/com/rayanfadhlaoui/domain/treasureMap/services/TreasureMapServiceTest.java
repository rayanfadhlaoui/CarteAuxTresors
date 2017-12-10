package com.rayanfadhlaoui.domain.treasureMap.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Field;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Plain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.treasureMap.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.NorthDirection;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.SouthDirection;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.WestDirection;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction.Instruction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction.MoveForwardInstruction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction.TurnLeftInstruction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction.TurnRightInstruction;
import com.rayanfadhlaoui.domain.treasureMap.services.TreasureMapService;
import com.rayanfadhlaoui.domain.treasureMap.services.fileParser.TreasureMapParser;
import com.rayanfadhlaoui.domain.treasureMap.services.fileParser.TreasureMapParserImpl;
import com.rayanfadhlaoui.domain.treasureMap.services.fileParser.TreasureMapWriter;
import com.rayanfadhlaoui.domain.treasureMap.services.fileParser.TreasureMapWriterImpl;

public class TreasureMapServiceTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	private TreasureMapParser parserMock;
	private Map<Position, Integer> treasuresByPosition;
	private List<Position> mountainsPositionList;
	private TreasureMapService treasureMapService;
	private Map<Position, AdventurerData> adventurerDataByPosition;

	@Before
	public void setUp() throws FileNotFoundException {
		parserMock = Mockito.mock(TreasureMapParser.class);
		mountainsPositionList = new ArrayList<>();
		treasuresByPosition = new HashMap<>();
		adventurerDataByPosition = new HashMap<>();
		treasureMapService = new TreasureMapService(parserMock);
	}

	@Test
	public void testTreasureMapCreationOk() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(0, 4), 5);
		AdventurerData adventurerData = new AdventurerData("Indiana", "N", "AADGAA");
		adventurerDataByPosition.put(new Position(0, 0), adventurerData);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition,
				adventurerDataByPosition);

		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		treasureMapService.generateTreasureMapAndAdventurer();
		TreasureMap treasureMap = treasureMapService.getTreasureMap();

		Field field = treasureMap.getFieldAt(new Position(1, 1));
		assertTrue(field instanceof Mountain);
		assertEquals(0, field.getNumberOfTreasures());

		field = treasureMap.getFieldAt(new Position(0, 4));
		assertTrue(field instanceof Plain);
		assertEquals(5, field.getNumberOfTreasures());

		Adventurer adventurer = treasureMapService.getAdventurerByPosition().get(new Position(0, 0));
		assertTrue(adventurer.getDirection() instanceof NorthDirection);
		List<Instruction> instructionList = adventurer.getInstructionList();
		assertEquals(6, instructionList.size());
		assertTrue(instructionList.get(0) instanceof MoveForwardInstruction);
		assertTrue(instructionList.get(2) instanceof TurnRightInstruction);
		assertTrue(instructionList.get(3) instanceof TurnLeftInstruction);
	}

	@Test
	public void testTreasureMapCreationPutTreasureOnMountain() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(1, 1), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition,
				adventurerDataByPosition);
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
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition,
				adventurerDataByPosition);
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
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition,
				adventurerDataByPosition);
		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		try {
			treasureMapService.generateTreasureMapAndAdventurer();
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}
	}

	@Test
	public void testAdventurerExploration() {
		Position treasurePosition1 = new Position(1, 4);
		Position treasurePosition2 = new Position(1, 6);
		Position treasurePosition3 = new Position(4, 10);
		Position adventurerStartingPosition = new Position(2, 4);
		Position adventurerLastPosition = new Position(1, 6);

		mountainsPositionList.add(new Position(2, 5));
		mountainsPositionList.add(new Position(0, 7));
		
		treasuresByPosition.put(treasurePosition1, 1);
		treasuresByPosition.put(treasurePosition2, 3);
		treasuresByPosition.put(treasurePosition3, 3);
		
		AdventurerData adventurerData = new AdventurerData("Indiana", "S", "DDAAGAAADAAADAAGGA");
		adventurerDataByPosition.put(adventurerStartingPosition, adventurerData);
		
		Dimension dimension = new Dimension(5, 14);
		TreasureMapData treasureMapData = new TreasureMapData(dimension, mountainsPositionList, treasuresByPosition, adventurerDataByPosition);

		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		treasureMapService.generateTreasureMapAndAdventurer();
		treasureMapService.simulate();
		
		
		Map<Position, Adventurer> adventurerByPosition = treasureMapService.getAdventurerByPosition();

		Adventurer adventurer = adventurerByPosition.get(adventurerStartingPosition);
		assertTrue(adventurer == null);
		
		adventurer = adventurerByPosition.get(adventurerLastPosition);
		assertTrue(adventurer.getDirection() instanceof WestDirection);
		assertEquals(3, adventurer.getCollectedTreasures());
		TreasureMap treasureMap = treasureMapService.getTreasureMap();
		
		Field field1 = treasureMap.getFieldAt(treasurePosition1);
		Field field2 = treasureMap.getFieldAt(treasurePosition2);
		Field field3 = treasureMap.getFieldAt(treasurePosition3);
		
		assertEquals(0, field1.getNumberOfTreasures());
		assertEquals(1, field2.getNumberOfTreasures());
		assertEquals(3, field3.getNumberOfTreasures());

	}

	@Test
	public void testMultipleAdventurerExploration() {
		Position treasurePosition1 = new Position(1, 4);
		Position treasurePosition2 = new Position(1, 6);
		Position treasurePosition3 = new Position(4, 10);
		Position adventurer1StartingPosition = new Position(2, 4);
		Position adventurer2StartingPosition = new Position(1, 5);
		Position adventurer1LastPosition = new Position(1, 6);
		Position adventurer2LastPosition = new Position(1, 0);

		mountainsPositionList.add(new Position(2, 5));
		mountainsPositionList.add(new Position(0, 7));
		
		treasuresByPosition.put(treasurePosition1, 1);
		treasuresByPosition.put(treasurePosition2, 3);
		treasuresByPosition.put(treasurePosition3, 3);
		
		AdventurerData adventurerData1 = new AdventurerData("Adventurer 1", "S", "DDAAGAAADAAADAAGGA");
		AdventurerData adventurerData2 = new AdventurerData("Adventurer 2", "S", "AGADAAAA");
		adventurerDataByPosition.put(adventurer1StartingPosition, adventurerData1);
		adventurerDataByPosition.put(adventurer2StartingPosition, adventurerData2);
		
		Dimension dimension = new Dimension(5, 14);
		TreasureMapData treasureMapData = new TreasureMapData(dimension, mountainsPositionList, treasuresByPosition, adventurerDataByPosition);

		Mockito.when(parserMock.getTreasureMapData()).thenReturn(treasureMapData);

		treasureMapService.generateTreasureMapAndAdventurer();
		
		assertFalse(treasureMapService.getTreasureMap().getFieldAt(adventurer1StartingPosition).isAccessible());
		assertFalse(treasureMapService.getTreasureMap().getFieldAt(adventurer2StartingPosition).isAccessible());
		
		treasureMapService.simulate();
		
		
		Map<Position, Adventurer> adventurerByPosition = treasureMapService.getAdventurerByPosition();

		Adventurer adventurer = adventurerByPosition.get(adventurer1StartingPosition);
		assertTrue(adventurer == null);
		
		//Adventure 1 result
		adventurer = adventurerByPosition.get(adventurer1LastPosition);
		assertTrue(adventurer.getDirection() instanceof WestDirection);
		assertEquals(2, adventurer.getCollectedTreasures());
		TreasureMap treasureMap = treasureMapService.getTreasureMap();
		
		Field field1 = treasureMap.getFieldAt(treasurePosition1);
		Field field2 = treasureMap.getFieldAt(treasurePosition2);
		Field field3 = treasureMap.getFieldAt(treasurePosition3);
		
		assertEquals(0, field1.getNumberOfTreasures());
		assertEquals(1, field2.getNumberOfTreasures());
		assertEquals(3, field3.getNumberOfTreasures());
		
		//Adventure 2 result
		adventurer = adventurerByPosition.get(adventurer2LastPosition);
		assertTrue(adventurer.getDirection() instanceof SouthDirection);
		assertEquals(1, adventurer.getCollectedTreasures());
		
	}

	@Test
	public void testFromEndToEnd() throws IOException, UnparsableException {
		List<String> fileContent = Arrays.asList(
				"C - 3 - 4", 
				"M - 1 - 0", 
				"M - 2 - 1", 
				"T - 0 - 3 - 2",
				"T - 1 - 3 - 3",
				"A - Laura - 1 - 1 - N - AAGAGAGGA");
		File treasureMapFile = createFile(fileContent);
		TreasureMapParser parserMock = new TreasureMapParserImpl(treasureMapFile);
		treasureMapService = new TreasureMapService(parserMock);
		treasureMapService.extractData();
		treasureMapService.generateTreasureMapAndAdventurer();
		treasureMapService.simulate();
		
		TreasureMapWriter treasureMapWriter = new TreasureMapWriterImpl();
		File resultFile = treasureMapWriter.write(treasureMapService.getTreasureMap(), treasureMapService.getAdventurerByPosition());
		StringBuilder sb = new StringBuilder();
		Files.lines(resultFile.toPath()).forEach(content -> sb.append(content).append("\n"));
		
		String expectedResult = "C-3-4\n" +
								"M-2-1\n" +
								"M-1-0\n" +
								"T-1-3-2\n" +
								"A-Laura-0-3-N-3\n";
		assertEquals(expectedResult, sb.toString());
	}
	
	private File createFile(List<String> instructionList) {
		File file = null;
		try {
			file = temporaryFolder.newFile("treasureMap.txt");
			Files.write(file.toPath(), instructionList, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	
}
