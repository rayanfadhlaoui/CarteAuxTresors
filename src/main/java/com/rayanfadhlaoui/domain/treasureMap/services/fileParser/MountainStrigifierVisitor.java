package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.util.ArrayList;
import java.util.List;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Plain;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.StringUtils;
import com.rayanfadhlaoui.domain.treasureMap.model.visitor.FieldVisitor;

public class MountainStrigifierVisitor implements FieldVisitor{

	private final List<String>  contentList;

	public MountainStrigifierVisitor() {
		contentList = new ArrayList<>();
	}

	@Override
	public void visit(Mountain mountain, Position position) {
		StringBuilder sb = new StringBuilder("M").append("-")
		.append(position.getX()).append("-")
		.append(position.getY()).append(StringUtils.LINE_BREAK);
		contentList.add(sb.toString());
	}

	@Override
	public void visit(Plain pain, Position position) {
		//do nothing
	}

	public void completeContent(List<String> externalContentList) {
		contentList.forEach(externalContentList::add);
	}
	
}
