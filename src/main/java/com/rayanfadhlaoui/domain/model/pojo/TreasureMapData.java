package com.rayanfadhlaoui.domain.model.pojo;

import java.util.List;
import java.util.Map;

public class TreasureMapData {

	private final Dimension dimension;
	private final List<Position> mountainPositionList;
	private final Map<Position, Integer> treasuresByPosition;
	private final Map<Position, AdventurerData> adventurerDataByPosition;

	public TreasureMapData(Dimension dimension, List<Position> mountainPositionList, Map<Position, Integer> treasuresByPosition, Map<Position, AdventurerData> adventurerDataByPosition) {
		this.dimension = dimension;
		this.mountainPositionList = mountainPositionList;
		this.treasuresByPosition = treasuresByPosition;
		this.adventurerDataByPosition = adventurerDataByPosition;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public List<Position> getMountainPositionList() {
		return mountainPositionList;
	}

	public Map<Position, Integer> getTreasuresByPosition() {
		return treasuresByPosition;
	}

	public Map<Position, AdventurerData> getAdventurerDataByPosition() {
		return adventurerDataByPosition;
	}

}
