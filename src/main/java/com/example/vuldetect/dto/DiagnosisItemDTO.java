package com.example.vuldetect.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiagnosisItemDTO {

    String name;
    int round;
    String result;
    String vulnerability;

    public DiagnosisItemDTO(String name, int round, String result, String vulnerability) {
        this.name = name;
        this.round = round;
        this.result = result;
        this.vulnerability = vulnerability;
    }
}
