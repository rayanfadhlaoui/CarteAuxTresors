package com.rayanfadhlaoui.domain.model.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public class TreasureMapTest {
	
	private TreasureMap treasureMap;

	@Before
	public void setUp() {
		treasureMap = new TreasureMap(10, 10);
	}
	
	@Test
	public void testCreationMap() {
		Plain plain = new Plain();
		
		assertEquals(plain, treasureMap.getFieldAt(new Position(0,0)));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetFieldOutOfBound() {
		treasureMap.getFieldAt(new Position(0,10));
	}
}
