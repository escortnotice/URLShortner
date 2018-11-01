package com.shorturl.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shorturl.model.URL_Info;
import com.shorturl.repository.URLInfoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest 										//Loads jpa relevant config; uses in-memory db by default
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 		//Runs the test cases in ascending order based on their names
public class URlInfoRepositoryTest {

	private static final String longUrl = "www.google.com";
	private static final String shortUrl = "08316aaa";
	private static final URL_Info urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1234l));
	
	@Autowired
	private URLInfoRepository urlInfoRepository;
	
	@After
	public void tearDown() {
		urlInfoRepository.deleteAll();
	}
	
	@Test
	public void _1_saveURLInfo_Test() {
		
		URL_Info urlInfo_ActualResult = urlInfoRepository.save(urlInfo);
		assertThat(urlInfo_ActualResult).isNotNull();
	}
	
	@Test
	public void _2_findByLongUrl() {
		urlInfoRepository.save(urlInfo);
		URL_Info urlInfo_ActualResult = urlInfoRepository.findByLongUrl(longUrl);
		assertThat(urlInfo_ActualResult.getShortUrl()).isEqualTo(shortUrl);
	}
	
	@Test
	public void _3_findByShortUrl() {
		urlInfoRepository.save(urlInfo);
		URL_Info urlInfo_ActualResult = urlInfoRepository.findByShortUrl(shortUrl);
		assertThat(urlInfo_ActualResult.getLongUrl()).isEqualTo(longUrl);
	}
	
	@Test
	public void _4_findAll() {
		urlInfoRepository.save(urlInfo);
		List<URL_Info> urlInfoList_ActualResult = urlInfoRepository.findAll();
		assertThat(urlInfoList_ActualResult.size()).isGreaterThan(0);
	}
	
}
