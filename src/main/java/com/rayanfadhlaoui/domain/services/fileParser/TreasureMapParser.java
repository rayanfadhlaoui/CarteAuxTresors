package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.FileNotFoundException;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;

public interface TreasureMapParser extends AutoCloseable{
	
	public void extractData() throws FileNotFoundException, UnparsableException;
	
	public TreasureMapData getTreasureMapData();
	
	public Map<Position, Adventurer> getAdventurerByPosition();
	
}
