package com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public class SouthDirection implements Direction{

	@Override
	public Direction rotateLeft() {
		return new EastDirection();
	}

	@Override
	public Direction rotateRight() {
		return new WestDirection();
	}

	@Override
	public Position move() {
		return new Position(0, -1);
	}

	@Override
	public String toString() {
		return "S";
	}
}
