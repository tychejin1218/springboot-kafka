package com.example.springbootapachkafka.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class XapiDto {

  @Getter
  @Setter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  public static class XapiRequest {

    private String resultDetailYn;
    private String userAccount;
    private String object;
    private Boolean examresult;
    private int examNo;
    private String answer;
    private String unit;
    private String tag;
    private long elapsedTime;
    private int score;
    private int maxScore;
    private int examTrue;
    private int examFalse;
  }
}
