package com.goingupdragon.going_up_dragon.repository;

import com.goingupdragon.going_up_dragon.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    // dept가 1인 메인 카테고리 조회 (부모 카테고리가 없는 경우)
    List<Categories> findByDepthAndParentCategoryIsNull(Integer depth);

    // dept가 2인 서브 카테고리 조회 (parentCategory로 부모 카테고리 지정)
    List<Categories> findByDepthAndParentCategory(Integer depth, Categories parentCategory);
}
