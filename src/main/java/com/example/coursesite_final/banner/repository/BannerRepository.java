package com.example.coursesite_final.banner.repository;

import com.example.coursesite_final.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByBannerName(String bannerName);
    Optional<List<Banner>> findByFrontOpen(boolean frontOpen);
}
