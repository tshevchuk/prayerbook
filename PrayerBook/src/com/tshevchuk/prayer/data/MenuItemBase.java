package com.tshevchuk.prayer.data;

import java.io.Serializable;

public abstract class MenuItemBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private int id;

	public MenuItemBase(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public int getId(){
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
}