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

	private TreasureMap generateTreasureMap() {
		Dimension dimension = parser.getDimension();
		TreasureMap treasureMap = new TreasureMap(dimension.getWidth(), dimension.getHeight());
		List<Position> mountainsPosition = parser.getMountainsPosition();
		Map<Position, Integer> treasuresByPosition2 = parser.getTreasuresByPosition();
		mountainsPosition.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition2.forEach(treasureMap::addTreasureToPosition); 
		
		return treasureMap;
	}

	@Override
	public void close() throws Exception {
		parser.close();
		/*if(bufferedReader != null) {
			bufferedReader.close();
			fileReader.close();
		}*/
	}
}