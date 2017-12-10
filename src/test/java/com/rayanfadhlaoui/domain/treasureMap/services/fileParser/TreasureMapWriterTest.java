package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.SouthDirection;

public class TreasureMapWriterTest {

	private TreasureMapWriter treasureMapWriter;

	@Before
	public void setUp() {
		treasureMapWriter = new TreasureMapWriterImpl();
	}

	@Test
	public void testRetrieveResult() throws IOException {
		TreasureMap treasureMap = new TreasureMap(new Dimension(4,5));
		treasureMap.addField(new Position(1,1), new Mountain());
		treasureMap.addField(new Position(2,2), new Mountain());
		treasureMap.addField(new Position(3,3), new Mountain());
		
		treasureMap.addTreasureToPosition(new Position(0,0), 4);
		
		Map<Position, Adventurer>  adventurerByPosition = new HashMap<>();
		
		adventurerByPosition.put(new Position(0,1), new Adventurer("Indiana", new SouthDirection(), Collections.emptyList()));
		File file = treasureMapWriter.write(treasureMap, adventurerByPosition);
	
		StringBuilder sb = new StringBuilder();
		Files.lines(file.toPath()).forEach(content -> sb.append(content).append("\n"));
		
		String expectedResult = "C-4-5\n" +
								"M-3-3\n" +
								"M-2-2\n" +
								"M-1-1\n" +
								"T-0-0-4\n" +
								"A-Indiana-0-1-S-0\n";
		assertEquals(expectedResult, sb.toString());
		
	}
}
