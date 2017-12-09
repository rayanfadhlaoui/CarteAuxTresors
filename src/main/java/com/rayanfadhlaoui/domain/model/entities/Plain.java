package com.rayanfadhlaoui.domain.model.entities;

public class Plain implements Field{
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Plain;
	}
}	
