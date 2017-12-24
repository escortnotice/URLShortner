package com.shorturl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shorturl.model.URL_Info;
import com.shorturl.service.URLInfoService;

@RestController
@RequestMapping("/url-service")
public class URLInfoRestController {
	
	@Autowired
	URLInfoService urlInfoService;

	//shorten the long url
	@GetMapping("/shorten")
	@ResponseStatus(code=HttpStatus.CREATED)
	public URL_Info checkShortenAndSaveURLInfo(@RequestParam("longUrl") String long_Url) {
		return urlInfoService.checkShortenAndSaveURLInfo(long_Url.trim());
	}
	
	//get the long url for the short url requested
	@GetMapping("/fullUrl")
	@ResponseStatus(code=HttpStatus.OK)
	public URL_Info getLongUrl(@RequestParam("shortUrl") String short_url) {
		URL_Info urlInfo = urlInfoService.getLongUrlAndPushToQueue(short_url.trim());
		return urlInfo;
	}
	
	//get all the urls in the db
	@GetMapping("/AllUrls")
	@ResponseStatus(code=HttpStatus.OK)
	public List<URL_Info> getAllUrls(){
		return urlInfoService.getAllUrls();
	}
	
}
