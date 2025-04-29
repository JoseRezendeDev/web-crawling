package com.axreng.backend.repository;

import com.axreng.backend.entity.CrawlEntity;

import java.util.Collection;

public interface CrawlRepository {

    void addCrawl(CrawlEntity crawlEntity);

    CrawlEntity getCrawlById(String id);

    Collection<CrawlEntity> getAll();

    void finishSearch(String id);
}
