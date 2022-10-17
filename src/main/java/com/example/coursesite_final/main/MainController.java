package com.example.coursesite_final.main;

import com.example.coursesite_final.banner.entity.Banner;
import com.example.coursesite_final.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final BannerRepository bannerRepository;

    @GetMapping("/")
    public String index (Model model) {

        List<Banner> all = bannerRepository.findAll();
        List<Banner> banners = new ArrayList<>();
        for(Banner x : all){
            if(x.isFrontOpen()){
                banners.add(x);
            }
        }

        model.addAttribute("banners", banners);
        return "index";
    }

    @GetMapping("/error/denied") // 관리자 권한 없는 경우 관리자 페이지 접속 시 발생하는 에러 페이지
    public String error() {
        return "error/denied";
    }


}
