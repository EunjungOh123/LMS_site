package com.example.coursesite_final.banner.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InputBannerDto {

    private long id;
    private String bannerName;

    private String bannerUrl;
    private String linkUrl;
    private String targetNewPage;
    private int sortValue;
    private boolean frontOpen;
    private LocalDate addAt;


    String filename;
    String urlFilename;

    String idList;
}
