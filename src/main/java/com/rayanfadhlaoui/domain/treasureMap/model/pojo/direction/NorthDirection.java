package com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public class NorthDirection implements Direction {

	@Override
	public Direction rotateLeft() {
		return new WestDirection();
	}

	@Override
	public Direction rotateRight() {
		return new EastDirection();
	}

	@Override
	public Position move() {
		return new Position(0,1);
	}

	@Override
	public String toString() {
		return "N";
	}
}