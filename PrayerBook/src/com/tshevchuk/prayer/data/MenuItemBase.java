package com.tshevchuk.prayer.data;

import java.io.Serializable;

public abstract class MenuItemBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;

	public MenuItemBase(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}