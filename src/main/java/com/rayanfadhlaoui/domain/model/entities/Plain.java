package com.rayanfadhlaoui.domain.model.entities;

public class Plain implements Field {
	
	public boolean hasAdventurer;
	int numberOfTreasures;

	public Plain() {
		hasAdventurer = false;
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
		return !hasAdventurer;
	}

	@Override
	public int collectTreasure() {
		if (numberOfTreasures > 0) {
			numberOfTreasures--;
			return 1;
		}
		return 0;
	}

	@Override
	public void addAdventurer() {
		hasAdventurer = true;
	}

	@Override
	public void removeAdventurer() {
		hasAdventurer = false;
	}
}
