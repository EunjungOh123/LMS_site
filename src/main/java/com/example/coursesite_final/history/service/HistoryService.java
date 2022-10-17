package com.example.coursesite_final.history.service;

import com.example.coursesite_final.history.dto.HistoryDto;

import java.util.List;

public interface HistoryService {

    boolean add(HistoryDto history);
    List<HistoryDto> detail(String userId);

}
