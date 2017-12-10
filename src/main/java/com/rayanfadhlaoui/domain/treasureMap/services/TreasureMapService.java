package com.rayanfadhlaoui.domain.treasureMap.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Field;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.treasureMap.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction.Instruction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.Movement;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.convertor.AdventurerConvertor;
import com.rayanfadhlaoui.domain.treasureMap.services.fileParser.TreasureMapParser;

public class TreasureMapService {

	private TreasureMapParser parser;
	private Map<Position, Adventurer> adventurerByPosition;
	private TreasureMap treasureMap;

	public TreasureMapService(TreasureMapParser parser) {
		this.parser = parser;
		adventurerByPosition = new HashMap<>();
	}

	public void extractData() throws UnparsableException, IOException {
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

	public TreasureMap getTreasureMap() {
		return treasureMap;
	}

	private void generateAdventures(TreasureMapData treasureMapData) {
		Map<Position, AdventurerData> adventurerDataByPosition = treasureMapData.getAdventurerDataByPosition();
		adventurerDataByPosition.forEach((position, adventurerData) -> {
			adventurerByPosition.put(position, AdventurerConvertor.convert(adventurerData));
			treasureMap.getFieldAt(position).addAdventurer();
		});
	}

	private void generateTreasureMap(TreasureMapData treasureMapData) {
		Dimension dimension = treasureMapData.getDimension();
		List<Position> mountainsPosition = treasureMapData.getMountainPositionList();
		Map<Position, Integer> treasuresByPosition = treasureMapData.getTreasuresByPosition();

		treasureMap = new TreasureMap(dimension);

		mountainsPosition.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition.forEach(treasureMap::addTreasureToPosition);
	}

	public void simulate() {
		boolean isOver = false;
		while (!isOver) {
			isOver = simulateTurn();
		}
	}

	private boolean simulateTurn() {
		//We can remove or add data from adventurerByPosition while we iterate over it.
		//So the runnable is used to do it after the iteration.
		List<Runnable> adventurerByPositionUpdaterRunnable = new ArrayList<>();

		adventurerByPosition.forEach((currentPosition, adventurer) -> {
			if (!adventurer.explorationIsOver()) {
				Position newPosition = getAdventurerNewPosition(currentPosition, adventurer);
				moveAdventurer(adventurerByPositionUpdaterRunnable, currentPosition, adventurer, newPosition);
			}
		});

		//
		adventurerByPositionUpdaterRunnable.forEach(Runnable::run);

		boolean explorationIsOver = adventurerByPosition.values().stream().allMatch(Adventurer::explorationIsOver);
		return explorationIsOver;
	}

	private void moveAdventurer(List<Runnable> adventurerByPositionUpdater, Position currentPosition, Adventurer adventurer, Position newPosition) {
		if (!currentPosition.equals(newPosition) && treasureMap.isAccessible(newPosition)) {

			adventurer.collectTreasure(treasureMap.getFieldAt(newPosition));

			adventurerByPositionUpdater.add(() -> {

				adventurerByPosition.put(newPosition, adventurer);
				Field fieldNewPosition = treasureMap.getFieldAt(newPosition);
				Field fieldCurrentPosition = treasureMap.getFieldAt(currentPosition);

				fieldNewPosition.addAdventurer();
				fieldCurrentPosition.removeAdventurer();

				adventurerByPosition.remove(currentPosition);
			});
		}
	}

	private Position getAdventurerNewPosition(Position position, Adventurer adventurer) {
		Instruction instruction = adventurer.getNextInstruction();
		Movement movement = instruction.getMovement(adventurer.getDirection());
		Position newPosition = movement.move(position);
		adventurer.setDirection(movement.getDirection());
		return newPosition;
	}

}