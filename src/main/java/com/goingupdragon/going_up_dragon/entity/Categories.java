package com.goingupdragon.going_up_dragon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "category_name", length = 255)
    private String categoryName;

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "category_desc", length = 255)
    private String categoryDesc;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "parent_id")  // 자식 카테고리의 parent_id를 참조
    private Categories parentCategory;  // 부모 카테고리

    @ToString.Exclude
    @OneToMany(mappedBy = "parentCategory")  // 자식 카테고리들은 parentCategory로 연결됨
    private List<Categories> subCategories = new ArrayList<>();  // 서브 카테고리 목록

    @ToString.Exclude
    @OneToMany(mappedBy = "category")  // SubjectTags와의 관계
    private List<SubjectTags> tags = new ArrayList<>();
}

