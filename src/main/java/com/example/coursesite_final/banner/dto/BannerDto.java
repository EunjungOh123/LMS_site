package com.example.coursesite_final.banner.dto;

import com.example.coursesite_final.banner.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BannerDto {

    private long id;
    private String bannerName;
    private String linkUrl;
    private String targetNewPage;
    private int sortValue;
    private boolean frontOpen;

    private String bannerUrl;
    private LocalDate addAt;

    private long seq;
    private long totalCount;


    public static BannerDto fromEntity(Banner banner){
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .bannerUrl(banner.getBannerUrl())
                .linkUrl(banner.getLinkUrl())
                .targetNewPage(banner.getTargetNewPage())
                .frontOpen(banner.isFrontOpen())
                .sortValue(banner.getSortValue())
                .addAt(banner.getAddAt())
                .build();
    }

    public static List<BannerDto> fromEntity(List<Banner> banners) {
        if(banners == null){
            return null;
        }
        List<BannerDto> bannerList = new ArrayList<>();
        for(Banner x : banners){
            BannerDto banner = BannerDto.fromEntity(x);
            bannerList.add(banner);
        }
        return bannerList;
    }
}
