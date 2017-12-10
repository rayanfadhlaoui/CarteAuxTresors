package com.rayanfadhlaoui.domain.model.pojo.instruction;

import com.rayanfadhlaoui.domain.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.model.pojo.movement.Movement;

public interface Instruction {

	Movement getMovement(Direction direction);
}
