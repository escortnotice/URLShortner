About the project : This is a simple application where we pass the long url and a short url is generated,
saved in the database and returned to the requester.
Next time when the short url is sent in the request, its corresponding long url is returned.
It also captures how many times the short url was used to get the long url.

1) diagrams made with www.draw.io


2) urls for the app : 
http://localhost:8300/url-service/shorten?longUrl=www.yahoo.com
http://localhost:8300/url-service/AllUrls
http://localhost:8300/url-service/fullUrl?shortUrl= 1070359356

3)  Tech Stack
 - Web
 - MySQL
 - JPA
 - Caching
 - Async (does not work when called in the same class, will only work when the method is
 			called from a different class)
 - RabbitMQ (Point to Point technique used)
 
 
 Rabbit MQ Details: 
 	QueueName : link-queue
 	Queue Durability : Durable
 	ExchangeName: link-exchange
 	Exchange Durability: Durable
 	ExchangeType : Topic
 	Routing-key : link-routingkey
 	
 	
 4) Added content negotiation feature (to produce both xml and json responses).Default is json
 Url: http://localhost:8300/url-service/shorten?longUrl=www.sify.com
 Header: Accept:application/json
 
 Url: http://localhost:8300/url-service/shorten?longUrl=www.sify.com
 Header: Accept:application/xml
 
 Url: http://localhost:8300/url-service/shorten.xml?longUrl=www.sify.com
 
 Url: http://localhost:8300/url-service/shorten.json?longUrl=www.sify.com
 
 
 	
 