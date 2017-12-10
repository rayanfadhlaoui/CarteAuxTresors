package com.rayanfadhlaoui.domain.model.pojo.direction;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public interface Direction {
	Direction rotateLeft();
	Direction rotateRight();
	Position move();
}
