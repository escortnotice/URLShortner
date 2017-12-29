package com.shorturl.messageQueueService.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorturl.messageQueueService.RecordClickQueueMessageReceiverService;
import com.shorturl.model.Click_Info;
import com.shorturl.model.URL_Info;
import com.shorturl.repository.ClickInfoRepository;

@RunWith(MockitoJUnitRunner.class)
public class RecordClickQueueMessageReceiverServiceTest {
	
	//@InjectMocks
	 RecordClickQueueMessageReceiverService recordClickQueueMessageReceiverService;
	@Mock
	ClickInfoRepository clickInfoRepository;
	
	private static final String EXCHANGE = "link-exchange";
	private static final String QUEUE_NAME = "link-queue";
	private static final String TOPIC= "topic";
	
	private static final String longUrl = "www.google.com";
	private static final String shortUrl = "08316aaa";
	private static final URL_Info urlInfo = new URL_Info(longUrl, shortUrl, new Timestamp(1234l));
	private static final Click_Info clickInfo = new Click_Info(urlInfo, new Timestamp(345l));
	
	@Before
	public void setUp() {
		this.recordClickQueueMessageReceiverService = new RecordClickQueueMessageReceiverService(clickInfoRepository, new ObjectMapper());
	}
	
	@Test
	public void receiveMessage_Test_CheckingQueueConfigurationsAndAnnotationValues() throws Exception {
		Method receiveMessage = RecordClickQueueMessageReceiverService.class.getMethod("receiveMessage", String.class);
        RabbitListener annotation = receiveMessage.getAnnotation(RabbitListener.class);

        assertThat(annotation).isNotNull();
        QueueBinding[] queueBindings = annotation.bindings().clone();
        assertThat(queueBindings[0].value().value()).isEqualTo(QUEUE_NAME);
        assertThat(queueBindings[0].exchange().value()).isEqualTo(EXCHANGE);
        assertThat(queueBindings[0].exchange().type()).isEqualTo(TOPIC);
	}
	
	@Test
	public void receiveMessage_Test_dbSaveMethodCalled() throws Exception{
		
		//mock db method call
		when(clickInfoRepository.save(clickInfo)).thenReturn(clickInfo);
		
		//action to be tested
		ObjectMapper objectMapper2 = new ObjectMapper();
		recordClickQueueMessageReceiverService.receiveMessage(objectMapper2.writeValueAsString(clickInfo));
		
		//verify if the db method was called
		verify(clickInfoRepository).save(any(Click_Info.class));
	}

}
