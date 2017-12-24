package com.shorturl.messageQueueService;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shorturl.model.Click_Info;
import com.shorturl.model.URL_Info;
import com.shorturl.repository.ClickInfoRepository;
import com.shorturl.repository.URLInfoRepository;

@Component
public class RecordClickQueueMessageReceiverService {

	@Autowired
	ClickInfoRepository clickInfoRepository;
	@Autowired
	URLInfoRepository urlInfoRepository;
	@Autowired
	ObjectMapper objectMapper;

    public static final String EXCHANGE = "link-exchange";
    public static final String QUEUE_NAME = "link-queue";
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME, durable = "true"),
            exchange = @Exchange(value = EXCHANGE, type = ExchangeTypes.TOPIC, durable="true")))
//    public void receiveMessage(long url_id) {
//    	System.out.println("Queue Receiver invoked...");
//    	if(url_id > 0l) {
//    		System.out.println("url_id: "+ url_id);
//        	URL_Info urlInfo = urlInfoRepository.getOne(url_id);
//        	Click_Info clickInfo = new Click_Info(urlInfo, new Timestamp(System.currentTimeMillis()));
//        	clickInfoRepository.save(clickInfo);
//    	}
    public void receiveMessage(String clickInfo_json) {
    	System.out.println("Queue Receiver invoked...");
    	if(!clickInfo_json.isEmpty()) {
    		System.out.println("click Info in Json Format in Queue receiver"+ clickInfo_json);
    		//convert json to object
    		//ObjectMapper mapper = new ObjectMapper();
    		Click_Info clickInfo = null;
			try {
				clickInfo = objectMapper.readValue(clickInfo_json, Click_Info.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("After conversion from json to object - print longUrl :" + clickInfo.getUrlInfo().getLongUrl());
        	clickInfoRepository.save(clickInfo);
    	}
    	
    }
    
}
