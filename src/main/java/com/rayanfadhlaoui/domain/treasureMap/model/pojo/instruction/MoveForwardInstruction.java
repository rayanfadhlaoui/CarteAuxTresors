package com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.MoveForward;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.Movement;

public class MoveForwardInstruction implements Instruction{

	@Override
	public Movement getMovement(Direction direction) {
		Movement movement = new MoveForward();
		movement.move(direction);
		return movement;
	}

}
