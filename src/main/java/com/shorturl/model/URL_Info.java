package com.shorturl.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="URL_INFO")
@XmlRootElement
public class URL_Info {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty("url-id")
	private long url_id;
	
	@Column(name="long_url",nullable=false, length=255, unique=true)
	@JsonProperty("long-url")
	private String longUrl;
	
	@Column(name="short_url",nullable=false, length=255, unique=true)
	private String shortUrl;
	
	@Column(nullable=false)
	private Timestamp short_url_created_ts;

	
	//constructors
	public URL_Info() {
		//why jpa why ?
		super();
	}
	
	public URL_Info(String long_url, String short_url, Timestamp short_url_created_ts) {
		super();
		this.longUrl = long_url;
		this.shortUrl = short_url;
		this.short_url_created_ts = short_url_created_ts;
	}

	//setters and getters
	public long getUrl_id() {
		return url_id;
	}

	public void setUrl_id(long url_id) {
		this.url_id = url_id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public Timestamp getShort_url_created_ts() {
		return short_url_created_ts;
	}

	public void setShort_url_created_ts(Timestamp short_url_created_ts) {
		this.short_url_created_ts = short_url_created_ts;
	}

	
	@Override
	public String toString() {
		return "URL_Info [url_id=" + url_id + ", longUrl=" + longUrl + ", shortUrl=" + shortUrl
				+ ", short_url_created_ts=" + short_url_created_ts + "]";
	}

}
