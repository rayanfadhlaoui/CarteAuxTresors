package com.rayanfadhlaoui.domain.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.rayanfadhlaoui.domain.model.entities.Field;
import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.Plain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.services.TreasureMapService;
import com.rayanfadhlaoui.domain.services.fileParser.TreasureMapParser;

public class TreasureMapServicesTest {

	private TreasureMapParser parser;
	private Map<Position, Integer> treasuresByPosition;
	private List<Position> mountainsPositionList;
	private TreasureMapService treasureMapService;

	@Before
	public void setUp() throws FileNotFoundException {
		parser = Mockito.mock(TreasureMapParser.class);
		mountainsPositionList = new ArrayList<>();
		treasuresByPosition = new HashMap<>();

		treasureMapService = new TreasureMapService(parser);
	}

	@Test
	public void testTreasureMapCreationOk() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(0, 4), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition);

		TreasureMap treasureMap = treasureMapService.createTreasureMap(treasureMapData);
		Field field = treasureMap.getFieldAt(new Position(1, 1));
		assertEquals(field, new Mountain());
		assertEquals(0, field.getNumberOfTreasures());
		field = treasureMap.getFieldAt(new Position(0, 4));
		assertEquals(field, new Plain());
		assertEquals(5, field.getNumberOfTreasures());
	}

	@Test
	public void testTreasureMapCreationPutTreasureOnMountain() {
		mountainsPositionList.add(new Position(1, 1));
		treasuresByPosition.put(new Position(1, 1), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition);

		TreasureMap treasureMap = treasureMapService.createTreasureMap(treasureMapData);
		Field field = treasureMap.getFieldAt(new Position(1, 1));
		assertEquals(field, new Mountain());
		assertEquals(0, field.getNumberOfTreasures());
	}

	@Test
	public void testCreation_MountainWrongPosition() throws Exception {
		mountainsPositionList.add(new Position(10, 10));
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition);

		try {
			treasureMapService.createTreasureMap(treasureMapData);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}

	}

	@Test
	public void testCreation_TreasureWrongPosition() throws Exception {
		treasuresByPosition.put(new Position(10, 10), 5);
		TreasureMapData treasureMapData = new TreasureMapData(new Dimension(4, 5), mountainsPositionList, treasuresByPosition);

		try {
			treasureMapService.createTreasureMap(treasureMapData);
			fail("IndexOutOfBoundsException expected");
		} catch (IndexOutOfBoundsException e) {
			assertEquals("You've reached too far !", e.getMessage());
		}
	}

}
