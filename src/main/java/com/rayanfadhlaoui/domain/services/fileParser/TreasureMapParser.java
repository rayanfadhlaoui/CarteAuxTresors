package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.FileNotFoundException;

import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;

public interface TreasureMapParser extends AutoCloseable{
	
	public void extractData() throws FileNotFoundException, UnparsableException;
	
	public TreasureMapData getTreasureMapData();
	
}
