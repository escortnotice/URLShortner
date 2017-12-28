package com.shorturl.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.shorturl.messageQueueService.RecordClickQueueMessageSenderService;
import com.shorturl.model.URL_Info;
import com.shorturl.repository.URLInfoRepository;

@Service
public class URLInfoService {

	@Autowired
	private URLInfoRepository urlInfoRepository;
	@Autowired
	private RecordClickQueueMessageSenderService recordClickQueueService;

	
	/*
	 * 1. check if the long url exists in db 2. if no entry found then get a short
	 * url generated 3. save it in the db
	 */
	public URL_Info checkShortenAndSaveURLInfo(String longUrl) {

		URL_Info urlInfo = getShortUrlForLongUrl(longUrl);
		if (urlInfo == null) {
			String shortUrl = generateShortURL(longUrl);
			urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(System.currentTimeMillis()));
			urlInfoRepository.save(urlInfo);
		}
		return urlInfo;
	}

	/*
	 * Check if the short url exists in db If yes, -push the url accessed and the
	 * timestamp of its access to the queue -then return the short url
	 * 
	 * If No, then return the empty object
	 */
	public URL_Info getLongUrlAndPushToQueue(String shortUrl) {
		URL_Info urlInfo = getLongUrlForShortUrl(shortUrl);
		if (urlInfo != null) {
			System.out.println("Got Long Url: " + urlInfo.getLongUrl());
			recordClickQueueService.pushToQueue(urlInfo, new Timestamp(System.currentTimeMillis()));
			System.out.println("Click information pushed to Queue successfully--");
		}
		return urlInfo;
	}

	// get All Urls
	@Cacheable("allUrls")
	public List<URL_Info> getAllUrls() {
		return urlInfoRepository.findAll();
	}

	// generate a short url as hashcode of the string
	private String generateShortURL(String longUrl) {
		// String shortUrl = Integer.toString(longUrl.trim().hashCode());
		String shortUrl = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
		return shortUrl;
	}

	// get short url from db for the respective long url
	@Cacheable("shortUrls")
	private URL_Info getShortUrlForLongUrl(String longUrl) {
		return urlInfoRepository.findByLongUrl(longUrl);
	}

	// get long url from db for the respective short url
	@Cacheable("longUrls")
	private URL_Info getLongUrlForShortUrl(String shortUrl) {
		return urlInfoRepository.findByShortUrl(shortUrl);
	}

}
