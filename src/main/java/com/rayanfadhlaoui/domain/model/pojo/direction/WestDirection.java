package com.rayanfadhlaoui.domain.model.pojo.direction;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public class WestDirection implements Direction {

	@Override
	public Direction rotateLeft() {
		return new SouthDirection();
	}

	@Override
	public Direction rotateRight() {
		return new NorthDirection();
	}

	@Override
	public Position move() {
		return new Position(-1, 0);
	}

	@Override
	public String toString() {
		return "O";
	}
}