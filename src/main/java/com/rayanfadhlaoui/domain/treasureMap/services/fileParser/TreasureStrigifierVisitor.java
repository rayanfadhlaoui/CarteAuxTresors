package com.rayanfadhlaoui.domain.treasureMap.services.fileParser;

import java.util.ArrayList;
import java.util.List;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Plain;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.utils.StringUtils;
import com.rayanfadhlaoui.domain.treasureMap.model.visitor.FieldVisitor;

public class TreasureStrigifierVisitor implements FieldVisitor {

	private final List<String> contentList;

	public TreasureStrigifierVisitor() {
		contentList = new ArrayList<>();
	}

	@Override
	public void visit(Mountain mountain, Position position) {
		// do nothing
	}

	@Override
	public void visit(Plain plain, Position position) {
		if (plain.getNumberOfTreasures() != 0) {
			StringBuilder sb = new StringBuilder("T").append("-")
					.append(position.getX()).append("-")
					.append(position.getY()).append("-")
					.append(plain.getNumberOfTreasures()).append(StringUtils.LINE_BREAK);
			contentList.add(sb.toString());
		}
	}

	public void completeContent(List<String> externalContentList) {
		contentList.forEach(externalContentList::add);
	}

}
