package com.goingupdragon.going_up_dragon.controller;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import com.goingupdragon.going_up_dragon.service.SearchLogService;
import com.goingupdragon.going_up_dragon.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchLogController {

    private final SearchLogService searchLogService;

    @PostMapping("/save")
    public ResponseEntity<SearchLog> saveSearchQuery(@RequestParam String searchQuery) {
        SearchLog savedLog = searchLogService.saveSearchQuery(searchQuery);
        return ResponseEntity.ok(savedLog);
    }

//    @GetMapping("/suggest")
//    public List<SearchLogElasticsearch> getSuggestions(@RequestParam String searchQuery) {
//        return searchLogService.getSuggestions(searchQuery);
//    }

    @GetMapping("/suggest")
    public ResponseEntity<List<String>> getSuggestions(@RequestParam String searchQuery) {
        List<String> suggestions = searchLogService.getSuggestions(searchQuery);
        return ResponseEntity.ok(suggestions);
    }

//    @GetMapping("/suggest")
//    public ResponseEntity<List<String>> getSuggestions(@RequestParam String searchQuery) {
//        List<String> suggestions = searchLogService.getSuggestions(searchQuery);
//        return ResponseEntity.ok(suggestions);
//    }
}


