package com.example.coursesite_final.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Accessors(chain=true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String categoryName;
    int sortValue;
    boolean usingYn;
}
