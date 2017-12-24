package com.shorturl.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CLICK_INFO")
public class Click_Info {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "url_click_id")
	private long urlClickId;

	// many to one mapping (many clicks can be made on one url)
	@ManyToOne
	@JoinColumn(name = "url_id", nullable = false)
	private URL_Info urlInfo;

	@Column(name = "clicked_ts")
	private Timestamp clickedTimstamp;

	
	// constructors
	public Click_Info() {
		super();
	}
	
	public Click_Info(URL_Info urlInfo, Timestamp clickedTimstamp) {
		super();
		this.urlInfo = urlInfo;
		this.clickedTimstamp = clickedTimstamp;
	}


	// setters and getters
	public long getUrlClickId() {
		return urlClickId;
	}

	public void setUrlClickId(long urlClickId) {
		this.urlClickId = urlClickId;
	}

	public Timestamp getClickedTimstamp() {
		return clickedTimstamp;
	}

	public void setClickedTimstamp(Timestamp clickedTimstamp) {
		this.clickedTimstamp = clickedTimstamp;
	}

	public URL_Info getUrlInfo() {
		return urlInfo;
	}

	public void setUrlInfo(URL_Info urlInfo) {
		this.urlInfo = urlInfo;
	}

}
