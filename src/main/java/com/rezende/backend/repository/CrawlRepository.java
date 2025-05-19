package com.rezende.backend.repository;

import com.rezende.backend.entity.CrawlEntity;

import java.util.Collection;

public interface CrawlRepository {

    void addCrawl(CrawlEntity crawlEntity);

    CrawlEntity getCrawlById(String id);

    Collection<CrawlEntity> getAll();

    void finishSearch(String id);
}
