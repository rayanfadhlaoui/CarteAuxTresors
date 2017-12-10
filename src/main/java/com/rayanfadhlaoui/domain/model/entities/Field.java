package com.rayanfadhlaoui.domain.model.entities;

public interface Field {

	int getNumberOfTreasures();

	Field addTreasure(Integer numberOfTreasures);

	boolean isAccessible();

	int collectTreasure();

	void addAdventurer();

	void removeAdventurer();
}