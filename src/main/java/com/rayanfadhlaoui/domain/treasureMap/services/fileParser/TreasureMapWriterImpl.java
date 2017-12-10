package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.StringUtils;

public class TreasureMapWriterImpl implements TreasureMapWriter {

	private File file;

	public TreasureMapWriterImpl() {
		file = new File("result.txt");
	}

	@Override
	public File write(TreasureMap treasureMap, Map<Position, Adventurer> adventurerByPosition) throws IOException {
		List<String> contentList = new ArrayList<>();
		stringify(treasureMap, contentList);
		stringify(adventurerByPosition, contentList);

		StringBuilder sb = new StringBuilder();
		contentList.forEach(sb::append);
		Files.write(file.toPath(), sb.toString().getBytes());
		return file;
	}

	private void stringify(Map<Position, Adventurer> adventurerByPosition, List<String> contentList) {

		adventurerByPosition.forEach((position, adventurer) -> {
			StringBuilder sb = new StringBuilder("A").append("-")
					.append(adventurer.getName()).append("-")
					.append(position.getX()).append("-")
					.append(position.getY()).append("-")
					.append(adventurer.getDirection()).append("-")
					.append(adventurer.getCollectedTreasures()).append(StringUtils.LINE_BREAK);
			contentList.add(sb.toString());
		});
	}

	private void stringify(TreasureMap treasureMap, List<String> contentList) {
		strigifyMapConfiguration(treasureMap, contentList);
		treasureMap.strigifyMountainsAndTreasures(contentList);
	}

	private void strigifyMapConfiguration(TreasureMap treasureMap, List<String> contentList) {
		StringBuilder sb = new StringBuilder("C").append("-")
				.append(treasureMap.getWidth()).append("-")
				.append(treasureMap.getHeight()).append(StringUtils.LINE_BREAK);
		contentList.add(sb.toString());
	}

}
