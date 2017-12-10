package com.rayanfadhlaoui.domain.model.pojo.movement;

import com.rayanfadhlaoui.domain.model.entities.Adventurer;
import com.rayanfadhlaoui.domain.model.pojo.Position;
import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;

public abstract class Movement {

	protected int x;
	protected int y;
	protected Direction direction;
	
	public Movement() {
		x=0;
		y=0;
	}

	public Position move(Position position) {
		return new Position(position.getX() + x, position.getY() + y);
	}
	
	public void changeDirection(Adventurer adventurer) {
		adventurer.setDirection(direction);
	}

	public abstract void move(Direction direction);
}
