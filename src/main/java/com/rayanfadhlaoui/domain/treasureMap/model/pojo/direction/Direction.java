package com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.Position;

public interface Direction {
	Direction rotateLeft();
	Direction rotateRight();
	Position move();
}
