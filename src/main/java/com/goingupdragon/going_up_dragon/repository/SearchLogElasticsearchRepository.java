package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchLogElasticsearchRepository extends ElasticsearchRepository<SearchLogElasticsearch, String> {
    List<SearchLogElasticsearch> findBySearchQueryStartingWith(String prefix);

    SearchLogElasticsearch findBySearchQuery(String searchQuery);

//    SearchLogElasticsearch findBySearchQuery(String query);
//
//    List<SearchLogElasticsearch> findBySearchQueryStartingWithOrderBySearchCountDesc(String query);
}
