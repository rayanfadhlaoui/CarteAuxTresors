package com.rayanfadhlaoui.domain.model.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.rayanfadhlaoui.domain.model.entities.Field;
import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Position;

public class TreasureMapFactoryTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testCreationWithSingleDigit() throws FileNotFoundException, UnparsableException {
		File treasureMapFile = createFile(Collections.singletonList("C - 3 - 4"));
		TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile);
		TreasureMap treasureMap = treasureMapFactory.createTreasureMap();

		assertEquals(3, treasureMap.getWidth());
		assertEquals(4, treasureMap.getHeight());
	}

	@Test
	public void testCreationWithMultipleDigit() throws FileNotFoundException, UnparsableException {
		File treasureMapFile = createFile(Collections.singletonList("C - 13 - 44"));
		TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile);
		TreasureMap treasureMap = treasureMapFactory.createTreasureMap();

		assertEquals(13, treasureMap.getWidth());
		assertEquals(44, treasureMap.getHeight());
	}

	@Test
	public void testCreation_FirstLineEmpty() throws FileNotFoundException {
		File treasureMapFile = createFile(Collections.emptyList());
		TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile);
		try {
			treasureMapFactory.createTreasureMap();
			fail("UnparsableException was not thrown");
		} catch (UnparsableException e) {
			assertEquals("The file is empty !", e.getMessage());
		}
	}

	@Test
	public void testCreation_FirstDoesNotMatchRegex() throws FileNotFoundException {
		File treasureMapFile = createFile(Collections.singletonList("C - -4 - 5"));
		TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile);
		try {
			treasureMapFactory.createTreasureMap();
			fail("UnparsableException was not thrown");
		} catch (UnparsableException e) {
			assertEquals("First line does not match with the pattern 'C-[0-9]*-[0-9]*'", e.getMessage());
		}
	}

	@Test
	public void testCreation_WithMountainOk() throws FileNotFoundException, UnparsableException {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "M -1 -1"));
		TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile);
		TreasureMap treasureMap = treasureMapFactory.createTreasureMap();
		Field field = treasureMap.getFieldAt(new Position(1,1));
		assertEquals(field, new Mountain());
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
