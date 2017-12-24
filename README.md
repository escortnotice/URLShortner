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
 
 