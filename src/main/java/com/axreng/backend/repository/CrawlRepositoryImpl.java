package com.axreng.backend.repository;

import com.axreng.backend.entity.CrawlEntity;
import com.axreng.backend.entity.CrawlStatus;
import spark.utils.StringUtils;

import java.util.*;

public class CrawlRepositoryImpl implements CrawlRepository {
    private static CrawlRepositoryImpl crawlRepository;
    private final Map<String, CrawlEntity> crawlData;

    private CrawlRepositoryImpl() {
        crawlData = new HashMap<>();
    }

    public static CrawlRepositoryImpl getInstance() {
        if (Objects.isNull(crawlRepository)) {
            crawlRepository = new CrawlRepositoryImpl();
        }

        return crawlRepository;
    }

    public void addCrawl(CrawlEntity crawlEntity) {
        if (Objects.isNull(crawlEntity)
                || StringUtils.isBlank(crawlEntity.getId())) {
            throw new IllegalArgumentException("Crawl should have an id");
        }

        crawlRepository.crawlData.put(crawlEntity.getId(), crawlEntity);
    }

    public CrawlEntity getCrawlById(String id) {
        return crawlRepository.crawlData.get(id);
    }

    public Collection<CrawlEntity> getAll() {
        return crawlRepository.crawlData.values();
    }

    @Override
    public void finishSearch(String id) {
        CrawlEntity crawlEntity = crawlRepository.crawlData.get(id);

        if (Objects.nonNull(crawlEntity)) {
            crawlEntity.setStatus(CrawlStatus.DONE);
        }
    }
}
