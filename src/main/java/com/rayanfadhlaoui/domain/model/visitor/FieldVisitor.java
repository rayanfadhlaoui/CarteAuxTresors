package com.rayanfadhlaoui.domain.model.visitor;

import com.rayanfadhlaoui.domain.model.entities.Mountain;
import com.rayanfadhlaoui.domain.model.entities.Plain;
import com.rayanfadhlaoui.domain.model.pojo.Position;

public interface FieldVisitor {
	public void visit(Mountain mountain, Position position);
	public void visit(Plain pain, Position position);
}
