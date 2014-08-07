package com.tshevchuk.prayer.data;

import android.text.TextUtils;

public class MenuItemPrayer extends MenuItemBase {
	public enum Type {
		Text, HtmlInTextView, HtmlInWebView
	};

	private static final long serialVersionUID = 1L;

	private String fileName;
	private String source;
	private String fullName;
	private Type type = Type.HtmlInTextView;
	private String htmlLinkAnchor;

	public MenuItemPrayer(int id, String name, String fileName) {
		super(id, name);
		this.fileName = fileName;
	}

	public MenuItemPrayer(int id, String name, String fileName, String source) {
		this(id, name, fileName);
		this.source = source;
	}

	public String getFileName() {
		return fileName;
	}

	public MenuItemPrayer setSource(String source) {
		this.source = source;
		return this;
	}

	public String getAbout() {
		return "Джерело тексту: " + source;
	}

	public String getSource() {
		return source;
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

	public MenuItemPrayer setType(Type type) {
		this.type = type;
		return this;
	}

	public Type getType() {
		return type;
	}

	public String getHtmlLinkAnchor() {
		return htmlLinkAnchor;
	}

	public void setHtmlLinkAnchor(String htmlLinkAnchor) {
		this.htmlLinkAnchor = htmlLinkAnchor;
	}
}