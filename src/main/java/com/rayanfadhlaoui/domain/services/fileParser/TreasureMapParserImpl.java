package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.AdventurerData;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;
import com.rayanfadhlaoui.domain.model.utils.StringUtils;
import com.rayanfadhlaoui.domain.model.utils.TreasureMapPattern;

public class TreasureMapParserImpl implements TreasureMapParser {

	private static final String LINE_DOES_NOT_MATCH_ANY_PATTERN = "Line does not match any pattern";

	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private TreasureMapData treasureMapData;
	private final TreasureMapPattern treasureMapPattern;

	public TreasureMapParserImpl(File file) throws FileNotFoundException {
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		treasureMapPattern = new TreasureMapPattern();
	}

	@Override
	public void extractData() throws FileNotFoundException, UnparsableException {
		List<Position> mountainPositionList = new ArrayList<>();
		Map<Position, Integer> treasuresByPosition = new HashMap<>();
		Map<Position, AdventurerData> adventurerDataByPosition = new HashMap<>();
		Dimension dimension = extractMapDimension();
		extractOtherData(mountainPositionList, treasuresByPosition, adventurerDataByPosition);

		treasureMapData = new TreasureMapData(dimension, mountainPositionList, treasuresByPosition, adventurerDataByPosition);
	}

	@Override
	public TreasureMapData getTreasureMapData() {
		return treasureMapData;
	}

	@Override
	public Map<Position, Adventurer> getAdventurerByPosition() {
		return null;
	}

	@Override
	public void close() throws Exception {
		if (bufferedReader != null) {
			bufferedReader.close();
			fileReader.close();
		}
	}

	private Dimension extractMapDimension() throws UnparsableException {
		try {
			String line = bufferedReader.readLine();
			line = StringUtils.removeWhiteSpace(line);
			if (checkFirstLineConsistency(line)) {
				String[] splitLine = line.split("-");
				int width = Integer.valueOf(splitLine[1]);
				int height = Integer.valueOf(splitLine[2]);
				return new Dimension(width, height);
			}
		} catch (IOException e) {
			throw new UnparsableException(e.getMessage());
		}
		return null;
	}

	private boolean checkFirstLineConsistency(String line) throws UnparsableException {
		if (line == null) {
			throw new UnparsableException("The file is empty !");
		}

		if (!treasureMapPattern.isMapConfiguration(line)) {
			throw new UnparsableException(LINE_DOES_NOT_MATCH_ANY_PATTERN);
		}

		return true;
	}

	private void extractOtherData(	List<Position> mountainPositionList, Map<Position, Integer> treasuresByPosition,
									Map<Position, AdventurerData> adventurerDataByPosition) throws UnparsableException {
		String currentLine;
		try {
			while ((currentLine = StringUtils.removeWhiteSpace(bufferedReader.readLine())) != null) {
				if (treasureMapPattern.isMountain(currentLine)) {
					extractMontainPosition(currentLine, mountainPositionList);
				} else if (treasureMapPattern.isTreasure(currentLine)) {
					extractTreasuresPosition(currentLine, treasuresByPosition);
				} else if (treasureMapPattern.isAdventurer(currentLine)) {
					extractAdventurerDataPosition(currentLine, adventurerDataByPosition);
				} else if (!treasureMapPattern.isComment(currentLine)) {
					throw new UnparsableException(LINE_DOES_NOT_MATCH_ANY_PATTERN);
				}
			}
		} catch (IOException e) {
			throw new UnparsableException(e.getMessage());
		}
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

}
