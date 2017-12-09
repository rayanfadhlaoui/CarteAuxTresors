package com.rayanfadhlaoui.domain.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.services.fileParser.TreasureMapParser;

public class TreasureMapService implements AutoCloseable{

	private TreasureMapParser parser;

	public TreasureMapService(TreasureMapParser parser) {
		this.parser = parser;
	}
	
	public TreasureMapData extractData() throws FileNotFoundException, UnparsableException {
		TreasureMapData treasureMapData = parser.extractData();
		return treasureMapData;
	}

	public TreasureMap createTreasureMap(TreasureMapData treasureMapData) {
		Dimension dimension = treasureMapData.getDimension();
		List<Position> mountainsPosition = treasureMapData.getMountainPositionList();
		Map<Position, Integer> treasuresByPosition = treasureMapData.getTreasuresByPosition();

		TreasureMap treasureMap = new TreasureMap(dimension);
		
		mountainsPosition.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition.forEach(treasureMap::addTreasureToPosition); 
		
		return treasureMap;
	}

	@Override
	public void close() throws Exception {
		parser.close();
	}

}