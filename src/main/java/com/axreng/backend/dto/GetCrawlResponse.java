package com.axreng.backend.dto;

import com.axreng.backend.entity.CrawlStatus;
import java.util.List;

public record GetCrawlResponse(String id, CrawlStatus crawlStatus, List<String> urls) { }
