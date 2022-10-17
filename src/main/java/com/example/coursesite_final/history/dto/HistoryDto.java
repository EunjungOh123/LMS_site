package com.example.coursesite_final.history.dto;

import com.example.coursesite_final.history.entity.History;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryDto {

    private String userId;
    private String userAgent;
    private String userIp;
    private LocalDateTime logDt;

    long totalCount;
    long seq;

    public static HistoryDto fromEntity(History history){
        return HistoryDto.builder()
                .userId(history.getUserId())
                .userAgent(history.getUserAgent())
                .userIp(history.getUserIp())
                .logDt(history.getLogDt())
                .build();
    }
    public static List<HistoryDto> fromEntity(List<History> histories) {
        if(histories== null){
            return null;
        }
        List<HistoryDto> historyList = new ArrayList<>();
        int count = 1;

        for(History x : histories){
            HistoryDto historyDto = HistoryDto.fromEntity(x);
            historyDto.setSeq(count);
            historyList.add(historyDto);
            count++;
        }
        return historyList;
    }
    public String getLogDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return logDt != null ? logDt.format(formatter) : "";
    }
}
