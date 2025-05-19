package com.rezende.backend.service;

import com.rezende.backend.dto.CreateCrawlRequest;
import com.rezende.backend.dto.CreateCrawlResponse;
import com.rezende.backend.repository.CrawlRepositoryImpl;
import com.google.gson.Gson;
import spark.Request;
import spark.utils.StringUtils;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateCrawl {
    private final Gson gson;
    private final ExecutorService executorService;

    public CreateCrawl() {
        gson = new Gson();
        executorService = Executors.newCachedThreadPool();
    }

    public CreateCrawlResponse create(Request request) {
        CreateCrawlRequest createCrawlRequest = validateAndGetRequest(request);

        String id = UUID.randomUUID().toString().substring(0, 8);

        executorService.submit(new SearchTerm(id, createCrawlRequest.getKeyword(), CrawlRepositoryImpl.getInstance()));

        return new CreateCrawlResponse(id);
    }

    private CreateCrawlRequest validateAndGetRequest(Request request) {
        if (Objects.isNull(request) || StringUtils.isBlank(request.body())) {
            throw new IllegalArgumentException("Body is missing");
        }

        CreateCrawlRequest createCrawlRequest = gson.fromJson(request.body(), CreateCrawlRequest.class);

        String keyword = createCrawlRequest.getKeyword();

        if (StringUtils.isBlank(keyword)) {
            throw new IllegalArgumentException("Field 'keyword' is required");
        }

        if (keyword.length() < 4 || keyword.length() > 32) {
            throw new IllegalArgumentException("Field 'keyword' must have between 4 and 32 characters");
        }

        createCrawlRequest.setKeyword(createCrawlRequest.getKeyword().toLowerCase());

        return createCrawlRequest;
    }
}
