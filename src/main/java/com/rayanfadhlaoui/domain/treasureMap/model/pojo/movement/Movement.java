package com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.Direction;

public abstract class Movement {

	protected int x;
	protected int y;
	protected Direction direction;

	public Movement() {
		x = 0;
		y = 0;
	}

	public Position move(Position position) {
		return new Position(position.getX() + x, position.getY() + y);
	}

	public Direction getDirection() {
		return direction;
	}

	public abstract void move(Direction direction);
}
