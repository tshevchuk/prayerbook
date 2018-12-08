package com.tshevchuk.prayer.domain.model;

import com.tshevchuk.prayer.data.Catalog;

import org.parceler.Parcel;

@Parcel
public class MenuItemPrayer extends MenuItemBase {
	private String fileName;
	private String source;
	private Type type = Type.HtmlInTextView;
	private String htmlLinkAnchor;
	private String audioUrl;
	private String audioSource;
	private int audioStartPosition;

	public MenuItemPrayer() {
	}

	public MenuItemPrayer(int id, String name, String fileName) {
		super(id, name);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSource() {
		return source;
	}

	@SuppressWarnings("UnusedReturnValue")
	public MenuItemPrayer setSource(String source) {
		this.source = source;
		return this;
	}

	public String getAudioSource(){
		return audioSource;
	}

	public Type getType() {
		return type;
	}

	public MenuItemPrayer setType(Type type) {
		this.type = type;
		return this;
	}

	public String getHtmlLinkAnchor() {
		return htmlLinkAnchor;
	}

	public void setHtmlLinkAnchor(String htmlLinkAnchor) {
		this.htmlLinkAnchor = htmlLinkAnchor;
	}

	public MenuItemPrayer setAudio(String url, String source){
		audioUrl = Catalog.AUDIO_BASE_URL + url;
		audioSource = source;
		return this;
	}

	public MenuItemPrayer setAudioStartPosition(int startPosition){
		audioStartPosition = startPosition;
		return this;
	}

	public int getAudioStartPosition(){
		return audioStartPosition;
	}

	public String getAudioUrl(){
		return audioUrl;
	}

	public enum Type {
		Text, HtmlInTextView, HtmlInWebView
	}
}