package com.tshevchuk.prayer.domain.model;

public abstract class MenuItemBase {

	private String name;
	private int id;
	private int parentItemId;
	private boolean isOfficialUGCCText;

	MenuItemBase() {
	}

	protected MenuItemBase(int id, String name) {
		this.name = name;
		this.id = id;
	}

	MenuItemBase(MenuItemBase item) {
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