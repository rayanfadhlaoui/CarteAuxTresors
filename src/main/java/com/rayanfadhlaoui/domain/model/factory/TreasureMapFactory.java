package com.rayanfadhlaoui.domain.model.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.utils.StringUtils;

public class TreasureMapFactory implements AutoCloseable{

	private static final String DIGIT_BIGGER_THAN_ZERO = "[1-9]+[0-9]*";
	private static final String LINE_DOES_NOT_MATCH_ANY_PATTERN = "Line does not match any pattern";
	private final Pattern mapPattern = Pattern.compile("C-" + DIGIT_BIGGER_THAN_ZERO + "-" + DIGIT_BIGGER_THAN_ZERO);
	private final Pattern mountainPattern = Pattern.compile("M-\\d*-\\d*");
	private final Pattern treasurePattern = Pattern.compile("T-\\d*-\\d*-\\d*");

	private final File treasureMapFile;
	private BufferedReader bufferedReader;
	private FileReader fileReader;
	private int width;
	private int height;
	private final List<Position> mountainPositionList;
	private final Map<Position, Integer> treasuresByPosition;

	public TreasureMapFactory(File treasureMapFile) {
		this.treasureMapFile = treasureMapFile;
		mountainPositionList = new ArrayList<>();
		treasuresByPosition = new HashMap<>();
	}

	public TreasureMap createTreasureMap() throws FileNotFoundException, UnparsableException {
		extractDataFromFile();
		TreasureMap treasureMap = generateTreasureMap();
		return treasureMap;
	}

	private void extractDataFromFile() throws FileNotFoundException, UnparsableException {
		initBufferedReader();
		extractWidthAndHeight();
		extractMountainAndTresorPosition();
	}

	private TreasureMap generateTreasureMap() {
		TreasureMap treasureMap = new TreasureMap(width, height);
		mountainPositionList.forEach(position -> treasureMap.addField(position, new Mountain()));
		treasuresByPosition.forEach(treasureMap::addTreasureToPosition); 
		
		return treasureMap;
	}

	private void initBufferedReader() throws FileNotFoundException {
		fileReader = new FileReader(treasureMapFile);
		bufferedReader = new BufferedReader(fileReader);
	}

	private void extractMountainAndTresorPosition() throws UnparsableException {
		String currentLine;
		try {
			while ((currentLine = StringUtils.removeWhiteSpace(bufferedReader.readLine())) != null) {
				Matcher mountainMatcher = mountainPattern.matcher(currentLine);
				Matcher treasureMatcher = treasurePattern.matcher(currentLine);
				if (mountainMatcher.matches()) {
					extractMontainPosition(currentLine);
				} 
				else if (treasureMatcher.matches()) {
					extractTreasuresPosition(currentLine);
				} 
				else {
					throw new UnparsableException(LINE_DOES_NOT_MATCH_ANY_PATTERN);
				}
			}
		} catch (IOException e) {
			throw new UnparsableException(e.getMessage());
		}
	}

	private void extractMontainPosition(String line) {
		String[] splitLine = line.split("-");
		int x = Integer.valueOf(splitLine[1]);
		int y = Integer.valueOf(splitLine[2]);
		mountainPositionList.add(new Position(x, y));
	}

	private void extractTreasuresPosition(String line) {
		String[] splitLine = line.split("-");
		int x = Integer.valueOf(splitLine[1]);
		int y = Integer.valueOf(splitLine[2]);
		int numberOTreasures = Integer.valueOf(splitLine[3]);
		treasuresByPosition.put(new Position(x, y), numberOTreasures);
	}

	private void extractWidthAndHeight() throws UnparsableException {
		try {
			String line = bufferedReader.readLine();
			line = StringUtils.removeWhiteSpace(line);
			if (checkFirstLineConsistency(line)) {
				String[] splitLine = line.split("-");
				width = Integer.valueOf(splitLine[1]);
				height = Integer.valueOf(splitLine[2]);
			}
		} catch (IOException e) {
			throw new UnparsableException(e.getMessage());
		}
	}

	private boolean checkFirstLineConsistency(String firstLine) throws UnparsableException {
		if (firstLine == null) {
			throw new UnparsableException("The file is empty !");
		}

		Matcher matcher = mapPattern.matcher(firstLine);
		if (!matcher.matches()) {
			throw new UnparsableException(LINE_DOES_NOT_MATCH_ANY_PATTERN);
		}

		return true;
	}

	@Override
	public void close() throws Exception {
		if(bufferedReader != null) {
			bufferedReader.close();
			fileReader.close();
		}
	}
}