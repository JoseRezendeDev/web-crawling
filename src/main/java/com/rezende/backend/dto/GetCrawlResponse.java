package com.rezende.backend.dto;

import com.rezende.backend.entity.CrawlStatus;
import java.util.List;

public record GetCrawlResponse(String id, CrawlStatus crawlStatus, List<String> urls) { }
