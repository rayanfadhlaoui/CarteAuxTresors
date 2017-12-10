package com.rayanfadhlaoui.domain.model.entities;

import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.visitor.FieldVisitor;

public interface Field {

	int getNumberOfTreasures();

	Field addTreasure(Integer numberOfTreasures);

	boolean isAccessible();

	int collectTreasure();

	void addAdventurer();

	void removeAdventurer();

	void visitWithPosition(FieldVisitor visitor, Position position);

}