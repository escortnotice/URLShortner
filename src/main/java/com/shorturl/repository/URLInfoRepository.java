package com.shorturl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shorturl.model.URL_Info;

public interface URLInfoRepository extends JpaRepository<URL_Info, Long>{
	
	URL_Info findByLongUrl(String longurl);
	
	URL_Info findByShortUrl(String shorturl);

}
