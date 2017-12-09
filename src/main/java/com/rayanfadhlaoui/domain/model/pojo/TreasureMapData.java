package com.rayanfadhlaoui.domain.model.pojo;

import java.util.List;
import java.util.Map;

public class TreasureMapData {

	private final Dimension dimension;
	private final List<Position> mountainPositionList;
	private final Map<Position, Integer> treasuresByPosition;

	public TreasureMapData(Dimension dimension, List<Position> mountainPositionList, Map<Position, Integer> treasuresByPosition) {
		this.dimension = dimension;
		this.mountainPositionList = mountainPositionList;
		this.treasuresByPosition = treasuresByPosition;
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

}
