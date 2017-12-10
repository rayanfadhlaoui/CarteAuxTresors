package com.rayanfadhlaoui.domain.model.pojo.direction;

import com.rayanfadhlaoui.domain.model.pojo.Position;

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

}
