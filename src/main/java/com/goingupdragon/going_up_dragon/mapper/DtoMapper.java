package com.goingupdragon.going_up_dragon.mapper;

import com.goingupdragon.going_up_dragon.dto.CategoriesDTO;
import com.goingupdragon.going_up_dragon.dto.SubjectTagsDTO;
import com.goingupdragon.going_up_dragon.entity.Categories;
import com.goingupdragon.going_up_dragon.entity.SubjectTags;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    // Categories -> CategoriesDTO 변환
    public static CategoriesDTO toCategoriesDTO(Categories category) {
        List<CategoriesDTO> subCategoriesDTO = category.getSubCategories()
                .stream()
                .map(DtoMapper::toCategoriesDTO)
                .collect(Collectors.toList());

        List<SubjectTagsDTO> tagsDTO = category.getTags()
                .stream()
                .map(DtoMapper::toSubjectTagsDTO)
                .collect(Collectors.toList());

        return new CategoriesDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getDepth(),
                category.getCategoryDesc(),
                subCategoriesDTO,
                tagsDTO
        );
    }

    // SubjectTags -> SubjectTagsDTO 변환
    public static SubjectTagsDTO toSubjectTagsDTO(SubjectTags tag) {
        return new SubjectTagsDTO(
                tag.getSubjectTagId(),
                tag.getSubjectTagName(),
                tag.getSubjectTagDesc()
        );
    }
}
