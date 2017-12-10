package com.rayanfadhlaoui.domain.treasureMap.model.visitor;

import com.rayanfadhlaoui.domain.treasureMap.model.entities.Mountain;
import com.rayanfadhlaoui.domain.treasureMap.model.entities.Plain;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public interface FieldVisitor {
	public void visit(Mountain mountain, Position position);
	public void visit(Plain pain, Position position);
}
