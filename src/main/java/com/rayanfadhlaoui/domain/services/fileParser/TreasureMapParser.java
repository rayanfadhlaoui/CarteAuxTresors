package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.TreasureMapData;

public interface TreasureMapParser extends AutoCloseable{
	
	public TreasureMapData extractData() throws FileNotFoundException, UnparsableException;
	
	public Dimension getDimension();
	
	public List<Position> getMountainsPosition();
	
	public Map<Position, Integer> getTreasuresByPosition();
	
}
