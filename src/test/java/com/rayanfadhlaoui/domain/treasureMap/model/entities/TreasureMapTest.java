package com.rayanfadhlaoui.domain.treasureMap.model.entities;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public class TreasureMapTest {
	
	private TreasureMap treasureMap;

	@Before
	public void setUp() {
		treasureMap = new TreasureMap(new Dimension(10,10));
	}
	
	@Test
	public void testCreationMap() {
		assertTrue(treasureMap.getFieldAt(new Position(0,0)) instanceof Plain);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetFieldOutOfBound() {
		treasureMap.getFieldAt(new Position(0,10));
	}
}
