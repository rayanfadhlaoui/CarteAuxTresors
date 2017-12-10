package com.rayanfadhlaoui.domain.treasureMap.model.pojo.instruction;

import com.rayanfadhlaoui.domain.treasureMap.model.pojo.direction.Direction;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.Movement;
import com.rayanfadhlaoui.domain.treasureMap.model.pojo.movement.RotateRightMovement;

public class TurnRightInstruction implements Instruction{

	@Override
	public Movement getMovement(Direction direction) {
		Movement movement = new RotateRightMovement();
		movement.move(direction);
		return movement;
	}

}
