package com.example.coursesite_final.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Accessors(chain=true)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long categoryId;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDt;

    LocalDateTime registeredAt; //등록일(추가날짜)
    LocalDateTime updatedAt; //수정일(수정날짜)

    String filename;
    String urlFilename;

}
