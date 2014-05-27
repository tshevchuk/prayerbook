package com.tshevchuk.prayer.data;

import android.text.TextUtils;


public class MenuItemPrayer extends MenuItemBase {
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String about;
	private String fullName;
	private boolean isHtml = true;

	public MenuItemPrayer(String name, String fileName) {
		super(name);
		this.fileName = fileName;
	}

	public MenuItemPrayer(String name, String fileName, String source) {
		this(name, fileName);
		setSource(source);
	}

	public String getFileName() {
		return fileName;
	}

	public MenuItemPrayer setSource(String source) {
		this.about = "Джерело тексту: " + source;
		return this;
	}

	public String getAbout() {
		return about;
	}

	public MenuItemPrayer setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getFullName() {
		if (TextUtils.isEmpty(fullName)) {
			return getName();
		}
		return fullName;
	}

	public MenuItemPrayer setIsHtml(boolean isHtml) {
		this.isHtml = isHtml;
		return this;
	}

	public boolean isHtml() {
		return isHtml;
	}
}