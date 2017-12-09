package com.rayanfadhlaoui.domain.model.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public class TreasureMap {
	private final Map<Position, Field> fieldByPosition;
	private final int width;
	private final int height;

	public TreasureMap(int width, int height) {
		this.width = width;
		this.height = height;
		fieldByPosition = createFieldByPosition();

	}

	private Map<Position, Field> createFieldByPosition() {
		Map<Position, Field> localFieldByPosition = new HashMap<>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Position position = new Position(x, y);
				localFieldByPosition.put(position, new Plain());
			}
		}
		return localFieldByPosition;
	}

	/**
	 * Retrieve the field at a certain position
	 * 
	 * @param position
	 *            The position
	 * @return The field
	 */
	public Field getFieldAt(Position position) {
		Field field = fieldByPosition.get(position);
		if (field == null) {
			throw new IndexOutOfBoundsException("You've reached too far !");
		}
		return field;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void addField(Position position, Field field) {
		fieldByPosition.put(position, field);
	}
}
