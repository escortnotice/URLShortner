package com.shorturl.messageQueueService.test;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorturl.messageQueueService.RecordClickQueueMessageReceiverService;
import com.shorturl.messageQueueService.RecordClickQueueMessageSenderService;
import com.shorturl.model.URL_Info;

@RunWith(MockitoJUnitRunner.class)
public class RecordClickQueueMessageSenderServiceTest {

	@InjectMocks
	RecordClickQueueMessageSenderService recordClickQueueMessageSenderService;
	@Mock
	RabbitTemplate rabbitTemplate;
	@Mock
	ObjectMapper objectMapper;
	
	private static final String longUrl = "www.google.com";
	private static final String shortUrl = "08316aaa";
	private static final URL_Info urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1234l));
	
	
	@Test
	public void pushToQueueTest() {
		
		//mock call to queue to send message
		doNothing().when(rabbitTemplate).convertAndSend(anyString(),anyString());
		
		//action to be tested
		recordClickQueueMessageSenderService.pushToQueue(urlInfo, new Timestamp(124l));
		
		//verify if the method was called once, to send message to the queue
		verify(rabbitTemplate).convertAndSend(anyString(),anyString());
	}
}
