package com.tshevchuk.prayer.domain.model;

public abstract class MenuItemBase {

	String name;
	int id;
	int parentItemId;
	boolean isOfficialUGCCText;

	public MenuItemBase() {
	}

	MenuItemBase(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public MenuItemBase(MenuItemBase item) {
		name = item.name;
		id = item.id;
		parentItemId = item.parentItemId;
		isOfficialUGCCText = item.isOfficialUGCCText;
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

	void setParentItemId(int parentItemId) {
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