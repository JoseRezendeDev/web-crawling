package com.rezende.backend.service;

import com.rezende.backend.dto.GetCrawlResponse;
import com.rezende.backend.entity.CrawlEntity;
import com.rezende.backend.exception.CrawlNotFoundException;
import com.rezende.backend.repository.CrawlRepository;

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
