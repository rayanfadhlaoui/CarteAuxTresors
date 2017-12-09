package com.rayanfadhlaoui.domain.model.entities;

import java.util.HashMap;
import java.util.Map;

import com.rayanfadhlaoui.domain.model.pojo.Position;

public class TreasureMap {
	private static final String OUT_OF_BOUND_MESSAGE = "You've reached too far !";
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
	 * Retrieve a field at a certain position.
	 * 
	 * @param position
	 *            The position
	 * @return The field
	 * 
	 * @throws IndexOutOfBoundsException
	 *             throws IndexOutOfBoundsException if the position is outside the
	 *             treasure map
	 */
	public Field getFieldAt(Position position) {
		Field field = fieldByPosition.get(position);
		if (field == null) {
			throw new IndexOutOfBoundsException(OUT_OF_BOUND_MESSAGE);
		}
		return field;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Add the field to the position.
	 * 
	 * @param position
	 *            The position.
	 * @param field
	 *            The field.
	 * 
	 * @throws IndexOutOfBoundsException
	 *             throws IndexOutOfBoundsException if the position is outside the
	 *             treasure map
	 */
	public void addField(Position position, Field field) {
		if (fieldByPosition.get(position) == null) {
			throw new IndexOutOfBoundsException(OUT_OF_BOUND_MESSAGE);
		}
		fieldByPosition.put(position, field);
	}

	public void addTreasureToPosition(Position position, Integer numberOfTreasures) {
		Field field = getFieldAt(position).addTreasure(numberOfTreasures);
		addField(position, field);
	}
}
