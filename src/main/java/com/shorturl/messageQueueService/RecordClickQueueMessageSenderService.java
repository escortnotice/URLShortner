package com.shorturl.messageQueueService;

import java.sql.Timestamp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorturl.model.Click_Info;
import com.shorturl.model.URL_Info;

import static com.shorturl.messageQueueService.RecordClickQueueMessageReceiverService.QUEUE_NAME;

@Component
public class RecordClickQueueMessageSenderService {

	@Autowired
	RabbitTemplate rabbitTemplateQueue;
	@Autowired
	ObjectMapper objectMapper;
	
	/* push to queue when any short url is called 
	 * - record the url which was called
	 * - record the timestamp when it was called
	 */
	@Async
	public void pushToQueue(URL_Info urlInfo, Timestamp urlAccessedTimestamp) {	
		System.out.println("Pushing to Queue");
		Click_Info clickInfo = new Click_Info(urlInfo,urlAccessedTimestamp);
		System.out.println("In Queue Messgage Sender Service");
		push(clickInfo);
	}

	private void push(Click_Info clickInfo) {
		//rabbitTemplateQueue.convertAndSend(QUEUE_NAME, clickInfo.getUrlInfo().getUrl_id());
		/*
		 * Convert the object to json string and then send it to the queue
		 */
		//ObjectMapper mapper = new ObjectMapper();
		try {
			String clickInfo_json = objectMapper.writeValueAsString(clickInfo);
			System.out.println("clickInfo in json format in Queue Sender: " + clickInfo_json);
			rabbitTemplateQueue.convertAndSend(QUEUE_NAME,clickInfo_json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}