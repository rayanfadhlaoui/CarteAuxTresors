package com.rayanfadhlaoui.domain.model.factory;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.services.fileParser.TreasureMapFileParser;

public class TreasureMapFactory implements AutoCloseable{

	private final TreasureMapFileParser parser;

	public TreasureMapFactory(TreasureMapFileParser parser) {
		this.parser = parser;
	}

	public TreasureMap createTreasureMap() throws FileNotFoundException, UnparsableException {
		parser.extractData();
		TreasureMap treasureMap = generateTreasureMap();
		return treasureMap;
	}

	@Override
	public void close() throws Exception {
		parser.close();
	}
	
	private TreasureMap generateTreasureMap() {
		Dimension dimension = parser.getDimension();
		List<Position> mountainsPosition = parser.getMountainsPosition();
		Map<Position, Integer> treasuresByPosition = parser.getTreasuresByPosition();

		TreasureMap treasureMap = new TreasureMap(dimension.getWidth(), dimension.getHeight());
		
		mountainsPosition.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition.forEach(treasureMap::addTreasureToPosition); 
		
		return treasureMap;
	}

}