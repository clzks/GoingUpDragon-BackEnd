package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.SearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE search_log sl
        LEFT JOIN user_info ui ON sl.info_id = ui.info_id
        LEFT JOIN courses co 
            ON ui.info_id = co.info_id
            OR co.course_title LIKE CONCAT('%', sl.search_query, '%')
            OR co.short_description LIKE CONCAT('%', sl.search_query, '%')
        LEFT JOIN categories mc ON co.main_category_id = mc.category_id
        LEFT JOIN categories sc ON co.sub_category_id = sc.category_id 
        LEFT JOIN subject_tags st ON sc.category_id = st.category_id
        SET sl.search_category = (
            SELECT GROUP_CONCAT(DISTINCT mc.category_name, ', ', sc.category_name, ', ', st.subject_tag_name)
            FROM categories mc
            LEFT JOIN categories sc ON co.sub_category_id = sc.category_id
            LEFT JOIN subject_tags st ON sc.category_id = st.category_id
            WHERE co.main_category_id = mc.category_id
            AND co.sub_category_id = sc.category_id
        )
        WHERE sl.search_log_id = :searchLogId
    """, nativeQuery = true)
    void updateSearchCategory(@Param("searchLogId") Long searchLogId);
}

