package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.io.IOException;

import com.rayanfadhlaoui.domain.treasureMap.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.TreasureMapData;

public interface TreasureMapParser{
	
	public void extractData() throws UnparsableException, IOException;
	
	public TreasureMapData getTreasureMapData();
	
}
