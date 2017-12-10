package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.entities.TreasureMap;
import com.rayanfadhlaoui.domain.model.pojo.Position;

public interface TreasureMapWriter {

	File write(TreasureMap treasureMap, Map<Position, Adventurer> adventurerByPosition) throws IOException;

}
