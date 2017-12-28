package com.shorturl.caching.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.shorturl.model.URL_Info;
import com.shorturl.repository.URLInfoRepository;
import com.shorturl.service.URLInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class URlInfoService_Caching_Test {

	@Autowired
	URLInfoService urlInfoService;
	@MockBean
	URLInfoRepository urlInfoRepository;

	private static final String longUrl = "www.google.com";
	private static final String shortUrl = "08316aaa";
	private static final URL_Info urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1234l));

	@Test
	public void checkShortenAndSaveURLInfo_caching_Test() {

		// mock the data for second db call
		URL_Info urlInfo2 = new URL_Info("www.yahoo.com", "a1234", new Timestamp(456l));

		// mock db service
		/*
		 * For the first db call "urlInfo" will be returned. 
		 * For the second db call, if caching is enabled then "urlInfo" will again be called. 
		 * If caching is disabled, then for the second db call "urlInfo2" should be returned.
		 * 
		 * In our test it should always return "urlInfo" because caching is enabled and
		 * present on the service layer method call.Hence the framework will identify
		 * that already a db call was made from this service method, so caching
		 * framework will return the first (Cached) object and will not call the
		 * repository service the second time.
		 * 
		 */
		when(urlInfoRepository.findByLongUrl(longUrl)).thenReturn(urlInfo, urlInfo2);
		
		//actions to be tested(directly test the cacheable method, testing the method calling the cacheable method was returning false)
		URL_Info first_fetch = urlInfoService.getShortUrlForLongUrl(longUrl);
		URL_Info second_fetch = urlInfoService.getShortUrlForLongUrl(longUrl);
		
		
		//Assert that first_fetch and second_fetch are the same objects returned
		assertThat(first_fetch).isEqualTo(urlInfo);
		assertThat(second_fetch).isEqualTo(urlInfo);
		
		//verify that the urlInfoRepository service was called only once due to caching enabled
		verify(urlInfoRepository, times(1)).findByLongUrl(longUrl);
	
		
	
		

	}
}
