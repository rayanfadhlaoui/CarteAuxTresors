package com.rayanfadhlaoui.domain.services;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.model.utils.convertor.AdventurerConvertor;
import com.rayanfadhlaoui.domain.services.fileParser.TreasureMapParser;

public class TreasureMapParserService implements AutoCloseable{

	private TreasureMapParser parser;
	private Map<Position, Adventurer> adventurerByPosition;
	private TreasureMap treasureMap;

	public TreasureMapParserService(TreasureMapParser parser) {
		this.parser = parser;
		adventurerByPosition = new HashMap<>();
	}
	
	public void extractData() throws FileNotFoundException, UnparsableException {
		parser.extractData();
	}

	public void generateTreasureMapAndAdventurer() {
		TreasureMapData treasureMapData = parser.getTreasureMapData();
		generateTreasureMap(treasureMapData); 
		generateAdventures(treasureMapData);
	}
	
	public Map<Position, Adventurer> getAdventurerByPosition() {
		return adventurerByPosition;
	}

	@Override
	public void close() throws Exception {
		parser.close();
	}

	public TreasureMap getTreasureMap() {
		return treasureMap;
	}
	
	private void generateAdventures(TreasureMapData treasureMapData) {
		Map<Position, AdventurerData> adventurerDataByPosition = treasureMapData.getAdventurerDataByPosition();
		adventurerDataByPosition.forEach((position, adventurerData) -> adventurerByPosition.put(position, AdventurerConvertor.convert(adventurerData)));
	}

	private void generateTreasureMap(TreasureMapData treasureMapData) {
		Dimension dimension = treasureMapData.getDimension();
		List<Position> mountainsPosition = treasureMapData.getMountainPositionList();
		Map<Position, Integer> treasuresByPosition = treasureMapData.getTreasuresByPosition();

		treasureMap = new TreasureMap(dimension);
		
		mountainsPosition.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition.forEach(treasureMap::addTreasureToPosition);
	}

}