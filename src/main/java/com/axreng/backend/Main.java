package com.axreng.backend;

import com.axreng.backend.exception.CrawlNotFoundException;
import com.axreng.backend.exception.ErrorResponse;
import com.axreng.backend.repository.CrawlRepository;
import com.axreng.backend.repository.CrawlRepositoryImpl;
import com.axreng.backend.service.CreateCrawl;
import com.axreng.backend.service.GetCrawl;
import com.google.gson.Gson;

import java.time.LocalDateTime;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        CreateCrawl createCrawl = new CreateCrawl();
        GetCrawl getCrawl = new GetCrawl(CrawlRepositoryImpl.getInstance());
        Gson gson = new Gson();
        String jsonType = "application/json";

        get("/crawl/:id", (req, res) -> {
            res.type(jsonType);
            return gson.toJson(getCrawl.getById(req.params("id")));
        });

        get("/crawl", (req, res) -> {
            res.type(jsonType);
            return gson.toJson(getCrawl.getAll());
        });

        post("/crawl", (req, res) -> {
            res.type(jsonType);
            return gson.toJson(createCrawl.create(req));
        });

        exception(CrawlNotFoundException.class, ((exception, request, response) -> {
            response.status(404);
            response.type(jsonType);
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        }));

        exception(IllegalArgumentException.class, (((exception, request, response) -> {
            response.status(400);
            response.type(jsonType);
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        })));

        exception(Exception.class, (((exception, request, response) -> {
            response.status(500);
            response.type(jsonType);
            response.body(gson.toJson(new ErrorResponse(exception.getMessage())));
        })));
    }
}
