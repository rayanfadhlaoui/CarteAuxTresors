package com.rayanfadhlaoui.domain.model.entities;

public class Plain implements Field{
	final int numberOfTreasures;
	
	public Plain() {
		this.numberOfTreasures = 0;
	}

	public Plain(Integer numberOfTreasures) {
		this.numberOfTreasures = numberOfTreasures;
	}

	@Override
	public int getNumberOfTreasures() {
		return numberOfTreasures;
	}

	@Override
	public Field addTreasure(Integer numberOfTreasures) {
		return new Plain(numberOfTreasures);
	}

	@Override
	public boolean isAccessible() {
		return true;
	}
}	
