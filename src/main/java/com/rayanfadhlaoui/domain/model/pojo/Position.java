package com.rayanfadhlaoui.domain.model.pojo;

public class Position {

	private final int x;
	private final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position position = (Position) o;
			return x == position.x && y == position.getY();
		}
		return false;
	}

	@Override
	public int hashCode() {
		int res = 31;
		res += 31 * x;
		res += 31 * y;
		return res;
	}
}
