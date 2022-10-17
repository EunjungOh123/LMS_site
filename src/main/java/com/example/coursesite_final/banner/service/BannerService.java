package com.example.coursesite_final.banner.service;

import com.example.coursesite_final.banner.dto.BannerDto;
import com.example.coursesite_final.banner.dto.InputBannerDto;
import com.example.coursesite_final.banner.model.BannerParam;

import java.util.List;

public interface BannerService {

    /**
     * 배너 목록
     */
    List<BannerDto> list(BannerParam parameter);

    /**
     * 배너 등록
     */
    void add(InputBannerDto bannerInput);

    /**
     * 배너 상세정보
     */
    BannerDto getById(long id);

    /**
     * 배너 정보수정
     */
    boolean set(InputBannerDto parameter);
    /**
     * 배너 삭제 (선택 삭제)
     */
    void delete(String idList);


    boolean updateStatus(String bannerName, String targetNewPage);
}
