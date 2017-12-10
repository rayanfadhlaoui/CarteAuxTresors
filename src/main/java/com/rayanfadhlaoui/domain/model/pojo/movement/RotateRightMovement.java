package com.rayanfadhlaoui.domain.model.pojo.movement;

import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;

public class RotateRightMovement extends Movement {

	@Override
	public void move(Direction direction) {
		this.direction = direction.rotateRight();
	}

}
