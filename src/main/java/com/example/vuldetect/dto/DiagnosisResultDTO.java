package com.example.vuldetect.dto;

import com.example.vuldetect.domain.ProjectResult;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class DiagnosisResultDTO {
    private String name;
    private String code;
    private int round;
    private String result;
    private String goodStandard;
    private ProjectResult projectResult;
    private Long diagnosis_item_id;
    private String vulnerability;
    private String dgnssEntityKey;
    private String dgnssEntityStatus;
    private String isPass;
    private JsonNode jsonNode;

    public DiagnosisResultDTO(String name, String code, String goodStandard, ProjectResult projectResult, Long diagnosis_item_id, String vulnerability, String dgnssEntityKey, String dgnssEntityStatus, String isPass, JsonNode jsonNode) {
        this.name = name;
        this.code = code;
        this.goodStandard = goodStandard;
        this.round = projectResult.getRound();
        this.projectResult = projectResult;
        this.diagnosis_item_id = diagnosis_item_id;
        this.vulnerability = vulnerability;
        this.dgnssEntityKey = dgnssEntityKey;
        this.dgnssEntityStatus = dgnssEntityStatus;
        this.isPass = isPass;
        this.jsonNode = jsonNode;
    }


}