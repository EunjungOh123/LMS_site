package com.example.coursesite_final.banner.service;

import com.example.coursesite_final.banner.dto.BannerDto;
import com.example.coursesite_final.banner.dto.InputBannerDto;
import com.example.coursesite_final.banner.entity.Banner;
import com.example.coursesite_final.banner.mapper.BannerMapper;
import com.example.coursesite_final.banner.model.BannerParam;
import com.example.coursesite_final.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;



    @Override
    public List<BannerDto> list(BannerParam parameter) {
        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(BannerDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() -i);
                i++;
            }
        }
        return list;
    }

    @Override
    public void add(InputBannerDto bannerInput) {
        Banner banner = Banner.builder()
                .bannerName(bannerInput.getBannerName())
                .bannerUrl(bannerInput.getUrlFilename())
                .linkUrl(bannerInput.getLinkUrl())
                .targetNewPage(bannerInput.getTargetNewPage())
                .addAt(LocalDate.now())
                .sortValue(bannerInput.getSortValue())
                .frontOpen(bannerInput.isFrontOpen())
                .build();

        bannerRepository.save(banner);
    }

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::fromEntity).orElse(null);
    }

    @Override
    public boolean set(InputBannerDto bannerInput) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerInput.getId());
        if(!optionalBanner.isPresent()){
            return false;
        }
        Banner banner = optionalBanner.get();

        if(bannerInput.getBannerUrl() != null){
            banner.setBannerUrl(bannerInput.getUrlFilename());
        }

        banner.setBannerName(bannerInput.getBannerName());
        banner.setLinkUrl(bannerInput.getLinkUrl());
        banner.setTargetNewPage(bannerInput.getTargetNewPage());
        banner.setSortValue(bannerInput.getSortValue());
        banner.setFrontOpen(bannerInput.isFrontOpen());

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public void delete (String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
    }

    @Override
    public boolean updateStatus(String bannerName, String targetNewPage) {
        Optional<Banner> optionalBanner = bannerRepository.findByBannerName(bannerName);
        if(!optionalBanner.isPresent()){
            throw new UsernameNotFoundException("배너 정보가 없습니다.");
        }
        Banner banner = optionalBanner.get();

        banner.setTargetNewPage(targetNewPage);
        bannerRepository.save(banner);
        return true;
    }
}
