package com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.Movement;

public interface Instruction {

	Movement getMovement(Direction direction);
}
