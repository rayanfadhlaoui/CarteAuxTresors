package com.rayanfadhlaoui.domain.services.fileParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.services.TreasureMapParserService;

public class TreasureMapParserTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testCreationWithSingleDigit() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 3 - 4"));
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			TreasureMapData treasureMapData = parser.getTreasureMapData();
			Dimension dimension = treasureMapData.getDimension();
			assertEquals(3, dimension.getWidth());
			assertEquals(4, dimension.getHeight());
		}
	}

	@Test
	public void testCreationWithMultipleDigit() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 13 - 44"));
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			TreasureMapData treasureMapData = parser.getTreasureMapData();
			Dimension dimension = treasureMapData.getDimension();
			assertEquals(13, dimension.getWidth());
			assertEquals(44, dimension.getHeight());
		}
	}

	@Test
	public void testCreation_FirstLineEmpty() throws Exception {
		File treasureMapFile = createFile(Collections.emptyList());
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			fail("UnparsableException expected");
		} catch (UnparsableException e) {
			assertEquals("The file is empty !", e.getMessage());
		}
	}

	@Test
	public void testCreation_FirstDoesNotMatchRegex() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - -4 - 5"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testCreation_WithMountainOk() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "M -1 -1"));
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			TreasureMapData treasureMapData = parser.getTreasureMapData();
			List<Position> mountainPositionList = treasureMapData.getMountainPositionList();
			Position position = mountainPositionList.get(0);
			assertEquals(1, mountainPositionList.size());
			assertEquals(new Position(1, 1), position);
		}
	}

	@Test
	public void testCreation_WithMountainDoesNotMatchRegex() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "M -u -1"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testCreation_WithTresuresOk() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "T - 0 -0-  5"));
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			TreasureMapData treasureMapData = parser.getTreasureMapData();
			Map<Position, Integer> treasuresByPosition = treasureMapData.getTreasuresByPosition();
			int numberOfTreasures = treasuresByPosition.get(new Position(0, 0));
			assertEquals(5, numberOfTreasures);
		}
	}

	@Test
	public void testCreation_WithAdventurer() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "A - Indiana - 0 - 4 - N -  AADGAA"));
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			Map<Position, AdventurerData> adventurerDataByPosition = parser.getTreasureMapData().getAdventurerDataByPosition();
			AdventurerData adventurerData = adventurerDataByPosition.get(new Position(0, 4));
			assertEquals("Indiana", adventurerData.getName());
			assertEquals("N", adventurerData.getDirection());
			String instructions = adventurerData.getInstructions();
			assertEquals(6, instructions.length());
			assertEquals("AADGAA", instructions);
		}
	}

	public void testCreation_WithTresuresDoesNotMatchRegex() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "T - 0 -0-  5-8"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testCreation_WithWrongSize() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 0 - 5"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testParserCloseCorrectly() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 1 - 5"));

		TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile);
		TreasureMapParser parserSpy = Mockito.spy(parser);
		try (TreasureMapParserService treasureMapService = new TreasureMapParserService(parserSpy)) {
			treasureMapService.extractData();
		}

		Mockito.verify(parserSpy).close();

	}

	@Test
	public void testCreation_WithComment() throws Exception {
		String mapCreation = "C - 4 - 5";
		String comment = "#  {A comme Aventurier} - {Nom de l’aventurier} - {Axe horizontal} - {Axe vertical} - {Orientation} - {Séquence de mouvement} ";
		String treasurePosition = "T - 3 -4-  50";
		File treasureMapFile = createFile(Arrays.asList(mapCreation, comment, treasurePosition));

		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			TreasureMapData treasureMapData = parser.getTreasureMapData();
			Map<Position, Integer> treasuresByPosition = treasureMapData.getTreasuresByPosition();
			int numberOfTreasures = treasuresByPosition.get(new Position(3, 4));
			assertEquals(50, numberOfTreasures);
		}
	}

	private void assertThatUnparsableExceptionIsThrown(File treasureMapFile) throws Exception {
		try (TreasureMapParser parser = new TreasureMapParserImpl(treasureMapFile)) {
			parser.extractData();
			fail("UnparsableException expected");
		} catch (UnparsableException e) {
			assertEquals("Line does not match any pattern", e.getMessage());
		}
	}

	private File createFile(List<String> instructionList) {
		File file = null;
		try {
			file = temporaryFolder.newFile("treasureMap.txt");
			test(file, instructionList);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	private void test(File file, List<String> instructionsList) {
		try {
			Files.write(file.toPath(), instructionsList, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
