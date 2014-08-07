package com.tshevchuk.prayer.data;

import java.io.Serializable;

public abstract class MenuItemBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private int id;
	private int parentItemId;
	private boolean isOfficialUGCCText;

	public MenuItemBase(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(int parentItemId) {
		this.parentItemId = parentItemId;
	}

	public boolean isOfficialUGCCText() {
		return isOfficialUGCCText;
	}

	public MenuItemBase setOfficialUGCCText(boolean isOfficialUGCCText) {
		this.isOfficialUGCCText = isOfficialUGCCText;
		return this;
	}
}