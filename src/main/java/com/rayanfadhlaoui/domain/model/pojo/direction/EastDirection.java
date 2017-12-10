package com.rayanfadhlaoui.domain.model.pojo.direction;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public class EastDirection implements Direction {

	@Override
	public Direction rotateLeft() {
		return new NorthDirection();
	}

	@Override
	public Direction rotateRight() {
		return new SouthDirection();
	}

	@Override
	public Position move() {
		return new Position(1,0);
	}

	@Override
	public String toString() {
		return "E";
	}
}