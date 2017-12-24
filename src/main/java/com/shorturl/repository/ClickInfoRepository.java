package com.shorturl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shorturl.model.Click_Info;

@Repository
public interface ClickInfoRepository extends JpaRepository<Click_Info, Long>{

}
