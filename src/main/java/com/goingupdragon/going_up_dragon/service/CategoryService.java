package com.goingupdragon.going_up_dragon.service;

import com.goingupdragon.going_up_dragon.entity.Categories;
import com.goingupdragon.going_up_dragon.entity.SubjectTags;
import com.goingupdragon.going_up_dragon.repository.CategoryRepository;
import com.goingupdragon.going_up_dragon.repository.SubjectTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubjectTagRepository subjectTagRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,
                           SubjectTagRepository subjectTagRepository) {
        this.categoryRepository = categoryRepository;
        this.subjectTagRepository = subjectTagRepository;
    }

    // 메인 카테고리 및 서브 카테고리와 태그들 조회
    public List<Categories> getCategoriesWithTags(Integer depth) {
        // depth가 1인 메인 카테고리 조회
        List<Categories> mainCategories = categoryRepository.findByDepthAndParentCategoryIsNull(depth);

        // 각 메인 카테고리의 서브 카테고리와 태그 조회
        for (Categories mainCategory : mainCategories) {
            // 서브 카테고리 조회 (depth = 2)
            List<Categories> subCategories = categoryRepository.findByDepthAndParentCategory(2, mainCategory);
            mainCategory.setSubCategories(subCategories);

            // 서브 카테고리 내의 태그 조회
            for (Categories subCategory : subCategories) {
                List<SubjectTags> tags = subjectTagRepository.findByCategory(subCategory);
                subCategory.setTags(tags);
            }
        }

        return mainCategories;
    }
}
