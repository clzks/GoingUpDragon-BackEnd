//package com.goingupdragon.going_up_dragon.service;
//
//import com.goingupdragon.going_up_dragon.entity.SearchLog;
//import com.goingupdragon.going_up_dragon.entity.UserInfo;
//import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
//import com.goingupdragon.going_up_dragon.repository.UserInfoRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class SearchLogService {
//
//    private final SearchLogRepository searchLogRepository;
//    private final UserInfoService userInfoService;
//
//    @Transactional
//    public SearchLog saveSearchQuery(String searchQuery) {
//        // 로그인한 사용자 정보 가져오기 (없으면 null)
//        UserInfo userInfo = userInfoService.getLoggedInUser().orElse(null);
//
//        // 2️⃣ SearchLog 객체 생성 및 저장
//        SearchLog searchLog = SearchLog.builder()
//                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
//                .searchQuery(searchQuery)
//                .searchCategory(null)
//                .searchTime(LocalDateTime.now())
//                .build();
//
//        searchLog = searchLogRepository.save(searchLog);
//
//        // search_category 업데이트 실행
//        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());
//
//        return searchLog;
//    }
//}
//
//

package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import com.goingupdragon.going_up_dragon.entity.SearchLogElasticsearch;
import com.goingupdragon.going_up_dragon.entity.UserInfo;
import com.goingupdragon.going_up_dragon.repository.SearchLogRepository;
import com.goingupdragon.going_up_dragon.repository.SearchLogElasticsearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchLogService {

    private final SearchLogRepository searchLogRepository;
    private final SearchLogElasticsearchRepository searchLogElasticsearchRepository;  // 추가된 리포지토리
    private final UserInfoService userInfoService;


    @Transactional
    public SearchLog saveSearchQuery(String searchQuery) {
        // 로그인한 사용자 정보 가져오기 (없으면 null)
        UserInfo userInfo = userInfoService.getLoggedInUser().orElse(null);

        // 2️⃣ SearchLog 객체 생성 및 저장
        SearchLog searchLog = SearchLog.builder()
                .userInfo(userInfo)  // 로그인한 사용자 정보 (없으면 null)
                .searchQuery(searchQuery)
                .searchCategory(null)
                .searchTime(LocalDateTime.now())
                .build();

        searchLog = searchLogRepository.save(searchLog);

        // search_category 업데이트 실행
        searchLogRepository.updateSearchCategory(searchLog.getSearchLogId());

        // Elasticsearch에 저장
//        saveToElasticsearch(searchLog);
        saveToElasticsearch(searchQuery);

        return searchLog;
    }

    // Elasticsearch에 데이터를 저장하는 메서드
//    private void saveToElasticsearch(SearchLog searchLog) {
//        SearchLogElasticsearch searchLogElasticsearch = new SearchLogElasticsearch();
//        searchLogElasticsearch.setSearchQuery(searchLog.getSearchQuery());
//        searchLogElasticsearch.setSearchCategory(searchLog.getSearchCategory());
//        searchLogElasticsearch.setSearchTime(searchLog.getSearchTime());
//
//        searchLogElasticsearchRepository.save(searchLogElasticsearch);
//    }


//    public List<SearchLogElasticsearch> getSuggestions(String query) {
//        return searchLogElasticsearchRepository.findBySearchQueryStartingWith(query);
//    }

    private void saveToElasticsearch(String searchQuery) {
        // 기존 검색어 존재 여부 확인
        SearchLogElasticsearch existingLog = searchLogElasticsearchRepository.findBySearchQuery(searchQuery);
        if (existingLog != null) {
            existingLog.setFrequency(existingLog.getFrequency() + 1);
        } else {
            existingLog = new SearchLogElasticsearch();
            existingLog.setSearchQuery(searchQuery);
            existingLog.setFrequency(1);
        }
        searchLogElasticsearchRepository.save(existingLog);
    }

    // 자동완성 검색 API
    public List<String> getSuggestions(String query) {
        List<SearchLogElasticsearch> results = searchLogElasticsearchRepository.findBySearchQueryStartingWith(query);

        return results.stream()
                .sorted(Comparator.comparingInt(SearchLogElasticsearch::getFrequency).reversed()) // 빈도순 정렬
                .map(SearchLogElasticsearch::getSearchQuery)
                .distinct()
                .limit(5) // 최대 5개 제한
                .collect(Collectors.toList());
    }
}

