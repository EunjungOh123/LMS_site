package com.example.coursesite_final.course.dto;

import com.example.coursesite_final.course.entity.TakeCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TakeCourseDto {
    long id;
    long courseId;
    String userId;

    long payPrice;//결제금액
    String status;//상태(수강신청, 결재완료, 수강취소)

    LocalDateTime registeredAt;//신청일

    String name;
    String email;
    String subject;

    //추가컬럼
    long totalCount;
    long seq;

    public static TakeCourseDto fromEntity(TakeCourse x) {

        return TakeCourseDto.builder()
                .id(x.getId())
                .courseId(x.getCourseId())
                .userId(x.getUserId())
                .payPrice(x.getPayPrice())
                .status(x.getStatus())
                .registeredAt(x.getRegisteredAt())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return registeredAt != null ? registeredAt.format(formatter) : "";
    }
}
