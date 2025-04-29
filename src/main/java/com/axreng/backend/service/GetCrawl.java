package com.axreng.backend.service;

import com.axreng.backend.dto.GetCrawlResponse;
import com.axreng.backend.entity.CrawlEntity;
import com.axreng.backend.exception.CrawlNotFoundException;
import com.axreng.backend.repository.CrawlRepository;

import java.util.List;
import java.util.Objects;

public class GetCrawl {

    private final CrawlRepository crawlRepository;

    public GetCrawl(CrawlRepository crawlRepository) {
        this.crawlRepository = crawlRepository;
    }

    public GetCrawlResponse getById(String id) {
        CrawlEntity crawlEntity = crawlRepository.getCrawlById(id);

        if (Objects.isNull(crawlEntity)) {
            throw new CrawlNotFoundException("Crawl with ID " + id + " not found");
        }

        return entityToDto(crawlEntity);
    }

    public List<GetCrawlResponse> getAll() {
        List<CrawlEntity> crawlEntities = crawlRepository.getAll().stream().toList();

        return crawlEntities.stream().map(this::entityToDto).toList();
    }

    private GetCrawlResponse entityToDto(CrawlEntity crawlEntity) {
        return new GetCrawlResponse(crawlEntity.getId(), crawlEntity.getStatus(), crawlEntity.getUrls());
    }
}
