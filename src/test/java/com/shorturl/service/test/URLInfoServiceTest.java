package com.shorturl.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.shorturl.messageQueueService.RecordClickQueueMessageSenderService;
import com.shorturl.model.URL_Info;
import com.shorturl.repository.URLInfoRepository;
import com.shorturl.service.URLInfoService;

@RunWith(MockitoJUnitRunner.class)
public class URLInfoServiceTest {

	private static final String longUrl = "www.google.com";
	private static final String shortUrl = "08316aaa";
	private static final URL_Info urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1234l));
	private static final ArrayList<URL_Info> urlList = new ArrayList<>(Arrays.asList(urlInfo));

	@InjectMocks
	URLInfoService urlInfoService;
	@Mock
	URLInfoRepository urlInfoRepository;
	@Mock
	RecordClickQueueMessageSenderService recordClickQueueMessageSenderService;

	@Test
	public void checkShortenAndSaveURLInfo_Test_whenLongUrlNotExistInDB() {

		// mock the db calls
		when(urlInfoRepository.findByLongUrl(anyString())).thenReturn(null);
		when(urlInfoRepository.save(any(URL_Info.class))).thenReturn(urlInfo);

		// action to be tested
		URL_Info actual_url_info = urlInfoService.checkShortenAndSaveURLInfo(longUrl);

		// Assert
		assertThat(urlInfo.getShortUrl()).isEqualTo(actual_url_info.getShortUrl());

		// verifies if the db services were called once
		verify(urlInfoRepository).findByLongUrl(anyString());
		verify(urlInfoRepository).save(any(URL_Info.class));

	}

	@Test
	public void checkShortenAndSaveURLInfo_Test_whenLongUrlExistInDB() {
		// mock the db calls
		when(urlInfoRepository.findByLongUrl(anyString())).thenReturn(urlInfo);
		when(urlInfoRepository.save(any(URL_Info.class))).thenReturn(urlInfo);

		// action to be tested
		URL_Info actual_url_info = urlInfoService.checkShortenAndSaveURLInfo(longUrl);

		// Assert
		assertThat(urlInfo.getShortUrl()).isEqualTo(actual_url_info.getShortUrl());

		// verifies if the db services were called once
		verify(urlInfoRepository).findByLongUrl(anyString());
	}

	@Test
	public void getLongUrlAndPushToQueue_Test_whenShortUrlExistsInDB() {

		//mock the db calls
		when(urlInfoRepository.findByShortUrl(anyString())).thenReturn(urlInfo);
		//mock the queue pushing call (has void return type)
		doNothing().when(recordClickQueueMessageSenderService).pushToQueue(anyObject(), anyObject());
		
		//action to be tested
		URL_Info actual_url_info = urlInfoService.getLongUrlAndPushToQueue(shortUrl);
		
		//Assert
		assertThat(urlInfo.getLongUrl()).isEqualTo(actual_url_info.getLongUrl());
		
		//verify the db service was called once
		verify(urlInfoRepository).findByShortUrl(anyString());
		//verify the push to queue method was called once
		verify(recordClickQueueMessageSenderService).pushToQueue(anyObject(), anyObject());
		
	}
	
	@Test
	public void getLongUrlAndPushToQueue_Test_whenShortUrlNotExistsInDB() {

		//mock the db calls
		when(urlInfoRepository.findByShortUrl(anyString())).thenReturn(null);
		//mock the queue pushing call (has void return type)
		doNothing().when(recordClickQueueMessageSenderService).pushToQueue(anyObject(), anyObject());
		
		//action to be tested
		URL_Info actual_url_info = urlInfoService.getLongUrlAndPushToQueue(shortUrl);
		
		//Assert
		assertThat(actual_url_info).isEqualTo(null);
		
		//verify the db service was called once
		verify(urlInfoRepository).findByShortUrl(anyString());
		
	}
	
	@Test
	public void getAllUrls_Test() {
		
		//mock the db call
		when(urlInfoRepository.findAll()).thenReturn(urlList);
		
		//action to be tested
		 List<URL_Info> actualUrlInfoList = urlInfoService.getAllUrls();
		 
		 //Assert
		 assertThat(actualUrlInfoList.size()).isGreaterThan(0);
		 
		 //verify that db service was called once
		 verify(urlInfoRepository).findAll();
	}

}
