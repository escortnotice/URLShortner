package com.shorturl.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shorturl.controller.URLInfoRestController;
import com.shorturl.model.URL_Info;

@SpringBootTest
@RunWith(SpringRunner.class)
public class URLShortner_IntegrationTest {

	@Autowired
	URLInfoRestController urlInfoRestController;
	
	private static final String longUrl = "itsaverylongurl.com";
	
	@Test
	public void checkShortenAndSaveURLInfo_test() {	
		//action to be tested
		URL_Info urlInfo = urlInfoRestController.checkShortenAndSaveURLInfo(longUrl);
		
		//Assert
		assertThat(urlInfo.getShortUrl().length()).isLessThan(longUrl.length());	
	}
	
	
	
}
