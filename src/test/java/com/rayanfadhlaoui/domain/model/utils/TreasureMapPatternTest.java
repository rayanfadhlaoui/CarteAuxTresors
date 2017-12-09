package com.rayanfadhlaoui.domain.model.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TreasureMapPatternTest {
	
	private TreasureMapPattern treasureMapPattern;

	@Before
	public void setUp() {
		treasureMapPattern = new TreasureMapPattern();
	}
	
	@Test
	public void testMapConfigurationPatternOK() {
		String currentLine = "C-4-5";
		assertTrue(treasureMapPattern.isMapConfiguration(currentLine));
	}
	
	@Test
	public void testMountainPatternOK() {
		String currentLine = "M-7-0";
		assertTrue(treasureMapPattern.isMountain(currentLine));
	}
	
	@Test
	public void testCommentPatternOK() {
		String currentLine = "# This is a long comment";
		assertTrue(treasureMapPattern.isComment(currentLine));
	}
	
	@Test
	public void testTreasurePatternOK() {
		String currentLine = "T-1-0-5";
		assertTrue(treasureMapPattern.isTreasure(currentLine));
	}
	
	@Test
	public void testAdventurerPatternOK() {
		String currentLine = "A-Indiana-0-4-N-AADGAA";
		assertTrue(treasureMapPattern.isAdventurer(currentLine));
	}
	
	@Test
	public void testMapConfigurationPatternKO() {
		String currentLine = "D-4-5";
		assertFalse(treasureMapPattern.isMapConfiguration(currentLine));
		
		currentLine = "D-4-";
		assertFalse(treasureMapPattern.isMapConfiguration(currentLine));
		
		currentLine = "D--5";
		assertFalse(treasureMapPattern.isMapConfiguration(currentLine));
	}
	
	@Test
	public void testAdventurerPatternKO() {
		String currentLine = "A-Indiana-0-4-N-AACDGAA";
		assertFalse(treasureMapPattern.isAdventurer(currentLine));
		
		currentLine = "A-Indiana-0-4-Y-AADGAA";
		assertFalse(treasureMapPattern.isAdventurer(currentLine));
		
		currentLine = "A-Ind4iana-0-4-N-AADGAA";
		assertFalse(treasureMapPattern.isAdventurer(currentLine));
	}

	@Test
	public void testTreasurePatternKO() {
		String currentLine = "T-1--5";
		assertFalse(treasureMapPattern.isTreasure(currentLine));
	}
}
