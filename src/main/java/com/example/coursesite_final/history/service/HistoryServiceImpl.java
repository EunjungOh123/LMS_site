package com.example.coursesite_final.history.service;

import com.example.coursesite_final.history.dto.HistoryDto;
import com.example.coursesite_final.history.entity.History;
import com.example.coursesite_final.history.repository.HistoryRepository;
import com.example.coursesite_final.member.entity.SiteUser;
import com.example.coursesite_final.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService{

    private final UserRepository userRepository;
    private final HistoryRepository historyRepository;

    @Override
    public boolean add(HistoryDto historyDto) {
        Optional<SiteUser> optionalSiteUser = userRepository.findByUserId(historyDto.getUserId());
        if(!optionalSiteUser.isPresent()){
            return false;
        }
        History history = History.builder()
                .userId(historyDto.getUserId())
                .userIp(historyDto.getUserIp())
                .userAgent(historyDto.getUserAgent())
                .logDt(LocalDateTime.now())
                .build();

        historyRepository.save(history);
        SiteUser user = optionalSiteUser.get();
        user.setLastLoginDt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public List<HistoryDto> detail(String userId) {
        Optional<List<History>> list = historyRepository.findTop10ByUserIdOrderByLogDtDesc(userId);
        if(list.isPresent()){
            return HistoryDto.fromEntity(list.get());
        }
        return null;
    }
}
