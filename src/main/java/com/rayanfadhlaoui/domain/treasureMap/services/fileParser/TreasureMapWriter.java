package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public interface TreasureMapWriter {

	File write(TreasureMap treasureMap, Map<Position, Adventurer> adventurerByPosition) throws IOException;

}
