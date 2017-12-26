package com.shorturl.controller.test;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.shorturl.controller.URLInfoRestController;
import com.shorturl.model.URL_Info;
import com.shorturl.service.URLInfoService;

@RunWith(SpringRunner.class)
@WebMvcTest(URLInfoRestController.class)
public class URLInfoRestControllerTest {

	String longUrl = "www.google.com";
	String shortUrl = "ac123456";
	URL_Info mockUrlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1514312742983l));
	ArrayList<URL_Info> mockUrlInfoList = new ArrayList<>();

	@Autowired
	MockMvc mockMVC;

	@MockBean
	URLInfoService urlInfoService;

	@Test
	public void checkShortenAndSaveURLInfoTest_True() throws Exception {
		// mock service call
		when(urlInfoService.checkShortenAndSaveURLInfo(longUrl)).thenReturn(mockUrlInfo);

		// action to be tested
		ResultActions resultActions = mockMVC.perform(get("/url-service/shorten").param("longUrl", longUrl));

		// Assert
		resultActions.andExpect(status().isCreated())
				.andExpect(jsonPath("shortUrl").value(shortUrl))
				.andExpect(jsonPath("long-url").value(longUrl));

	}

	@Test
	public void getLongUrlTest_True() throws Exception {
		// mock service call
		when(urlInfoService.getLongUrlAndPushToQueue(shortUrl)).thenReturn(mockUrlInfo);

		// Action to be tested
		ResultActions resultActions = mockMVC.perform(get("/url-service/fullUrl").param("shortUrl", shortUrl));

		// Assert
		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("shortUrl").value(shortUrl))
				.andExpect(jsonPath("long-url").value(longUrl));

	}

	@Test
	public void getAllUrlsTest_True() throws Exception {
		//mock return data
		mockUrlInfoList.add(mockUrlInfo);
		
		//moc service call
		when(urlInfoService.getAllUrls()).thenReturn(mockUrlInfoList);
		
		//Action to be tested
		ResultActions resultActions = mockMVC.perform(get("/url-service/AllUrls"));
		
		//Assert
		resultActions.andExpect(status().isOk())
					 .andExpect(content().json("[{\"shortUrl\":\"ac123456\",\"short_url_created_ts\":1514312742983,\"url-id\":0,\"long-url\":\"www.google.com\"}]"));
		
	}
}
