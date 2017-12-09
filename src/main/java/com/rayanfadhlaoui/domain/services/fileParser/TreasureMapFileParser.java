package com.rayanfadhlaoui.domain.services.fileParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.exception.UnparsableException;
import com.rayanfadhlaoui.domain.model.pojo.Dimension;
import com.rayanfadhlaoui.domain.model.pojo.Position;

public interface TreasureMapFileParser{
	
	public void init(File file) throws FileNotFoundException;
	
	public void extractData() throws FileNotFoundException, UnparsableException;
	
	public Dimension getDimension();
	
	public List<Position> getMountainsPosition();
	
	public Map<Position, Integer> getTreasuresByPosition();
	
	public void close() throws Exception;
}
