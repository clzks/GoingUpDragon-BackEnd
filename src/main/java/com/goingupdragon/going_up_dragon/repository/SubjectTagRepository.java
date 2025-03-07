package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Categories;
import com.goingupdragon.going_up_dragon.entity.SubjectTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectTagRepository extends JpaRepository<SubjectTags, Integer> {
    // 특정 카테고리에 속한 모든 태그 조회
    List<SubjectTags> findByCategory(Categories category);
}