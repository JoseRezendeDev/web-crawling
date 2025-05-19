package com.rezende.backend.service;

import com.rezende.backend.entity.CrawlEntity;
import com.rezende.backend.repository.CrawlRepository;
import spark.utils.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * LOGIC OF CRAWLING IS IN THIS CLASS
 */
public class SearchTerm implements Runnable {
    private final String id;
    private final String term;
    private final String url;
    private final Set<String> visitedUrls;
    private final CrawlRepository crawlRepository;

    @Override
    public void run() {
        CrawlEntity crawlEntity = new CrawlEntity(id);
        crawlRepository.addCrawl(crawlEntity);

        searchRecursive(url, crawlEntity);

        crawlRepository.finishSearch(id);
    }

    private void searchRecursive(String url, CrawlEntity crawlEntity) {
        String nextUrl = scanNextUrl(url, crawlEntity);

        if (nextUrl == null) {
            return;
        }

        searchRecursive(nextUrl, crawlEntity);
    }

    /**
     * This method performs the request to the desired URL, retrieves the HTML response,
     * and checks if the HTML contains the searched term.
     *
     * @param url         The URL to be scanned
     * @param crawlEntity The entity to add the URL if the term is present on the website
     * @return The next URL anchored in the parameter URL
     * @author Jose Rezende
     */
    public String scanNextUrl(String url, CrawlEntity crawlEntity) {
        String htmlResponse = callWebsite(url);

        if (htmlResponse.contains(this.term)) {
            crawlEntity.addUrl(url);
        }

        // GPT code
        int index = 0;

        while ((index = htmlResponse.indexOf("<a", index)) != -1) {
            int tagEnd = htmlResponse.indexOf(">", index);
            if (tagEnd == -1) break;

            String tag = htmlResponse.substring(index, tagEnd + 1);
            int hrefIndex = tag.indexOf("href=");
            if (hrefIndex != -1) {
                char quote = tag.charAt(hrefIndex + 5);
                int start = hrefIndex + 6;
                int end = tag.indexOf(quote, start);
                if (end != -1) {
                    String href = tag.substring(start, end);

                    // My logic in this method starts here

                    // Check if it is relative path and add the base URL to it
                    if (!href.contains("http")) {
                        href = this.url + "/" + href;
                    }

                    // Check if the path has the base URL and return href which
                    // is the next URL to be searched
                    if (href.contains(this.url)) {
                        if (visitedUrls.contains(href)) {
                            index = tagEnd + 1;
                            continue;
                        }

                        visitedUrls.add(href);
                        return href;
                    }
                }
            }
            index = tagEnd + 1;
        }

        return null;
    }

    @Deprecated
    // Tried without GPT, but I couldn't make it
    private void searchRecursiveWithoutGPT(String url) {
        String htmlResponse = callWebsite(url);

        int index = htmlResponse.indexOf(url);
        if (index == -1) {
            return;
        }

        String portion = htmlResponse.substring(index, 200);

        String[] arrayOfPortion = portion.split("\"");

        searchRecursiveWithoutGPT(arrayOfPortion[0]);
    }

    private String callWebsite(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body().toLowerCase();
        } catch (IOException | InterruptedException e) {
            System.out.println("Ignoring due to bad URL");
            return "";
        }
    }

    public SearchTerm(String id, String term, CrawlRepository crawlRepository) {
        this.id = id;
        this.term = term;
        String envVarUrl = System.getenv("BASE_URL");
        if (StringUtils.isBlank(envVarUrl)) {
            throw new IllegalStateException("Environment variable 'BASE_URL' is not set");
        }
        this.url = envVarUrl;
        this.visitedUrls = new HashSet<>();
        this.crawlRepository = crawlRepository;
    }
}
