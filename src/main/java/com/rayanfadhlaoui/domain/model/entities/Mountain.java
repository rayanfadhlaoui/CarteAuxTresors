package com.rayanfadhlaoui.domain.model.entities;

public class Mountain implements Field {
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Mountain;
	}
}
