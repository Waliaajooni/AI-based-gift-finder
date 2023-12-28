package com.practice.giftfinder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {
    private String personalInterest;
    private Boolean isMale;
    private Integer budget;
    private String relation;
    private String occasion;
}
