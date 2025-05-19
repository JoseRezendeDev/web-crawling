package com.rezende.backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrawlEntity {
    private String id;
    private CrawlStatus status;
    private List<String> urls;

    public CrawlEntity(String id, CrawlStatus status) {
        this.id = id;
        this.status = status;
        this.urls = new ArrayList<>();
    }

    public CrawlEntity(String id) {
        this.id = id;
        this.status = CrawlStatus.ACTIVE;
        this.urls = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public void addUrl(String url) {
        this.urls.add(url);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CrawlEntity that = (CrawlEntity) o;
        return Objects.equals(id, that.id) && status == that.status && Objects.equals(urls, that.urls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, urls);
    }
}
