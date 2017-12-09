package com.rayanfadhlaoui.domain.model.factory;

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
	public void testCreationWithSingleDigit() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 3 - 4"));
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			TreasureMap treasureMap = treasureMapFactory.createTreasureMap();

			assertEquals(3, treasureMap.getWidth());
			assertEquals(4, treasureMap.getHeight());
		}
	}

	@Test
	public void testCreationWithMultipleDigit() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 13 - 44"));
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			TreasureMap treasureMap = treasureMapFactory.createTreasureMap();

			assertEquals(13, treasureMap.getWidth());
			assertEquals(44, treasureMap.getHeight());
		}
	}

	@Test
	public void testCreation_FirstLineEmpty() throws Exception {
		File treasureMapFile = createFile(Collections.emptyList());
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			treasureMapFactory.createTreasureMap();
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
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			TreasureMap treasureMap = treasureMapFactory.createTreasureMap();
			Field field = treasureMap.getFieldAt(new Position(1, 1));
			assertEquals(field, new Mountain());
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
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			TreasureMap treasureMap = treasureMapFactory.createTreasureMap();
			Field field = treasureMap.getFieldAt(new Position(0, 0));
			assertEquals(5, field.getNumberOfTreasures());
		}
	}

	public void testCreation_WithTresuresDoesNotMatchRegex() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "T - 0 -0-  5-8"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testCreation_WithTresuresOnMountain() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 4 - 5", "T - 0 -0-  5", "M -0 -0"));
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			TreasureMap treasureMap = treasureMapFactory.createTreasureMap();
			Field field = treasureMap.getFieldAt(new Position(0, 0));
			assertEquals(0, field.getNumberOfTreasures());
		}
	}

	@Test
	public void testCreation_WithWrongSize() throws Exception {
		File treasureMapFile = createFile(Collections.singletonList("C - 0 - 5"));
		assertThatUnparsableExceptionIsThrown(treasureMapFile);
	}

	@Test
	public void testCreation_MountainWrongPosition() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 1 - 1", "M -10-10"));
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			treasureMapFactory.createTreasureMap();
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}
	}

	@Test
	public void testCreation_TreasureWrongPosition() throws Exception {
		File treasureMapFile = createFile(Arrays.asList("C - 1 - 1", "T -10-10-5"));
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			treasureMapFactory.createTreasureMap();
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}
	}

	private void assertThatUnparsableExceptionIsThrown(File treasureMapFile) throws Exception {
		try (TreasureMapFactory treasureMapFactory = new TreasureMapFactory(treasureMapFile)) {
			treasureMapFactory.createTreasureMap();
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
