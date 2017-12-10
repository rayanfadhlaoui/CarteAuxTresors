package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.treasureMap.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.StringUtils;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.TreasureMapPattern;

public class TreasureMapParserImpl implements TreasureMapParser {

	private static final String LINE_DOES_NOT_MATCH_ANY_PATTERN = "Line does not match any pattern";

	private TreasureMapData treasureMapData;
	private final TreasureMapPattern treasureMapPattern;
	private Dimension dimension;
	private final File file;

	public TreasureMapParserImpl(File file) throws FileNotFoundException {
		this.file = file;
		treasureMapPattern = new TreasureMapPattern();
	}

	@Override
	public void extractData() throws UnparsableException, IOException {
		List<Position> mountainPositionList = new ArrayList<>();
		Map<Position, Integer> treasuresByPosition = new HashMap<>();
		Map<Position, AdventurerData> adventurerDataByPosition = new HashMap<>();
		List<String> allLines = Files.readAllLines(file.toPath());
		if (allLines.isEmpty()) {
			throw new UnparsableException("The file is empty !");
		}
		
		StatusHandler statusHandler = new StatusHandler();

		allLines.forEach((currentLine) -> {
			currentLine = StringUtils.removeWhiteSpace(currentLine);
			if (treasureMapPattern.isMapConfiguration(currentLine)) {
				extractMapDimension(currentLine);
			} else if (treasureMapPattern.isMountain(currentLine)) {
				extractMontainPosition(currentLine, mountainPositionList);
			} else if (treasureMapPattern.isTreasure(currentLine)) {
				extractTreasuresPosition(currentLine, treasuresByPosition);
			} else if (treasureMapPattern.isAdventurer(currentLine)) {
				extractAdventurerDataPosition(currentLine, adventurerDataByPosition);
			} else if (!treasureMapPattern.isComment(currentLine)) {
				statusHandler.setStatusKo();
			}
		});
		
		if(!statusHandler.statusOK) {
			throw new UnparsableException(LINE_DOES_NOT_MATCH_ANY_PATTERN);
		}
		
		treasureMapData = new TreasureMapData(dimension, mountainPositionList, treasuresByPosition, adventurerDataByPosition);
	}

	@Override
	public TreasureMapData getTreasureMapData() {
		return treasureMapData;
	}

	private void extractMapDimension(String line) {
		String[] splitLine = line.split("-");
		int width = Integer.valueOf(splitLine[1]);
		int height = Integer.valueOf(splitLine[2]);
		dimension = new Dimension(width, height);
	}

	private void extractAdventurerDataPosition(String line, Map<Position, AdventurerData> adventurerDataByPosition) {
		String[] splitLine = line.split("-");
		String name = splitLine[1];
		int x = Integer.valueOf(splitLine[2]);
		int y = Integer.valueOf(splitLine[3]);
		String direction = splitLine[4];
		String intructions = splitLine[5];
		AdventurerData adventurerData = new AdventurerData(name, direction, intructions);
		adventurerDataByPosition.put(new Position(x, y), adventurerData);
	}

	private void extractMontainPosition(String line, List<Position> mountainPositionList) {
		String[] splitLine = line.split("-");
		int x = Integer.valueOf(splitLine[1]);
		int y = Integer.valueOf(splitLine[2]);
		mountainPositionList.add(new Position(x, y));
	}

	private void extractTreasuresPosition(String line, Map<Position, Integer> treasuresByPosition) {
		String[] splitLine = line.split("-");
		int x = Integer.valueOf(splitLine[1]);
		int y = Integer.valueOf(splitLine[2]);
		int numberOTreasures = Integer.valueOf(splitLine[3]);
		treasuresByPosition.put(new Position(x, y), numberOTreasures);
	}

	class StatusHandler {
		private boolean statusOK;

		public StatusHandler() {
			statusOK = true;
		}
		
		void setStatusKo(){
			statusOK = false;
		}
		
		boolean getStatus(){
			return statusOK;
		}
	}

}
